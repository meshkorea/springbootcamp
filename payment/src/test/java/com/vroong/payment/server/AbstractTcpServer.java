package com.vroong.payment.server;

import com.vroong.payment.config.TcpServerProperties;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.PreDestroy;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractTcpServer {

  protected final int port;
  protected final ExecutorService executor;

  protected final AtomicReference<ServerSocket> serverSocketHolder = new AtomicReference<>();
  protected final AtomicReference<List<Socket>> socketHolder = new AtomicReference<>(
      new ArrayList<>());

  public AbstractTcpServer(TcpServerProperties properties) {
    this.port = properties.getPort();
    this.executor = Executors.newFixedThreadPool(properties.getMaxConnection());
  }

  public abstract void handleMessage(BufferedReader reader, PrintWriter writer);

  @SneakyThrows
  public void start() {
    final ServerSocket serverSocket = new ServerSocket(port);
    this.serverSocketHolder.set(serverSocket);
    log.info("Tcp server is listening at port {}", port);

    while (true) {
      final Socket socket = serverSocket.accept(); // 여기서 블록킹하고 있다가, 클라이언트가 접속하면 해제됨
      socketHolder.get().add(socket);
      log.info("A connection established to port {}", socket.getPort());

      CompletableFuture.runAsync(() -> {
        PrintWriter writer = null;
        BufferedReader reader = null;
        try {
          writer = new PrintWriter(socket.getOutputStream(), true);
          reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

          handleMessage(reader, writer);
        } catch (IOException e) {
          log.warn("{}: {}", e.getMessage(), socket.getPort());
        } finally {
          try {
            reader.close();
            writer.close();
            socket.close();
          } catch (IOException e) {
            log.error(String.format("Connection to port %s was not closed", socket.getPort()));
          }
        }
      }, executor);
    }
  }

  @PreDestroy
  @SneakyThrows
  public void stop() {
    if (socketHolder != null) {
      socketHolder.get().forEach(s -> {
        try {
          s.close();
        } catch (IOException e) {
          log.error(String.format("Connection to port %s was not closed", s.getPort()));
        }
      });
    }

    if (serverSocketHolder != null && serverSocketHolder.get() != null) {
      serverSocketHolder.get().close();
    }

    executor.awaitTermination(5, TimeUnit.SECONDS);
    executor.shutdown();
  }
}
