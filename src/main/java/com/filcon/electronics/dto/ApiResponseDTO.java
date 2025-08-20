// src/main/java/com/filcon/electronics/dto/ApiResponseDTO.java
package com.filcon.electronics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseDTO<T> {
    private boolean success;
    private String message;
    private T data;
    private Long timestamp;

    public static <T> ApiResponseDTO<T> success(String message, T data) {
        return new ApiResponseDTO<>(true, message, data, System.currentTimeMillis());
    }

    public static <T> ApiResponseDTO<T> error(String message) {
        return new ApiResponseDTO<>(false, message, null, System.currentTimeMillis());
    }
}