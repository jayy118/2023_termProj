package com.termProj.termProj.controller;

import com.termProj.termProj.dto.ItemDTO;
import com.termProj.termProj.dto.ResponseDTO;
import com.termProj.termProj.model.ItemEntity;
import com.termProj.termProj.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("item")
public class ItemController {

    @Autowired
    private ItemService service;

    @PostMapping
    public ResponseEntity<?> createItem(@RequestBody ItemDTO dto) {
        try {

            String temporaryUserId = "temporary-user";
            ItemEntity entity = ItemDTO.toEntity(dto);
            entity.setId(null);
            entity.setUserId(temporaryUserId);
            List<ItemEntity> entities = service.create(entity);
            List<ItemDTO> dtos = entities.stream().map(ItemDTO::new).collect(Collectors.toList());
            ResponseDTO<ItemDTO> response = ResponseDTO.<ItemDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);

        } catch (Exception e) {

            String error = e.getMessage();
            ResponseDTO<ItemDTO> response = ResponseDTO.<ItemDTO>builder().error(error).build();

            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateItem(@RequestBody ItemDTO dto) {
        String temporaryUserId = "temporary-user";
        ItemEntity entity = ItemDTO.toEntity(dto);
        entity.setUserId(temporaryUserId);
        List<ItemEntity> entities = service.update(entity);
        List<ItemDTO> dtos = entities.stream().map(ItemDTO::new).collect(Collectors.toList());
        ResponseDTO<ItemDTO> response = ResponseDTO.<ItemDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);

    }

    @GetMapping
    public ResponseEntity<?> searchItems(@RequestBody ItemDTO dto) {
        ItemEntity entity = ItemDTO.toEntity(dto);
        String title = entity.getTitle();
        try {
            List<ItemEntity> entities = service.search(title);
            List<ItemDTO> dtos = entities.stream().map(ItemDTO::new).collect(Collectors.toList());
            ResponseDTO<ItemDTO> response = ResponseDTO.<ItemDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String error =e.getMessage();
            ResponseDTO<ItemDTO> response = ResponseDTO.<ItemDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }

    }
    @GetMapping("/list")
    public ResponseEntity<?> retrieveItemList() {
        String temporaryUserId = "temporary-user";

        List<ItemEntity> entities = service.retrieve(temporaryUserId);
        List<ItemDTO> dtos = entities.stream().map(ItemDTO::new).collect(Collectors.toList());
        ResponseDTO<ItemDTO> response = ResponseDTO.<ItemDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteItem(@RequestBody ItemDTO dto) {
        try {
            String temporaryUserId = "temporary-user";
            ItemEntity entity = ItemDTO.toEntity(dto);
            entity.setUserId(temporaryUserId);
            List<ItemEntity> entities = service.delete(entity);
            List<ItemDTO> dtos = entities.stream().map(ItemDTO::new).collect(Collectors.toList());
            ResponseDTO<ItemDTO> response = ResponseDTO.<ItemDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String error =e.getMessage();
            ResponseDTO<ItemDTO> response = ResponseDTO.<ItemDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }

    }
}
