package com.clghks.homework.exceptionhandler

import org.springframework.http.HttpStatus

data class ExceptionResponse (
    val message: String,
    val httpStatus: HttpStatus
)