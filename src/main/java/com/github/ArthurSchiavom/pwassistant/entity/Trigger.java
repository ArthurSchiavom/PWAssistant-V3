package com.github.ArthurSchiavom.pwassistant.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity(name = "trigger_table")
@Getter
@Setter
@Accessors(chain = true)
public class Trigger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long serverId;
    private String triggerText;
    private String reply;
}
