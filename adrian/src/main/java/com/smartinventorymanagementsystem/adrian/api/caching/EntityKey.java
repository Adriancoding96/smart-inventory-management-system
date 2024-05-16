package com.smartinventorymanagementsystem.adrian.api.caching;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EntityKey<T> {
    private final Class<?> entityType;
    private final T id;

}
