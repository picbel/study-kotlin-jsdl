package com.study.kjsld.examplejsdl.domain.post.repository


import com.linecorp.kotlinjdsl.query.spec.predicate.PredicateSpec
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.querydsl.from.fetch
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import com.linecorp.kotlinjdsl.spring.data.querydsl.SpringDataCriteriaQueryDsl
import com.study.kjsld.examplejsdl.domain.post.aggregate.Post
import org.springframework.stereotype.Repository

interface PostRepository {
    fun findByTitleOrAuthor(title: String?, authorName: String?) : List<Post>
}

@Repository
internal class PostRepositoryImpl(
    private val queryFactory: SpringDataQueryFactory,
) : PostRepository {
    override fun findByTitleOrAuthor(title: String?, authorName: String?): List<Post> {
        return queryFactory.listQuery<PostEntity> {
            select(entity(PostEntity::class))
            from(entity(PostEntity::class))
            fetch(PostEntity::author)
            where(dynamicAndFactory(title, authorName))
        }
    }

}


private fun <T> SpringDataCriteriaQueryDsl<T>.dynamicAndFactory(title: String?, authorName: String?): PredicateSpec {
    return and(
        title?.let { column(PostEntity::title).equal(title) },
        authorName?.let { column(AuthorEntity::name).equal(authorName)}
    )
}
