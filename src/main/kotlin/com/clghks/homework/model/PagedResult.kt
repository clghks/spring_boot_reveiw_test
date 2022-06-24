package com.clghks.homework.model

data class PagedResult<T>(
    val pageNo: Int,
    val pageSize: Int,
    val total: Long,
    val totalPages: Int,
    val results: List<T>
)