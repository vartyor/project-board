package com.fastcampus.project_board.DTO;

/**
 * A DTO for the {@link com.fastcampus.project_board.domain.Article} entity
 */
public record ArticleUpdateDto(
        String title,
        String content,
        String hashtag
) {
    public static ArticleUpdateDto of(String title, String content, String hashtag) {
        return new ArticleUpdateDto(title, content, hashtag);
    } // 표준 생성자로 생성 후 static ~ of 사용
}