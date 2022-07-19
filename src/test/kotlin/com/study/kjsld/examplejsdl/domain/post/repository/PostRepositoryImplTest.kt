package com.study.kjsld.examplejsdl.domain.post.repository

import io.github.serpro69.kfaker.Faker
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import javax.transaction.Transactional


@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@SpringBootTest // @DataJpaTest로 바꿔야하는데 지금 이게 중요한게 아니다
class PostRepositoryImplTest {

    @Autowired
    private lateinit var sut: PostRepositoryImpl

    @Autowired
    private lateinit var dao: PostJpaRepository

    @Autowired
    private lateinit var authorDao: AuthorJpaRepository

    @Autowired
    private lateinit var postViewJpaDao: PostViewJpaDao

    @Autowired
    private lateinit var em: EntityManager


    companion object {
        val gon = AuthorEntity(authorId = 1L, name = "gon", introduction = "hi")
        val lyn = AuthorEntity(authorId = 2L, name = "lyn", introduction = "hi2")

        @BeforeAll
        @JvmStatic
        fun beforeAll(
            @Autowired dao: PostJpaRepository,
            @Autowired authorDao: AuthorJpaRepository,
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

    @Test @Transactional
    fun lazyTest() {

        var targetId : Long
        val aaa = AuthorEntity(authorId = 3L, name = "aaa", introduction = "hi2")

        println("=== PostViewEntity ===")
        PostViewEntity(
            post = PostEntity(
                title = "저장잘됨?",
                content = "ㅁㅁㅁㅁ",
                author = authorDao.save(aaa)
            )
        ).run {

            postViewJpaDao.save(this).also { targetId = this.viewId!! }
            authorDao.flush()
            postViewJpaDao.flush()
        }
//            PostViewEntity(
//                post = PostEntity(
//                    title = "저장잘됨?222",
//                    content = "ㅁㅁㅁㅁ222",
//                    author = aaa
//                )
//            ).run {
//                postViewJpaDao.save(this)
//            }
        println("=== PostViewEntity end ===")

        em.clear()
        println("=== PostViewEntity select ===")
        postViewJpaDao.findById(targetId).let {
            println("postId = ${it.get().post.postId}, viewId=${it.get().viewId}")
            println("postId = ${it.get().post.title}, viewId=${it.get().viewId}")
            println("postId = ${it.get().post.author.name}, viewId=${it.get().viewId}")
        }
        println("=== PostViewEntity select end ===")
    }



    @Test
    fun `제목과 작가를 null로 조회합니다`() {
        //given

        //when
        val result = sut.findByTitleAndAuthor(null, null)
        // then
        assertEquals(result.size, 13) // test 끝나면 12로 수정 해야함
    }

    @Test
    fun `제목과 작가를 입력하여 조회합니다`() {
        //given

        //when
        val result = sut.findByTitleAndAuthor("1", "lyn")
        //then
        assertEquals(result.size, 1)

    }

    @Test
    fun `제목만 조회합니다`() {
        //given

        //when
        val result = sut.findByTitleAndAuthor("1", null)
        // then
        assertEquals(result.size, 2)
    }

    @Test
    fun `작가만 조회합니다`() {
        //given

        //when
        val result = sut.findByTitleAndAuthor(null, "gon")
        //then
        assertEquals(result.size, 7)
    }


}