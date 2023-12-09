package com.github.ArthurSchiavom.shared.control.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class KeyedCachedRepository<K, T> {
    private PanacheRepository<T> repo;
    private final Map<K, List<T>> items = new HashMap<>();

    public abstract void init();
    public abstract K getKey(T entity);

    protected void init(final PanacheRepository<T> repo) {
        this.repo = repo;

        items.clear();
        repo.findAll().list().forEach(this::registerOnMap);
    }

    private void registerOnMap(final T entity) {
        final List<T> entities = items.computeIfAbsent(getKey(entity), key -> new ArrayList<>());
        entities.add(entity);
    }

    public void delete(T item) {
        repo.delete(item);

        final List<T> entities = items.get(getKey(item));
        if (entities == null) {
            return;
        }
        entities.remove(item);
        if (entities.isEmpty()) {
            items.remove(item);
        }
    }

    public void create(T item) {
        repo.persistAndFlush(item);
        registerOnMap(item);
    }

    public void updateOnDb(T item) {
        repo.getEntityManager().merge(item);
    }

    /**
     * @return view of items
     */
    public Map<K, List<T>> getAllItems() {
        return Collections.unmodifiableMap(items);
    }

    /**
     * @return view of items
     */
    public List<T> getItemsOfKey(final K key) {
        return Collections.unmodifiableList(items.getOrDefault(key, List.of()));
    }
}
