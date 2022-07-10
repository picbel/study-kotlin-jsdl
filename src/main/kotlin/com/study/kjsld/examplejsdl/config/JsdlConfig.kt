package com.study.kjsld.examplejsdl.config

import com.linecorp.kotlinjdsl.QueryFactory
import com.linecorp.kotlinjdsl.QueryFactoryImpl
import com.linecorp.kotlinjdsl.query.creator.CriteriaQueryCreatorImpl
import com.linecorp.kotlinjdsl.query.creator.SubqueryCreatorImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManager

@Configuration
class JsdlConfig(
//    entityManager: EntityManager
) {
//    @Bean
//    fun queryFactory() = QueryFactoryImpl(
//        criteriaQueryCreator = CriteriaQueryCreatorImpl(entityManager),
//        subqueryCreator = SubqueryCreatorImpl()
//    )
}