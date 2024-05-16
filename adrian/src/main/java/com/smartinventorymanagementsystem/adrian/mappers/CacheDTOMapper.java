package com.smartinventorymanagementsystem.adrian.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartinventorymanagementsystem.adrian.dtos.BaseDTO;
import com.smartinventorymanagementsystem.adrian.dtos.CacheDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CacheDTOMapper {

    private final ObjectMapper objectMapper;

    @Autowired
    public CacheDTOMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T extends BaseDTO> CacheDTO<T> fromJson(String json, Class<T> type) throws IOException {
        return objectMapper.readValue(json, objectMapper.getTypeFactory().constructParametricType(CacheDTO.class, type));
    }

    public <T extends BaseDTO> String toJson(CacheDTO<T> cacheDTO) throws IOException {
        return objectMapper.writeValueAsString(cacheDTO);
    }
}
