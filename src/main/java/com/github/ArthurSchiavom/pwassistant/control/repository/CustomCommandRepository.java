package com.github.ArthurSchiavom.pwassistant.control.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
class CustomCommandRepository implements PanacheRepository<CustomCommand> {
}
