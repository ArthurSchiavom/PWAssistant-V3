package com.github.ArthurSchiavom.pwassistant.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity(name = "trigger_table")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class Trigger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long serverId;
    private String triggerTextLowercase;
    private String reply;

    public Trigger(long serverId, String triggerText, String reply) {
        this.serverId = serverId;
        this.triggerTextLowercase = triggerText;
        this.reply = reply;
    }

    public void setTriggerTextLowercase(final String triggerText) {
        this.triggerTextLowercase = triggerText.toLowerCase();
    }
}
