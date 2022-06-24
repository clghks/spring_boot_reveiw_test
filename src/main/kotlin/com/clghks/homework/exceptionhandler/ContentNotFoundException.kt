package com.clghks.homework.exceptionhandler

class ContentNotFoundException(override val message: String?) : RuntimeException(message)