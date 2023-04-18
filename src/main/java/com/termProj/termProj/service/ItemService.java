package com.termProj.termProj.service;

import com.termProj.termProj.model.ItemEntity;
import com.termProj.termProj.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
