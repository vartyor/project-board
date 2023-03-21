package com.fastcampus.project_board.repository;

import com.fastcampus.project_board.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}