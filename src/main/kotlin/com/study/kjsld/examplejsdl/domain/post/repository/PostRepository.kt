package com.study.kjsld.examplejsdl.domain.post.repository


import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import com.linecorp.kotlinjdsl.spring.data.querydsl.SpringDataCriteriaQueryDsl
import com.linecorp.kotlinjdsl.spring.data.updateQuery
import com.study.kjsld.examplejsdl.domain.post.aggregate.Post
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.data.jpa.domain.Specification.where
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import javax.persistence.Query

interface PostRepository {
    fun save(post: Post) : Post

    fun findByTitleAndAuthor(title: String, author: String) : List<Post>
}

@Repository
internal class PostRepositoryImpl(
    private val jpaRepo: PostJpaRepository,
    private val queryFactory: SpringDataQueryFactory,
) : PostRepository {
    @Transactional
    override fun save(post: Post): Post = jpaRepo.save(PostEntity.create(post))

    override fun findByTitleAndAuthor(title: String, author: String): List<Post> {

        return queryFactory.listQuery<PostEntity> {
            select(entity(PostEntity::class))
            from(entity(PostEntity::class))
            where(andSpec(author, title))
        }

    }

//    private fun aaaa(vararg check: Any?) {
//        if (check.filterNotNull().isEmpty()){throw Exception()}
//    }
//    private fun SpringDataCriteriaQueryDsl<PostEntity>.andSpec(
//        author: String,
//        title: String
//    ) = {
//        aaaa(author,title)
//        author?.let {
//            column(PostEntity::author).equal(author)
//        }
//
//        .and(column(PostEntity::title).equal(title))
//    }

    fun a(){
        val query: Query = queryFactory.updateQuery<PostEntity> {
            where(col(PostEntity::id).`in`(1, 2))
            setParams(col(PostEntity::title) to 3000)
        }

        val updatedRowsCount: Int = query.executeUpdate()
    }

}