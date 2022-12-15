package com.vroong.delivery.application.port.out.message;

import com.vroong.delivery.domain.PersistentEvent;

public interface MessageProducer {

    boolean produce(PersistentEvent persistentEvent);
}
