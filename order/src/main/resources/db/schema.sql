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

CREATE TABLE IF NOT EXISTS orders
(
    id               bigint NOT NULL AUTO_INCREMENT,
    status           tinyint unsigned NOT NULL DEFAULT 0 COMMENT '오더 상태',
    orderer_name      varchar(32) NOT NULL COMMENT '주문자 이름',
    orderer_phone     varchar(20) NOT NULL COMMENT '주문자 전화번호',
    orderer_address   varchar(255) NOT NULL COMMENT '주문자 주소',
    receiver_name    varchar(32) NOT NULL COMMENT '수신자 이름',
    receiver_phone   varchar(20) NOT NULL COMMENT '수신자 전화번호',
    receiver_address varchar(255) NOT NULL COMMENT '수신자 주소',
    created_at       timestamp NOT NULL,
    updated_at       timestamp NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS order_items
(
    id               bigint NOT NULL AUTO_INCREMENT,
    order_id         bigint NOT NULL COMMENT '주문 번호',
    product_id       bigint NOT NULL COMMENT '상품 번호',
    product_name     varchar(60) NOT NULL COMMENT '상품명',
    quantity         smallint NOT NULL COMMENT '수량',
    created_at       timestamp NOT NULL,
    updated_at       timestamp NOT NULL,
    PRIMARY KEY (id)
);
