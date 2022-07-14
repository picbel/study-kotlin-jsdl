package com.study.kjsld.examplejsdl.domain.post.repository

import com.study.kjsld.examplejsdl.domain.post.aggregate.Author
import com.study.kjsld.examplejsdl.util.DateAuditing
import java.time.Instant
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

class AuthorEntity(
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    val id : Long? = null,
    override val name: String,
    override val introduction: String,
    override val createAt: Instant,
    override val updateAt: Instant,
) : Author,DateAuditing {

}