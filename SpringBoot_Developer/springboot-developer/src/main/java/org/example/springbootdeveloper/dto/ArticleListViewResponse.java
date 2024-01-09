package org.example.springbootdeveloper.dto;

import lombok.Getter;
import org.example.springbootdeveloper.domain.Article;

import java.time.LocalDateTime;

// 요청을 받아 사용자에게 뷰를 보여주려면 뷰 컨트롤러가 필요함
// ArticleListViewResponse.java : 뷰 컨트롤러에 데이터 전달용

@Getter
public class ArticleListViewResponse {

    private final Long id;
    private final String title;
    private final String content;
    private LocalDateTime createdAt;

    public ArticleListViewResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.createdAt = article.getCreatedAt();
    }
}
