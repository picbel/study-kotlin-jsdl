package com.study.kjsld.examplejsdl.domain.post.aggregate

import com.study.kjsld.examplejsdl.domain.post.repository.AuthorEntity

interface Post : Author{
    val title : String
    val content : String
}