create table countdown_clock
(
    id bigint NOT NULL AUTO_INCREMENT,
    server_id bigint,
    channel_id bigint,
    msg_id bigint,
    end_time datetime,
    PRIMARY KEY (id)
);

create table pwi_clock
(
    id bigint NOT NULL AUTO_INCREMENT,
    server_id bigint,
    channel_id bigint,
    message_id bigint,
    servers text,
    PRIMARY KEY (id)
);

create table scheduled_message
(
    id bigint NOT NULL AUTO_INCREMENT,
    schedule_name text,
    server_id bigint,
    channel_id bigint,
    message text,
    repetition_type text,
    schedule_days text,
    hour int,
    minute int,
    next_execution_time timestamp,
    PRIMARY KEY (id)
);

create table trigger_table
(
    id bigint NOT NULL AUTO_INCREMENT,
    server_id bigint,
    trigger_text text,
    reply text,
    PRIMARY KEY (id)
);
