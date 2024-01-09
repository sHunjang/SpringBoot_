package org.example.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import org.example.springbootdeveloper.domain.Article;
import org.example.springbootdeveloper.dto.ArticleListViewResponse;
import org.example.springbootdeveloper.dto.ArticleViewResponse;
import org.example.springbootdeveloper.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BlogViewController {

    private final BlogService blogService;

    // /articles GET 요청을 처리할 코드
    @GetMapping("/articles")
    public String getArticles(Model model) {
        List<ArticleListViewResponse> articles = blogService.findAll()
                .stream()
                .map(ArticleListViewResponse::new)
                .toList();
        model.addAttribute("articles", articles);  // 1. 블로그 글 리스트 저장
        // addAttribute() : 모델에 값을 저장 + "articles" 키에 블로그 글 리스트를 저장함

        return "articleList";  // 2. articleList.html 뷰 조회
        // 반환값 "articleList" : resource/templates/articleList.html 을 찾도록 뷰의 이름을 지정함
    }

    @GetMapping("/articles/{id}")
    // getArticle() 메서드 : 인자 id에 URL로 넘어온 값을 받아 findById() 메서드로 넘겨 글을 조회
    // + 화면에서 사용할 모델에 데이터를 저장
    // 보여줄 화면의 템플릿 이름을 반환
    public String getArticle(@PathVariable Long id, Model model) {
        Article article = blogService.findById(id);
        model.addAttribute("article", new ArticleViewResponse(article));

        return "article";
    }


    @GetMapping("/new-article")
    // 1. id 키를 가진 쿼리 파라미터의 값을 id 변수에 매핑 (id는 없을 수도 있음)
    // id 가 있으면 수정 -> 기존 값을 가져오는 findById() 메서드를 호출
    public String newArticle(@RequestParam(required = false) Long id, Model model) {
        if (id == null) {  // 2. id가 없으면 생성
            // 기본 생성자를 이용해 빈 ArticleViewResponse 객체를 만듬
            model.addAttribute("article", new ArticleViewResponse());
        } else {  // 3. id가 없으면 수정
            Article article = blogService.findById(id);
            model.addAttribute("article", new ArticleViewResponse(article));
        }

        return "newArticle";
    }
}