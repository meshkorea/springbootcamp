package com.vroong.order.application.port.out.message;

import com.vroong.order.domain.PersistentEvent;

public interface MessageProducer {

  boolean produce(PersistentEvent persistentEvent);
}
