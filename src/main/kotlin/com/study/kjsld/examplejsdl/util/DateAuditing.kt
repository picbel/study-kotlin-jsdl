package com.study.kjsld.examplejsdl.util

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant

interface DateAuditing {

    @get:CreatedDate
    val createAt : Instant

    @get:LastModifiedDate
    val updateAt : Instant
}