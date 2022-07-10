package com.study.kjsld.examplejsdl.domain.post.repository

import org.springframework.data.jpa.repository.JpaRepository

interface PostJpaRepository : JpaRepository<PostEntity, Long>
