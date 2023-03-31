package com.fastcampus.project_board.service;

import com.fastcampus.project_board.DTO.ArticleDto;
import com.fastcampus.project_board.DTO.ArticleWithCommentsDto;
import com.fastcampus.project_board.domain.type.SearchType;
import com.fastcampus.project_board.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor    // 필수 생성자를 만들어주는 Lombok의 기능
@Transactional
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType searchType, String search_keyword, Pageable pageable) {
        return Page.empty();
    }

    @Transactional(readOnly = true)
    public ArticleWithCommentsDto getArticle(Long articleId) {
        return null;
    }

    public void saveArticle(ArticleDto dto) {
    }

    public void updateArticle(ArticleDto dto) {
    }

    public void deleteArticle(long articleId) {
    }

}
