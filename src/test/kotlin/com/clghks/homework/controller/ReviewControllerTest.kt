package com.clghks.homework.controller

import com.clghks.homework.model.Review
import com.clghks.homework.model.ReviewRequest
import com.clghks.homework.repository.ReviewRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
class ReviewControllerTest(
    @Autowired
    private val mockMvc: MockMvc,
    @Autowired
    private val reviewRepository: ReviewRepository
) {
    @Test
    fun `리뷰 조회`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/v1/review/1")
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.pageNo", Matchers.`is`(0)))
    }

    @Test
    fun `리뷰 등록`() {
        val content = "리뷰 등록 테스트"
        mockMvc.perform(
            MockMvcRequestBuilders.post("/v1/review/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(ObjectMapper().writeValueAsString(ReviewRequest(
                    content = content,
                    image = "https://test/image",
                    width = 200,
                    height = 200
                )))
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.`is`(content)))
    }

    @Test
    fun `이미지 정보 없는 리뷰 등록`() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/v1/review/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(ObjectMapper().writeValueAsString(ReviewRequest(
                    content = "리뷰 등록 테스트",
                    image = null,
                    width = 200,
                    height = 200
                )))
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().is4xxClientError)
    }

    @Test
    fun `리뷰 수정`() {
        val review = reviewRepository.save(Review().apply {
            goodsNo = 1
            content = "리뷰 수정 테스트"
            image = "https://test/image"
            width = 200
            height = 200
        })

        val reviewContent = "리뷰 수정"
        mockMvc.perform(
            MockMvcRequestBuilders.put("/v1/review/${review.goodsNo}/${review.no}")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(ObjectMapper().writeValueAsString(ReviewRequest(
                    content = reviewContent,
                    image = review.image,
                    width = review.width,
                    height = review.height
                )))
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.`is`(reviewContent)))
    }

    @Test
    fun `존재하지 않는 리뷰 수정`() {
        mockMvc.perform(
            MockMvcRequestBuilders.put("/v1/review/2/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(ObjectMapper().writeValueAsString(ReviewRequest(
                    content = "리뷰 수정 테스트",
                    image = "https://test/image",
                    width = 200,
                    height = 200
                )))
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound)
    }

    @Test
    fun `리뷰 삭제`() {
        val review = reviewRepository.save(Review().apply {
            goodsNo = 1
            content = "리뷰 삭제 테스트"
            image = "https://test/image"
            width = 200
            height = 200
        })
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/v1/review/${review.goodsNo}/${review.no}")
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `존재하지 않는 리뷰 삭제`() {
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/v1/review/2/1")
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound)
    }
}