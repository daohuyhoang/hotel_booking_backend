package com.daisy.daisy_hotel_backend.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class ResponseObject <T> extends ResponseEntity<ResponseObject.Payload<T>> {

    public ResponseObject(HttpStatusCode code, String message, T data) {
        super(new Payload<>(code.value(), message, data),code);
    }

    @Getter
    @Setter
    @Builder
    public static class Payload<T> {
        private int code;
        private String message;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private T data;
    }
}
