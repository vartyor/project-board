package com.fastcampus.project_board.service;

import com.fastcampus.project_board.DTO.ArticleCommentDto;
import com.fastcampus.project_board.DTO.UserAccountDto;
import com.fastcampus.project_board.repository.ArticleCommentRepository;
import com.fastcampus.project_board.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleCommentService {
    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    @Transactional(readOnly = true)
    public List<ArticleCommentDto> searchArticleComments(long articleId) {
        return List.of();
    }

    public void saveArticleComment(ArticleCommentDto dto) {
    }

    public void updateArticleComment(ArticleCommentDto dto){
    }

    public void deleteArticleComment(Long articleCommentId){
    }
}
