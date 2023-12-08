package com.github.ArthurSchiavom.pwassistant.entity;

import com.github.ArthurSchiavom.shared.entity.converter.PwiServerSetConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Accessors(chain = true)
public class PwiClock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long serverId;
    private long channelId;
    private long messageId;
    @Convert(converter = PwiServerSetConverter.class)
    private Set<PwiServer> servers;

    public Set<PwiServer> getServers() {
        return new HashSet<>(servers);
    }
}
