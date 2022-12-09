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

CREATE TABLE IF NOT EXISTS deliveries
(
    id               bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
    order_id         bigint(20) NOT NULL COMMENT '주문 아이디',
    trace_number     char(36) NULL DEFAULT NULL COMMENT '송장번호',
    location         json NULL DEFAULT NULL COMMENT '현재 위치 {lat: 37.575819, lng: 126.976834}',
    status           tinyint(3) unsigned NOT NULL DEFAULT 0 COMMENT '배송 상태',
    created_at       timestamp(3) NOT NULL COMMENT '생성 일시',
    updated_at       timestamp(3) NULL DEFAULT NULL COMMENT '수정 일시',
    created_by       varchar(40) NULL COMMENT '생성 사용자',
    updated_by       varchar(40) NULL COMMENT '수정 사용자',
    PRIMARY KEY (id),
    KEY IDX_ORDER_ID (order_id),
    KEY IDX_TRACE_NUMBER (trace_number),
    KEY IDX_CREATED_AT (created_at),
    KEY IDX_STATUS_CREATED_AT (status, created_at)
);

CREATE TABLE IF NOT EXISTS delivery_user_infos
(
    id               bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
    delivery_id      bigint(20) NOT NULL COMMENT '배송 아이디',
    sender_name      varchar(32) NOT NULL COMMENT '발신자 이름',
    sender_phone     varchar(20) NOT NULL COMMENT '발신자 전화번호',
    sender_address   varchar(255) NOT NULL COMMENT '발신자 주소',
    receiver_name    varchar(32) NOT NULL COMMENT '수신자 이름',
    receiver_phone   varchar(20) NOT NULL COMMENT '수신자 전화번호',
    receiver_address varchar(255) NOT NULL COMMENT '수신자 주소',
    created_at       timestamp(3) NOT NULL COMMENT '생성 일시',
    updated_at       timestamp(3) NULL DEFAULT NULL COMMENT '수정 일시',
    created_by       varchar(40) NULL COMMENT '생성 사용자',
    updated_by       varchar(40) NULL COMMENT '수정 사용자',
    PRIMARY KEY (id),
    FOREIGN KEY (delivery_id) REFERENCES deliveries(id) ON DELETE CASCADE
);
