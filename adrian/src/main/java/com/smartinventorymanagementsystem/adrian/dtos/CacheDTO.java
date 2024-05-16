package com.smartinventorymanagementsystem.adrian.dtos;

import lombok.Data;

import java.util.Objects;

@Data
public class CacheDTO<T extends BaseDTO> {

    private String key;
    private T value;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CacheDTO<?> cacheDTO = (CacheDTO<?>) o;
        return Objects.equals(key, cacheDTO.key) &&
                Objects.equals(value, cacheDTO.value);
    }
}
