package com.fastcampus.project_board.DTO;

import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.fastcampus.project_board.domain.Article} entity
 */
public record ArticleDto(
        LocalDateTime createdAt,
        String createdBy,
        String title,
        String content,
        String hashtag
) {
    public static ArticleDto of(
            LocalDateTime createdAt,
            String createdBy,
            String title,
            String content,
            String hashtag
    ) {
        return new ArticleDto(createdAt, createdBy, title, content, hashtag);
    } // 표준 생성자로 생성 후 static ~ of 사용

}
