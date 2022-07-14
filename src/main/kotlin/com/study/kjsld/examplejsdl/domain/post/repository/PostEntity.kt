package com.study.kjsld.examplejsdl.domain.post.repository


import com.study.kjsld.examplejsdl.domain.post.aggregate.Author
import com.study.kjsld.examplejsdl.domain.post.aggregate.Post
import com.study.kjsld.examplejsdl.util.DateAuditing
import java.time.Instant
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table
class PostEntity(
    @Id @GeneratedValue(strategy= GenerationType.AUTO)
    val id : Long? = null,
    override val title : String,
    override val content : String,
    override val author: AuthorEntity,
    override val createAt: Instant = Instant.now(),
    override val updateAt: Instant = Instant.now()
) : Post, DateAuditing, Author by author{


//    override fun toString(): String {
//        return "PostEntity(id=$id, title='$title', content='$content', author='$author', createAt=$createAt, updateAt=$updateAt)"
//    }

    companion object {
        fun create(src: Post): PostEntity = with(src) {
            PostEntity(
                id = (src as? PostEntity)?.id,
                title = title,
                content = content,
            )
        }
    }
}