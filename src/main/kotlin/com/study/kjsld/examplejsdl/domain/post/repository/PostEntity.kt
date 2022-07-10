package com.study.kjsld.examplejsdl.domain.post.repository


import com.study.kjsld.examplejsdl.domain.post.aggregate.Post
import com.study.kjsld.examplejsdl.util.DateAuditing
import java.time.Instant
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table
class PostEntity(
    @Id
    val id : Long?,
    override val title : String,
    override val content : String,
    override val author: String,
    override val createAt: Instant = Instant.now(),
    override val updateAt: Instant = Instant.now()
) : Post, DateAuditing {
    companion object {
        fun create(src: Post): PostEntity = with(src) {
            PostEntity(
                id = (src as? PostEntity)?.id,
                title = title,
                content = content,
                author = author,
            )
        }
    }
}