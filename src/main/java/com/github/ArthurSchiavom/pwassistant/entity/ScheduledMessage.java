package com.github.ArthurSchiavom.pwassistant.entity;

import com.github.ArthurSchiavom.shared.entity.converter.IntegerListConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Entity
@Getter
@Setter
@Accessors(chain = true)
public class ScheduledMessage {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private long id;
    private String scheduleName;
    private long serverId;
    private long channelId;
    private String message;
    @Enumerated(EnumType.STRING)
    private RepetitionType repetitionType;
    @Convert(converter = IntegerListConverter.class)
    private List<Integer> scheduleDays;
    private short hour;
    private short minute;
}
