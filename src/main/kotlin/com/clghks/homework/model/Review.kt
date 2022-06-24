package com.clghks.homework.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "Review")
@EntityListeners(AuditingEntityListener::class)
class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var no: Int? = null

    @Column(nullable = false)
    var goodsNo: Int = 0

    @Column(length = 100)
    var content: String? = null

    @Column(nullable = false)
    var image: String? = null

    var width: Int? = null

    var height: Int? = null

    @CreatedDate
    var createdAt: LocalDateTime? = null

    @LastModifiedDate
    var updatedAt : LocalDateTime? = null

    var deleteAt : LocalDateTime? = null
}