-- USE local_payment;

CREATE TABLE IF NOT EXISTS shedlock
(
    name       varchar(64) NOT NULL,
    locked_by  varchar(255) DEFAULT NULL,
    locked_at  timestamp(3) NULL DEFAULT NULL,
    lock_until timestamp(3) NULL DEFAULT NULL,
    PRIMARY KEY (name)
);

CREATE TABLE IF NOT EXISTS persistent_events
(
    id            bigint(20) NOT NULL AUTO_INCREMENT,
    status        tinyint(3) unsigned NOT NULL DEFAULT '0',
    event_id      varchar(40)  NOT NULL,
    event_type    varchar(100) NOT NULL,
    partition_key varchar(40)  NOT NULL,
    body          longtext,
    created_at    timestamp(3) NOT NULL,
    produced_at   timestamp(3) NULL DEFAULT NULL,
    PRIMARY KEY (id)/*,
    KEY           IDX_STATUS_EVENTTYPE (status,event_type)*/
);

CREATE TABLE IF NOT EXISTS payments
(
    id              bigint(20)           NOT NULL AUTO_INCREMENT,
    card_number     varchar(40)          NOT NULL,
    approval_number varchar(40)          NULL     DEFAULT NULL,
    amount          decimal                       DEFAULT 0,
    status          tinyint(10) unsigned NOT NULL DEFAULT '0',
    order_id        bigint(20)           NOT NULL,
    created_by      varchar(40)          NOT NULL,
    created_at      timestamp(3)         NOT NULL,
    updated_by      varchar(40)          NOT NULL,
    updated_at      timestamp(3)         NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS idempotent_events
(
    id          bigint       NOT NULL AUTO_INCREMENT,
    event_id    varchar(40)  NOT NULL,
    consumed_at timestamp(3) NOT NULL,
    PRIMARY KEY (id)
);