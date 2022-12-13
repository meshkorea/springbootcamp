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

CREATE TABLE IF NOT EXISTS products
(
    product_id          bigint(20) NOT NULL AUTO_INCREMENT,
    product_name        varchar(255) NOT NULL,
    description varchar(255) NOT NULL,
    price       decimal(10,2) NOT NULL,
    inventory  int(11) NOT NULL,
    supplier varchar(255) NOT NULL,
    product_width       decimal(4,2) NOT NULL,
    product_height      decimal(4,2) NOT NULL,
    product_depth       decimal(4,2) NOT NULL,
    store_location varchar(255) NOT NULL,
    created_at  timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  timestamp(3) NULL DEFAULT NULL,
    PRIMARY KEY (id)
);
