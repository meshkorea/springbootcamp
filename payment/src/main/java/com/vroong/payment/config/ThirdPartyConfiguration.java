package com.vroong.payment.config;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vroong.payment.application.port.out.Fixtures;
import javax.annotation.PreDestroy;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile(value = "local")
public class ThirdPartyConfiguration {

  private ClientAndServer mockServer;

  public ThirdPartyConfiguration() throws JsonProcessingException {
    mockServer = ClientAndServer.startClientAndServer(65_535);
    ObjectMapper mapper = new ObjectMapper();

    new MockServerClient("localhost", 65_535)
        .when(request().withMethod("POST").withPath("/checkout"))
        .respond(
            response()
                .withHeader(new Header("Content-Type", "application/json"))
                .withBody(mapper.writeValueAsString(Fixtures.aRandomCheckoutPaymentResponse())));

    new MockServerClient("localhost", 65_535)
        .when(request().withMethod("POST").withPath("/cancel"))
        .respond(
            response()
                .withHeader(new Header("Content-Type", "application/json"))
                .withBody(mapper.writeValueAsString(Fixtures.aCancelPaymentResponse())));
  }

  @PreDestroy
  public void destroy() {
    mockServer.stop();
  }
}
