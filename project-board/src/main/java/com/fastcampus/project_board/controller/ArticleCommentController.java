package com.fastcampus.project_board.controller;

import com.fastcampus.project_board.DTO.UserAccountDto;
import com.fastcampus.project_board.DTO.request.ArticleCommentRequest;
import com.fastcampus.project_board.service.ArticleCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/comments")
@Controller
public class ArticleCommentController {

    private final ArticleCommentService articleCommentService;

    @PostMapping("/new")
    public String postNewArticleComment(ArticleCommentRequest articleCommentRequest) {
        //TODO: 인증 정보를 넣어줘야 한다.
        articleCommentService.saveArticleComment(articleCommentRequest.toDto(UserAccountDto.of(
                "vartyor", "pw", "varute1997@gmail.com", null, null
        )));

        return "redirect:/articles/" + articleCommentRequest.articleId();
    }

    @PostMapping("/{commentId}/delete")
    public String deleteArticleComment(@PathVariable Long commentId, Long articleId) {
        articleCommentService.deleteArticleComment(commentId);

        return "redirect:/articles/" + articleId;
    }
}
