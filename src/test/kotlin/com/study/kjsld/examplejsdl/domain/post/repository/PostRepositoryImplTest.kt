package com.study.kjsld.examplejsdl.domain.post.repository

import io.github.serpro69.kfaker.Faker
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration


@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@SpringBootTest // @DataJpaTest로 바꿔야하는데 지금 이게 중요한게 아니다
internal class PostRepositoryImplTest{

    @Autowired
    private lateinit var sut :PostRepositoryImpl

    @Autowired
    private lateinit var dao :PostJpaRepository

    @BeforeEach
    fun setup(){
        dao.deleteAll()
        repeat(10){
            with(Faker()){
                PostEntity(
                    title = it.toString(),
                    content = artist.names(),
                    author = "author$it"
                ).run {
                    sut.save(this)
                }
            }

        }
    }

    @Test
    fun `제목과 작가를 null로 조회합니다`(){
        sut.findByTitleAndAuthor(null,null).also {
            println(it)
        }
    }
    @Test
    fun `제목과 작가를 입력하여 조회합니다`(){
        sut.findByTitleAndAuthor("5","author5").also {
            println(it)
        }
    }
    @Test
    fun `제목만 조회합니다`(){
        sut.findByTitleAndAuthor("1",null).also {
            println(it)
        }
    }
    @Test
    fun `작가만 조회합니다`(){
        sut.findByTitleAndAuthor(null,"3").also {
            println(it)
        }
    }




}