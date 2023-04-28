package com.fastcampus.project_board.DTO.request;

import com.fastcampus.project_board.DTO.ArticleDto;
import com.fastcampus.project_board.DTO.UserAccountDto;
import com.fastcampus.project_board.domain.Article;

public record ArticleRequest(
        String title,
        String content,
        String hashtag
) {
    public static ArticleRequest of(String title, String content,String hashtag) {
        return new ArticleRequest(title, content, hashtag);
    }

    public ArticleDto toDto(UserAccountDto userAccountDto) {
        return ArticleDto.of(
                userAccountDto,
                title,
                content,
                hashtag
        );
    }
}
