package com.github.ArthurSchiavom.pwassistant.control.repository;

import com.github.ArthurSchiavom.pwassistant.entity.PwiClock;
import com.github.ArthurSchiavom.shared.control.repository.CachedRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PwiClockCachedRepository extends CachedRepository<PwiClock> {

    @Inject
    PwiClockRepository repo;

    @Override
    public void init() {
        this.init(repo);
    }

    public List<PwiClock> getAllItemsOfServer(final long serverId) {
        return this.getAllItems().stream().filter(c -> c.getServerId() == serverId).collect(Collectors.toList());
    }
}
