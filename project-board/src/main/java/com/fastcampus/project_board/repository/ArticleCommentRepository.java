package com.fastcampus.project_board.repository;

import com.fastcampus.project_board.domain.ArticleComment;
import com.fastcampus.project_board.domain.QArticleComment;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArticleCommentRepository extends
        JpaRepository<ArticleComment, Long>,
        QuerydslPredicateExecutor<ArticleComment>, // 기본 검색 구현(부분 검색 및 대소문자 구분X)
        QuerydslBinderCustomizer<QArticleComment>   // 입맛에 맞는 검색 기능 구현을 위해 사용
{
    @Override
    default void customize(QuerydslBindings bindings, QArticleComment root){
        bindings.excludeUnlistedProperties(true); // 리스트화하지 않은 porperty는 검색에서 제외(true)
        bindings.including(root.content, root.createdAt, root.createdBy);// 원하는 필드 추가
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);                // 시, 분, 초까지 모두 동일하게 검색해야 한다.
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);

    }
}
