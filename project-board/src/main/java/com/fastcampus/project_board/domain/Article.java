package com.fastcampus.project_board.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString(callSuper = true)
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class Article extends AuditingFields{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // JPA persistence context가 영속화를 할 때 자동으로 부여하는 고유 번호

    @Setter @ManyToOne(optional = false) private UserAccount userAccount; // 유저 정보(ID)

    @Setter @Column(nullable = false) private String title; // 제목
    @Setter @Column(nullable = false, length = 10000) private String content; // 본문

    @Setter private String hashtag; // 해시태그

    @ToString.Exclude // 퍼포먼스나 메모리 저하 발생을 방지, 순환 참조를 방지
    @OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();


    // 기본 생성자(Hibernate 기준)
    protected Article(){}

    public Article(UserAccount userAccount, String title, String content, String hashtag) {
        this.userAccount = userAccount;
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    // 도메인 Article을 생성하고자 할 때는 어떤 값을 필요로 하는 지를 guide 하는 역할
    public static Article of(UserAccount userAccount, String title, String content, String hashtag) {
        return new Article(userAccount, title, content, hashtag);
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
