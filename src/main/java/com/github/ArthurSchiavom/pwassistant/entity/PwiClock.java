package com.github.ArthurSchiavom.pwassistant.entity;

import com.github.ArthurSchiavom.shared.entity.converter.EnumListConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Setter;

import java.util.List;

@Entity
public class PwiClock {
    @Id
    @Setter(AccessLevel.NONE)
    private long id;
    private long serverId;
    private long channelId;
    private long msgId;
    @Convert(converter = EnumListConverter.class)
    private List<PwiServer> servers;
}
