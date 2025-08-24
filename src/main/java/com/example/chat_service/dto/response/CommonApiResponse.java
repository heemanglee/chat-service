package com.example.chat_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class CommonApiResponse<T> {

    private int status;
    private T data;

    public static<T> CommonApiResponse<T> success(T data) {
        return new CommonApiResponse<>(HttpStatus.OK.value(), data);
    }

    public static<T> CommonApiResponse<T> success(HttpStatus status, T data) {
        return new CommonApiResponse<>(status.value(), data);
    }

}
