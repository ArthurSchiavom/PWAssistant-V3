package com.github.ArthurSchiavom.shared.control.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import java.util.ArrayList;
import java.util.List;

public class CachedRepository<T> {
    private PanacheRepository<T> repo;
    private final List<T> items = new ArrayList<>();

    public void init(final PanacheRepository<T> repo) {
        this.repo = repo;

        items.clear();
        items.addAll(repo.findAll().list());
        System.out.println("a");
    }

    public void delete(T item) {
        repo.delete(item);
        items.remove(item);
    }

    public void persist(T item) {
        repo.persistAndFlush(item);
        items.add(item);
    }

    public List<T> getAllItems() {
        return new ArrayList<>(items);
    }
}
