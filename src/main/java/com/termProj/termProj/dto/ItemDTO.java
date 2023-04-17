package com.termProj.termProj.dto;

import com.termProj.termProj.model.ItemEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemDTO {
    private String id;
    private String title;
    private String brand;
    private String type;

    public ItemDTO(final ItemEntity entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.brand = entity.getBrand();
        this.type = entity.getType();
    }

    public static ItemEntity toEntity(final ItemDTO dto) {
        return ItemEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .brand(dto.getBrand())
                .type(dto.getType())
                .build();

    }
}
