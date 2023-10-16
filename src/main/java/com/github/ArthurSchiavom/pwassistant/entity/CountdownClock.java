package com.github.ArthurSchiavom.pwassistant.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Accessors(chain = true)
public class CountdownClock {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private long id;
    private long serverId;
    private long channelId;
    private long msgId;
    private Timestamp endTime;

}
