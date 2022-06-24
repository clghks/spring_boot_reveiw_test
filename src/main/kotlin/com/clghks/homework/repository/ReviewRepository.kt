package com.clghks.homework.repository

import com.clghks.homework.model.Review
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository : JpaRepository<Review, Int> {
    fun findByGoodsNoAndDeleteAtIsNull(goodsNo: Int, pageable: PageRequest): Page<Review>

    fun findByNoAndGoodsNoAndDeleteAtIsNull(reviewNo: Int, goodsNo: Int): Review?
}