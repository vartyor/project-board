package com.fastcampus.project_board.repository;

import com.fastcampus.project_board.domain.Article;
import com.fastcampus.project_board.domain.QArticle;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArticleRepository extends
        JpaRepository<Article, Long>,
        QuerydslPredicateExecutor<Article>,
        QuerydslBinderCustomizer<QArticle>  // QClass가 들어가게 설정되어 있다.
{

    Page<Article> findByTitleContaining(String title, Pageable pageable);
    Page<Article> findByContentContaining(String content, Pageable pageable);
    Page<Article> findByUserAccount_UserIdContaining(String userId, Pageable pageable);
    Page<Article> findByUserAccount_NicknameContaining(String nickname, Pageable pageable);
    Page<Article> findByHashtag(String hashtag, Pageable pageable);

    @Override
    default void customize(QuerydslBindings bindings, QArticle root){
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.title, root.hashtag, root.content, root.createdAt, root.createdBy);
        // argument를 하나만 받는다. containsIgnoreCase는 대소문자 구분을 안한다(아래 구문).
//        bindings.bind(root.title).first(StringExpression::likeIgnoreCase);          // like '${v}', 수동으로 검색하고 싶을 때
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase);      // like '%${v}%', 관련된 검색어 모두 출력할 때
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);                // 시, 분, 초까지 모두 동일하게 검색해야 한다.
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }
}
