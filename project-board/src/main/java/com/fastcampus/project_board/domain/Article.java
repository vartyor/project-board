package com.fastcampus.project_board.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // JPA persistence context가 영속화를 할 때 자동으로 부여하는 고유 번호


    @Setter @Column(nullable = false) private String title; // 제목
    @Setter @Column(nullable = false, length = 10000) private String content; // 본문

    @Setter private String hashtag; // 해시태그

    @ToString.Exclude // 퍼포먼스나 메모리 저하 발생을 방지, 순환 참조를 방지
    @OrderBy("id")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();
    // Article에 연동되어있는 Comment는 중복을 허용하지 않고 모아서 Collection으로 보겠다.

    // 자동으로 JPA가 세팅하게 하려면 메타 데이터들은 @Setter를 사용하지 않는다.
    @CreatedDate @Column(nullable = false) private LocalDateTime createdAt; // 생성일시
    @CreatedBy @Column(nullable = false, length = 100) private String createdBy; // 생성자
    @LastModifiedDate @Column(nullable = false) private LocalDateTime modifiedAt; // 수정일시
    @LastModifiedBy @Column(nullable = false, length = 100) private String modifiedBy; // 수정자


    // 기본 생성자(Hibernate 기준)
    protected Article(){}

    public Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    // 도메인 Article을 생성하고자 할 때는 어떤 값을 필요로 하는 지를 guide 하는 역할
    public static Article of(String title, String content, String hashtag) {
        return new Article(title,content,hashtag);
    }

    // 동일성, 동등성 검사를 할 수 있는 equals()랑 hashCode() 구현

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false;
        return id != null && id.equals(article.id);
        // id 가 null인 경우는 영속화가 일어나지 않아 entity가 구성되지 않은 시점
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
