package com.fastcampus.project_board.service;

import com.fastcampus.project_board.DTO.ArticleDto;
import com.fastcampus.project_board.DTO.ArticleUpdateDto;
import com.fastcampus.project_board.domain.Article;
import com.fastcampus.project_board.domain.type.SearchType;
import com.fastcampus.project_board.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 게시글")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @InjectMocks private ArticleService sut;
    @Mock private ArticleRepository articleRepository;

    @DisplayName("게시글을 검색하면 게시글 리스트를 반환한다.")
    @Test
    void givenSearchParameters_whenSearchingArticles_thenReturnsArticleList() {
        //given

        //when
        Page<ArticleDto> articles = sut.searchArticles(SearchType.TITLE, "search keyword"); // 제목, 본문, ID, 닉네임, 해시태그

        //then
        assertThat(articles).isNotNull();
    }

    @DisplayName("게시글을 조회하면 게시글을 반환한다.")
    @Test
    void givenArticleId_whenSearchingArticle_thenReturnsArticle() {
        //given

        //when
        ArticleDto articles = sut.searchArticle(1L); // 제목, 본문, ID, 닉네임, 해시태그

        //then
        assertThat(articles).isNotNull();
    }

    @DisplayName("게시글 정보를 입력하면 게시글을 생성한다.")
    @Test
    void givenArticleInfo_whenSavingArticle_thenSavesArticle() {
        //Given
        ArticleDto dto = ArticleDto.of(LocalDateTime.now(), "Vartyor", "title", "content", "#hashtag");
        // 실제로 저장이 잘 일어났는지 Mock으로 확인.
        given(articleRepository.save(any(Article.class))).willReturn(null); // articleRepository의 save()가 일어날 것이다를 보여주는 코드.

        //When
        sut.saveArticle(dto);

        //Then
        then(articleRepository).should().save(any(Article.class)); // 세이브 대상의 article에서 save()를 한번 호출했는가(any(Article.class))를 검사
    } // 여러 개의 레이어를 거쳐서 테스트한다(Unit Test 중에서 Sociable Test).
      // 해당 테스트는 Persistence Layer(데이터베이스)까지 가지 않는 Solitary Test이다.

    @DisplayName("게시글의 ID와 수정 정보를 입력하면 게시글을 수정한다.")
    @Test
    void givenArticleIdAndModifiedInfo_whenUpdatingArticle_thenUpdatesArticle() {
        //Given
        ArticleUpdateDto dto = ArticleUpdateDto.of("title", "content", "#hashtag");
        // 실제로 저장이 잘 일어났는지 Mock으로 확인.
        given(articleRepository.save(any(Article.class))).willReturn(null);

        //When
        sut.updateArticle(1L, dto);

        //Then
        then(articleRepository).should().save(any(Article.class));
    }

    @DisplayName("게시글의 ID를 입력하면 게시글을 삭제한다.")
    @Test
    void givenArticleId_whenDeletingArticle_thenDeletesArticle() {
        //Given
        willDoNothing().given(articleRepository).delete(any(Article.class)); // 아무 일도 하지 않는 코드적 명시

        //When
        sut.deleteArticle(1L);

        //Then
        then(articleRepository).should().delete(any(Article.class));
    }
}