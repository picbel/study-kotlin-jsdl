package com.study.kjsld.examplejsdl.domain.post.repository


import com.linecorp.kotlinjdsl.query.spec.predicate.PredicateSpec
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.querydsl.from.fetch
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import com.linecorp.kotlinjdsl.spring.data.querydsl.SpringDataCriteriaQueryDsl
import com.linecorp.kotlinjdsl.spring.data.singleQuery
import com.study.kjsld.examplejsdl.domain.post.aggregate.Post
import org.springframework.data.jpa.domain.Specification.where
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

interface PostRepository {
    fun save(post: Post) : Post

    fun findByTitleAndAuthor(title: String?, authorName: String?) : List<Post>
}

@Repository
internal class PostRepositoryImpl(
    private val jpaRepo: PostJpaRepository,
    private val queryFactory: SpringDataQueryFactory,
) : PostRepository {
    @Transactional
    override fun save(post: Post): Post = jpaRepo.save(PostEntity.create(post))

    override fun findByTitleAndAuthor(title: String?, authorName: String?): List<Post> {
        return queryFactory.listQuery<PostEntity> {
            select(entity(PostEntity::class))
            from(entity(PostEntity::class))
            fetch(PostEntity::author)
            where(this.andFactory(title, authorName))

        }
    }

}

// 이런식으로 동적쿼리 작성 가능하다
private fun <T> SpringDataCriteriaQueryDsl<T>.andFactory(title: String?, authorName: String?): PredicateSpec {
    return and(
        title?.let { column(PostEntity::title).equal(title) },
        authorName?.let { column(AuthorEntity::name).equal(authorName)}
    )
}
