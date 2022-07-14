package com.study.kjsld.examplejsdl.domain.post.aggregate

import com.study.kjsld.examplejsdl.domain.post.repository.AuthorEntity

interface Post {
    val title : String
    val content : String
    val author : Author
}