package com.e201.kidswallet.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public class RestApiException extends RuntimeException {
    private final StatusCode statusCode;
}
