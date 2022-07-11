package com.study.kjsld.examplejsdl.domain.post.repository


import com.linecorp.kotlinjdsl.query.spec.predicate.PredicateSpec
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.querydsl.SpringDataCriteriaQueryDsl
import com.linecorp.kotlinjdsl.spring.data.singleQuery
import com.study.kjsld.examplejsdl.domain.post.aggregate.Post
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

interface PostRepository {
    fun save(post: Post) : Post

    fun findByTitleAndAuthor(title: String?, author: String?) : Post
}

@Repository
internal class PostRepositoryImpl(
    private val jpaRepo: PostJpaRepository,
    private val queryFactory: SpringDataQueryFactory,
) : PostRepository {
    @Transactional
    override fun save(post: Post): Post = jpaRepo.save(PostEntity.create(post))

    override fun findByTitleAndAuthor(title: String?, author: String?): Post {

        return queryFactory.singleQuery<PostEntity> {
            select(entity(PostEntity::class))
            from(entity(PostEntity::class))
            where(this.andFactory(title, author))
        }

    }

}

// 이런식으로 동적쿼리 작성 가능하다
private fun <T> SpringDataCriteriaQueryDsl<T>.andFactory(title: String?, author: String?): PredicateSpec {
    return and(
        title?.let { column(PostEntity::title).equal(title) },
        author?.let { column(PostEntity::author).equal(author)}
    )
}
