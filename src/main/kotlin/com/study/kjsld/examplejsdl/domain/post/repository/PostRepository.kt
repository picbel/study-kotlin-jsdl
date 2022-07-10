package com.study.kjsld.examplejsdl.domain.post.repository

import com.linecorp.kotlinjdsl.query.spec.expression.ColumnSpec
import com.linecorp.kotlinjdsl.query.spec.predicate.EqualValueSpec
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import com.linecorp.kotlinjdsl.spring.data.singleQuery
import com.study.kjsld.examplejsdl.domain.post.aggregate.Post
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.stereotype.Repository

interface PostRepository {
    fun save(post: Post) : Post

    fun findByTitleAndAuthor(title: String, author: String) : List<Post>
}

@Repository
internal class PostRepositoryImpl(
    private val jpaRepo: PostJpaRepository,
    private val queryFactory: SpringDataQueryFactory,
) : PostRepository {
    override fun save(post: Post): Post = jpaRepo.save(PostEntity.create(post))

    override fun findByTitleAndAuthor(title: String, author: String): List<Post> {
        return queryFactory.listQuery<PostEntity> {
            select(entity(PostEntity::class))
            from(entity(PostEntity::class))
            where(column(PostEntity::author).equal(author))
        }

    }

}