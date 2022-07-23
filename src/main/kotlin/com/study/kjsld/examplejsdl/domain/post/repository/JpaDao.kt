package com.study.kjsld.examplejsdl.domain.post.repository

import org.springframework.data.jpa.repository.JpaRepository

interface PostJpaDao : JpaRepository<PostEntity, Long>

interface AuthorJpaDao : JpaRepository<AuthorEntity, Long>
