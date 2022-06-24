package com.clghks.homework.model

import javax.validation.constraints.NotBlank

data class ReviewRequest(
    val content: String?,
    @get:NotBlank(message = "이미지는 필수 값입니다.")
    val image: String?,
    val width: Int?,
    val height: Int?
)