package com.study.kjsld.examplejsdl.domain.post.repository

import com.study.kjsld.examplejsdl.domain.post.aggregate.Author
import com.study.kjsld.examplejsdl.util.DateAuditing
import java.time.Instant
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table
class AuthorEntity(
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    val authorId : Long? = null,
    override val name: String,
    override val introduction: String,
    override val createAt: Instant = Instant.now(),
    override val updateAt: Instant = Instant.now(),
) : Author, DateAuditing {
    override fun toString(): String {
        return "AuthorEntity(authorId=$authorId, name='$name', introduction='$introduction', createAt=$createAt, updateAt=$updateAt)"
    }
}