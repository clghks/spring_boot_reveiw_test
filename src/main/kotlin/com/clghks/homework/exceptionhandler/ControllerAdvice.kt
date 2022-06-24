package com.clghks.homework.exceptionhandler

import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected fun handlePartnerException(e: MethodArgumentNotValidException): ExceptionResponse {
        return ExceptionResponse(
            message = e.fieldError?.defaultMessage ?: "입력 값을 확인해주세요.",
            httpStatus = HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(ContentNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected fun handlePartnerException(e: Exception): ExceptionResponse {
        return ExceptionResponse(
            message = "요청한 컨텐츠가 없습니다.",
            httpStatus = HttpStatus.NOT_FOUND
        )
    }

}