package com.study.kjsld.examplejsdl.domain.post.repository

import io.github.serpro69.kfaker.Faker
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa
import org.springframework.boot.test.context.SpringBootTest


@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@SpringBootTest // @DataJpaTest로 바꿔야하는데 지금 이게 중요한게 아니다
class PostRepositoryImplTest {

    @Autowired
    private lateinit var sut: PostRepositoryImpl

    companion object {
        val gon = AuthorEntity(authorId = 1L, name = "gon", introduction = "hi")
        val lyn = AuthorEntity(authorId = 2L, name = "lyn", introduction = "hi2")

        @BeforeAll
        @JvmStatic
        fun beforeAll(
            @Autowired dao: PostJpaDao,
            @Autowired authorDao: AuthorJpaDao,
        ) {
            println("beforeAll")
            authorDao.save(gon)
            authorDao.save(lyn)

            repeat(7) {
                with(Faker()) {
                    PostEntity(
                        title = it.toString(),
                        content = artist.names(),
                        author = gon
                    ).run {
                        dao.save(this)
                    }
                }
            }
            repeat(5) {
                with(Faker()) {
                    PostEntity(
                        title = it.toString(),
                        content = artist.names(),
                        author = lyn
                    ).run {
                        dao.save(this)
                    }
                }

            }
        }
    }

    @Test
    fun `제목과 작가를 null로 조회합니다`() {
        //given //when
        val result = sut.findByTitleOrAuthor(null, null)
        // then
        assertEquals(result.size, 12)
    }

    @Test
    fun `제목과 작가를 입력하여 조회합니다`() {
        //given //when
        val result = sut.findByTitleOrAuthor("1", "lyn")
        //then
        assertEquals(result.size, 1)

    }

    @Test
    fun `제목만 조회합니다`() {
        //given //when
        val result = sut.findByTitleOrAuthor("1", null)
        // then
        assertEquals(result.size, 2)
    }

    @Test
    fun `작가만 조회합니다`() {
        //given //when
        val result = sut.findByTitleOrAuthor(null, "gon")
        //then
        assertEquals(result.size, 7)
    }


}