package com.clghks.homework.controller

import com.clghks.homework.model.PagedResult
import com.clghks.homework.model.Paging
import com.clghks.homework.model.ReviewRequest
import com.clghks.homework.model.ReviewResponse
import com.clghks.homework.service.ReviewService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/v1/review")
class ReviewController(
    private val reviewService: ReviewService
) {
    @GetMapping("{goodsNo}")
    fun getReviews(@PathVariable goodsNo: Int, @ModelAttribute paging: Paging): PagedResult<ReviewResponse> {
        return reviewService.getReviews(goodsNo, paging)
    }

    @PostMapping("{goodsNo}")
    fun createReview(@PathVariable goodsNo: Int, @RequestBody @Valid request: ReviewRequest): ReviewResponse {
        return reviewService.createReview(goodsNo, request)
    }

    @PutMapping("{goodsNo}/{reviewNo}")
    fun updateReview(@PathVariable goodsNo: Int, @PathVariable reviewNo: Int, @RequestBody request: ReviewRequest): ReviewResponse {
        return reviewService.updateReview(goodsNo, reviewNo, request)
    }

    @DeleteMapping("{goodsNo}/{reviewNo}")
    fun deleteReview(@PathVariable goodsNo: Int, @PathVariable reviewNo: Int): ResponseEntity<*> {
        reviewService.deleteReview(goodsNo, reviewNo)
        return ResponseEntity.ok().body("리뷰 삭제 완료")
    }
}