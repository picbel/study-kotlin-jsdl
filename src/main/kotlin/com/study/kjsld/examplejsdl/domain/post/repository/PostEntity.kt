package com.study.kjsld.examplejsdl.domain.post.repository


import com.study.kjsld.examplejsdl.domain.post.aggregate.Post
import com.study.kjsld.examplejsdl.util.DateAuditing
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import java.time.Instant
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.MapsId
import javax.persistence.NamedAttributeNode
import javax.persistence.NamedEntityGraph
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table
class PostEntity(
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    val postId : Long? = null,
    override val title : String,
    override val content : String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authorId")
    override val author: AuthorEntity,
    override val createAt: Instant = Instant.now(),
    override val updateAt: Instant = Instant.now()
) : Post,DateAuditing{

//    override fun toString(): String {
//        return "PostEntity(id=$id, title='$title', content='$content', author='$author', createAt=$createAt, updateAt=$updateAt)"
//    }

    companion object {
        fun create(src: Post): PostEntity = with(src) {
            PostEntity(
                postId = (src as? PostEntity)?.postId,
                title = title,
                content = content,
                author = author as AuthorEntity
            )
        }
    }
}

@Entity
@Table
class PostViewEntity(
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    val viewId : Long? = null,

    @OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], optional = false)
    @JoinColumn(name = "postId")
    val post: PostEntity,

    val view: Long = 0
)

interface PostViewJpaDao : JpaRepository<PostViewEntity,Long>
