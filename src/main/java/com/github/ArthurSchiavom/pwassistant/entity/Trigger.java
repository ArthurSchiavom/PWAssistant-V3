package com.github.ArthurSchiavom.pwassistant.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter
@Accessors(chain = true)
public class Trigger {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private long id;
    private long serverId;
    private String triggerText;
    private String reply;
}
