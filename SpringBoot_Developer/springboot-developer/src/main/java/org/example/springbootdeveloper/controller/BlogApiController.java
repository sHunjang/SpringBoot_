package org.example.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import org.example.springbootdeveloper.domain.Article;
import org.example.springbootdeveloper.dto.AddArticleRequest;
import org.example.springbootdeveloper.dto.ArticleResponse;
import org.example.springbootdeveloper.dto.UpdateArticleRequest;
import org.example.springbootdeveloper.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// BlogApiController.java : controller 패키지를 생성한 뒤, controller 패키지에서 BlogApiController 클래스 생성

@RequiredArgsConstructor
@RestController  // => @Controller + @ResponseBody
// HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
// 클래스에 붙이면 HTTP 응답으로 객체 데이터를 JSON 형식으로 반환
public class BlogApiController {

        private final BlogService blogService;

    // @PostMapping : HTTP 메서드가 POST일 때 전달받은 URL과 동일하면 메서드로 매핑
    @PostMapping("/api/articles")  // '/api/articles' 는 addArticle() 메서드에 매핑함
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request) {
        // @RequestBody : HTTP 요청 중 HTTP body 의 값들을 @RequestBody 애너테이션이 붙은 객체인 AddArticleRequest.java 에 매핑
        // @RequestBody로 요청 본문 값 매핑
        Article savedArticle = blogService.save(request);

        // 요청한 자원이 성공적으로 생성되었으며 저장된 블로그 글 정보를 응답 객체에 담아 전증
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle);
        // ResponseEntity.status().body() : HttpEntity 로부터 상속받아 정의한 클래스
        // + http request 의 결과 데이터 & 상태 코드를 제어할 수 있는 클래스
        // 응답 코드 201 / 즉, Created를 응답하고 테이블에 저장된 객체를 반환
    }

    // findAllArticles() : 전체 글을 조회한 뒤 반환하는 메서드
    // /api/articles GET 요청이 오면 글 전체를 조회하는 findAll() 메서드를 호출한 다음 응답용 객체인 ArticleResponse로 파싱해 body에 담아 클라이언트에게 전송
    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {
        List<ArticleResponse> articles = blogService.findAll()
                .stream()
                // stream (스트림) : 여러 데이터가 모여 있는 컬렉션을 간편하게 처리하기 위한 자바 8에서 추가된 기능
                // 리스트를 스트림으로 변환하여 스트림 파이프라인을 구성
                .map(ArticleResponse::new)
                // map() : 요소들을 특정 조건에 해당하는 값으로 변환
                // .map(ArticleResponse::new) : 각 블로그 글을 ArticleResponse 객체로 매핑
                .toList();

        return ResponseEntity.ok()
                .body(articles);
    }

    @GetMapping("/api/articles/{id}")  // ex. /api/articles/3 GET 요청을 받으면 id에 3이 들어옴
    // URL 경로에서 값 추출
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable long id) {
        // @PathVariable 애너테이션 : URL에서 값을 가져오는 애너테이션
        // 위 애너테이션이 붙은 메서드의 동작 원리는 '/api/articles/3 GET' 요청을 받으면 id에 3이 들어옴
        // + 이 값은 앞서 만든 서비스 클래스의 findById() 메서드로 넘어가 3번 블로그 글을 찾음
        // 글을 찾으면 3번 글의 정보를 body에 담아 웹 브라우저로 전송
        Article article = blogService.findById(id);

        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }

   @DeleteMapping("/api/articles/{id}")
   // '/api/articles/{id} DELETE' 요청이 오면 {id}에 해당하는 값이 @PathVariable 애너테이션을 통해 들어됨
   public ResponseEntity<Void> deleteArticle(@PathVariable long id) {
        blogService.delete(id);

        return ResponseEntity.ok()
                .build();
   }


    @PutMapping("/api/articles/{id}")
    // updateArticle() 메서드 : '/api/articles/{id} PUT' 요청이 오면 글을 수정하기 위한 메서드
    // '/api/articles/{id} PUT' 요청이 오면 Request Body 정보가 request로 넘어옴
    // 그리고 다시 서비스 클래스의 update() 메서드에 id와 request를 넘겨줌
    // 응답 값은 body에 담아 전송
    public ResponseEntity<Article> updateArticle(@PathVariable long id, @RequestBody UpdateArticleRequest request) {
        Article updateArticle = blogService.update(id, request);

        return ResponseEntity.ok()
                .body(updateArticle);
    }

}
