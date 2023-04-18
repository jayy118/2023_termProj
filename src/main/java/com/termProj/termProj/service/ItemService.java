package com.termProj.termProj.service;

import com.termProj.termProj.model.ItemEntity;
import com.termProj.termProj.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ItemService {

    public List<ItemEntity> create(final ItemEntity entity) {
        validate(entity);

        repository.save(entity);

        log.info("Entity id : {} is saved.", entity.getId());

        return repository.findByUserId(entity.getUserId());
    }

    public List<ItemEntity> retrieve(final String userId) {
        return repository.findByUserId(userId);
    }

    public List<ItemEntity> update(final ItemEntity entity) {
        validate(entity);
        final Optional<ItemEntity> original = repository.findById(entity.getId());

        original.ifPresent(item -> {
            item.setTitle(entity.getTitle());
            item.setType(entity.getType());
            item.setBrand(entity.getBrand());

            repository.save(item);
        });

        return retrieve(entity.getUserId());
    }

    public List<ItemEntity> search(String title) {
        try {
            repository.findByTitleContaining(title);
        } catch (Exception e) {
            log.error("error search entity ", e);

            throw new RuntimeException("error search entity" + title);
        }
        return repository.findByTitleContaining(title);
    }

    public List<ItemEntity> delete(final ItemEntity entity) {
        validate(entity);

        try {
            repository.delete(entity);
        } catch (Exception e) {
            log.error("error deleting entity ", entity.getId(), e);

            throw new RuntimeException("error deleting entity" + entity.getId());
        }
        return retrieve(entity.getUserId());
    }

    private void validate(final ItemEntity entity) {
        if(entity == null) {
            log.warn("Entity cannot be null.");
            throw new RuntimeException("Entity cannot be null.");
        }

        if(entity.getUserId() == null) {
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }
    }
    @Autowired
    private ItemRepository repository;
    public String itemService() {
        ItemEntity entity = ItemEntity.builder().build();
        repository.save(entity);
        ItemEntity savedEntity = repository.findById(entity.getId()).get();
        return "";
    }
}
