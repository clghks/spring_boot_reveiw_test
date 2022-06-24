package com.clghks.homework.service

import com.clghks.homework.exceptionhandler.ContentNotFoundException
import com.clghks.homework.extensions.getNullPropertyNames
import com.clghks.homework.model.*
import com.clghks.homework.repository.ReviewRepository
import org.springframework.beans.BeanUtils
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ReviewService(
    private val reviewRepository: ReviewRepository
) {
    fun getReviews(goodsNo: Int, paging: Paging): PagedResult<ReviewResponse> {
        val reviewList = reviewRepository.findByGoodsNoAndDeleteAtIsNull(goodsNo, PageRequest.of(paging.page, paging.size))
        return PagedResult(
            pageNo = reviewList.number,
            pageSize = reviewList.size,
            total = reviewList.totalElements,
            totalPages = reviewList.totalPages,
            results = reviewList.map { convertReviewResponse(it) }.toList()
        )
    }

    fun createReview(goodsNo: Int, request: ReviewRequest): ReviewResponse {
        val review = Review()
        review.goodsNo = goodsNo
        BeanUtils.copyProperties(request, review)
        return convertReviewResponse(reviewRepository.save(review))
    }

    private fun convertReviewResponse(review: Review): ReviewResponse {
        val reviewResponse = ReviewResponse()
        BeanUtils.copyProperties(review, reviewResponse)
        return reviewResponse
    }

    fun updateReview(goodsNo: Int, reviewNo: Int, request: ReviewRequest): ReviewResponse {
        val review = reviewRepository.findByNoAndGoodsNoAndDeleteAtIsNull(reviewNo, goodsNo) ?: throw ContentNotFoundException("요청한 컨텐츠가 없습니다.")
        BeanUtils.copyProperties(request, review, *request.getNullPropertyNames())
        return convertReviewResponse(reviewRepository.save(review))
    }

    fun deleteReview(goodsNo: Int, reviewNo: Int) {
        val review = reviewRepository.findByNoAndGoodsNoAndDeleteAtIsNull(reviewNo, goodsNo) ?: throw ContentNotFoundException("요청한 컨텐츠가 없습니다.")
        review.deleteAt = LocalDateTime.now()
        reviewRepository.save(review)
    }
}