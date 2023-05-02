package com.fastcampus.project_board.DTO.request;

import com.fastcampus.project_board.DTO.ArticleCommentDto;
import com.fastcampus.project_board.DTO.UserAccountDto;

public record ArticleCommentRequest(Long articleId, String content) {

    public static ArticleCommentRequest of(Long articleId, String content) {
        return new ArticleCommentRequest(articleId, content);
    }

    public ArticleCommentDto toDto(UserAccountDto userAccountDto) {
        return ArticleCommentDto.of(
                articleId,
                userAccountDto,
                content
        );
    }
}
