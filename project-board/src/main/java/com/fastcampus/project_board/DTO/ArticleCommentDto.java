package com.fastcampus.project_board.DTO;

import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.fastcampus.project_board.domain.ArticleComment} entity
 */
public record ArticleCommentDto(
      LocalDateTime createdAt,
      String createdBy,
      LocalDateTime modifiedAt,
      String modifiedBy,
      String content
) {
    public static ArticleCommentDto of(LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy, String content) {
        return new ArticleCommentDto(createdAt, createdBy, modifiedAt, modifiedBy, content);
    }

}
