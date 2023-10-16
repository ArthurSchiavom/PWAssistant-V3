package com.github.ArthurSchiavom.pwassistant.control.repository;

import com.github.ArthurSchiavom.pwassistant.entity.PwiClock;
import com.github.ArthurSchiavom.pwassistant.entity.Trigger;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
class TriggerRepository implements PanacheRepository<Trigger> {
}
