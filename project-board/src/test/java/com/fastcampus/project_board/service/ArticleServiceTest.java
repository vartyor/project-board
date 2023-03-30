package com.fastcampus.project_board.service;

import com.fastcampus.project_board.DTO.ArticleDto;
import com.fastcampus.project_board.DTO.ArticleWithCommentsDto;
import com.fastcampus.project_board.DTO.UserAccountDto;
import com.fastcampus.project_board.domain.Article;
import com.fastcampus.project_board.domain.UserAccount;
import com.fastcampus.project_board.domain.type.SearchType;
import com.fastcampus.project_board.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 게시글")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @InjectMocks private ArticleService sut;
    @Mock private ArticleRepository articleRepository;

    @DisplayName("검색어 없이 게시글을 검색하면 게시글 페이지를 반환한다.")
    @Test
    void givenNoSearchParameters_whenSearchingArticles_thenReturnsArticlePage() {
        //given
        Pageable pageable = Pageable.ofSize(20);
        given(articleRepository.findAll(pageable)).willReturn(Page.empty());

        //when
        Page<ArticleDto> articles = sut.searchArticles(null,null, pageable); // 제목, 본문, ID, 닉네임, 해시태그

        //then
        assertThat(articles).isEmpty();
        then(articleRepository).should().findAll(pageable);
    }

    @DisplayName("검색어와 함께 게시글을 검색하면 게시글 페이지를 반환한다.")
    @Test
    void givenSearchParameters_whenSearchingArticles_thenReturnsArticlePage() {
        //given
        SearchType searchType = SearchType.TITLE;
        String keyword = "title";
        Pageable pageable = Pageable.ofSize(20);
        given(articleRepository.findByTitle(keyword,pageable)).willReturn(Page.empty());

        //when
        Page<ArticleDto> articles = sut.searchArticles(searchType,keyword, pageable); // 제목, 본문, ID, 닉네임, 해시태그

        //then
        assertThat(articles).isEmpty();
        then(articleRepository).should().findByTitle(keyword, pageable);
    }

    @DisplayName("게시글을 조회하면 게시글을 반환한다.")
    @Test
    void givenArticleId_whenSearchingArticle_thenReturnsArticle() {
        //given
        Long articleId = 1L;
        Article article = createArticle();

        given(articleRepository.findById(articleId)).willReturn(Optional.of(article));
        //when
       ArticleWithCommentsDto dto = sut.getArticle(articleId); // 제목, 본문, ID, 닉네임, 해시태그

        //then
        assertThat(dto)
                .hasFieldOrPropertyWithValue("title", article.getTitle())
                .hasFieldOrPropertyWithValue("content", article.getContent())
                .hasFieldOrPropertyWithValue("hashtag", article.getHashtag());
        then(articleRepository).should().findById(articleId);
    }

    @DisplayName("없는 게시글을 조회하면 에외를 던진다.")
    @Test
    void givenNonexistentArticleId_whenSearchingArticle_thenThrowsException() {
        //given
        Long articleId = 0L;
        given(articleRepository.findById(articleId)).willReturn(Optional.empty());

        //when
        Throwable t = catchThrowable(() -> sut.getArticle(articleId));

        //then
        assertThat(t)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("해당 게시글이 존재하지 않습니다 - articleId: " + articleId);
        then(articleRepository).should().findById(articleId);
    }

    @DisplayName("게시글 정보를 입력하면 게시글을 생성한다.")
    @Test
    void givenArticleInfo_whenSavingArticle_thenSavesArticle() {
        //Given
        ArticleDto dto = createArticleDto();
        given(articleRepository.save(any(Article.class))).willReturn(createArticle()); // articleRepository의 save()가 일어날 것이다를 보여주는 코드.

        //When
        sut.saveArticle(dto);

        //Then
        then(articleRepository).should().save(any(Article.class)); // 세이브 대상의 article에서 save()를 한번 호출했는가(any(Article.class))를 검사
    } // 여러 개의 레이어를 거쳐서 테스트한다(Unit Test 중에서 Sociable Test).
      // 해당 테스트는 Persistence Layer(데이터베이스)까지 가지 않는 Solitary Test이다.

    @DisplayName("게시글의 수정 정보를 입력하면 게시글을 수정한다.")
    @Test
    void givenModifiedInfo_whenUpdatingArticle_thenUpdatesArticle() {
        //Given
        Article article = createArticle();
        ArticleDto dto = createArticleDto("새 타이틀", "새 내용", "#springboot");
        given(articleRepository.getReferenceById(dto.id())).willReturn(article);

        //When
        sut.updateArticle(dto);

        //Then
        assertThat(article)
                .hasFieldOrPropertyWithValue("title", dto.title())
                .hasFieldOrPropertyWithValue("content", dto.content())
                .hasFieldOrPropertyWithValue("hashtag", dto.hashtag());
        then(articleRepository).should().getReferenceById(dto.id());
    }

    @DisplayName("없는 게시글의 수정 정보를 입력하면 경고 로그를 찍고 아무 것도 하지 않는다.")
    @Test
    void givenNonexistentArticleInfo_whenUpdatingArticle_thenLogsWarningAndDoesNothing() {
        //Given
        ArticleDto dto = createArticleDto("새 타이틀", "새 내용", "새 해시태그");
        given(articleRepository.getReferenceById(dto.id())).willThrow(EntityNotFoundException.class);

        //When
        sut.updateArticle(dto);

        //Then
        then(articleRepository).should().getReferenceById(dto.id());
    }

    @DisplayName("게시글의 ID를 입력하면 게시글을 삭제한다.")
    @Test
    void givenArticleId_whenDeletingArticle_thenDeletesArticle() {
        //Given
        Long articleId = 1L;
        willDoNothing().given(articleRepository).deleteById(articleId); // 아무 일도 하지 않는 코드적 명시

        //When
        sut.deleteArticle(1L);

        //Then
        then(articleRepository).should().deleteById(articleId);
    }

    private UserAccount createUserAccount() {
        return UserAccount.of(
                "vartyor",
                "password",
                "varute@gmail.com",
                "Vartyor",
                null
        );
    }

    private Article createArticle(){
        return Article.of(
                createUserAccount(),
                "title",
                "cotent",
                "#java"
        );
    }

    private ArticleDto createArticleDto(){
        return createArticleDto("title", "content", "#java");
    }

    private ArticleDto createArticleDto(String title, String content, String hashtag) {
        return ArticleDto.of(
          createUserAccountDto(),
                title,
                content,
                hashtag,
                LocalDateTime.now(),
                "Vartyor",
                LocalDateTime.now(),
                "Vartyor");
    }

    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(
                1L,
                "vartyor",
                "password",
                "varute@gmail.com",
                "Vartyor",
                "This is memo",
                LocalDateTime.now(),
                "vartyor",
                LocalDateTime.now(),
                "vartyor"
        );
    }
}