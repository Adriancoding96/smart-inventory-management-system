package com.smartinventorymanagementsystem.adrian.dtos;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)

@JsonSubTypes({
        @JsonSubTypes.Type(value = CategoryDTO.class, name = "categoryDTO"),
        @JsonSubTypes.Type(value = ProductDTO.class, name = "productDTO"),
        @JsonSubTypes.Type(value = ProductImageDTO.class, name = "productImageDTO"),
        @JsonSubTypes.Type(value = ProductWithImagesDTO.class, name = "productWithImagesDTO"),
        @JsonSubTypes.Type(value = RoleDTO.class, name = "roleDTO"),
        @JsonSubTypes.Type(value = UserDTO.class, name = "userDTO")
})

public abstract class BaseDTO {
}
