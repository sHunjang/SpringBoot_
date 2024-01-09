package org.example.springbootdeveloper.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.springbootdeveloper.domain.Article;
import org.example.springbootdeveloper.dto.AddArticleRequest;
import org.example.springbootdeveloper.dto.UpdateArticleRequest;
import org.example.springbootdeveloper.repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

// BlogService.java : service 패키지를 생성한 뒤, service 패키지에서 BlogService 클래스 구현
@RequiredArgsConstructor  // 빈을 생성자로 생성하는 롬복 지원 애너테이션
// + (초기화 되지않은) final 필드나 @NotNull이 붙은 필드에 대해 생성자를 생성, @Autowired를 사용하지 않고 의존성 주입 (DI)
@Service  // 해당 클래스를 '빈 Bean' 객체로 생서하여 서블릿 컨테이너에 등록함
public class BlogService {

    private final BlogRepository blogRepository;
    // 블로그 글 추가 메서드
    public Article save(AddArticleRequest request) {
    // save() 메서드 : JpaRepository에서 지원하는 저장 메서드로 AddArticleRequest 클래스에 저장된 값들을 article 데이터베이스에 저장
        return blogRepository.save(request.toEntity());
    }

    public List<Article> findAll() {
    // findAll() 메서드 : JPA 지원 메서드 + article 테이블에 저장 되어 있는 모든 데이터를 조회함
        return blogRepository.findAll();
    }

    // findById() 메서드 : JPA에서 제공 / ID로 Entity 조회
    // findById() 메서드가 없으면 IllegalArgumentException 예외 발생
    public Article findById(long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    // delete() 메서드 : ID로 JPA에서 제공하는 deleteById() 메서드를 이용해 데이터베이스에서 데이터를 삭제
    public void delete(long id) {
        blogRepository.deleteById(id);
    }

    @Transactional // 트랜잭션 메서드
    // 매칭한 메서드를 하나의 트랜잭션으로 처리함
    // 스프링에서는 트랜잭션을 적용하기 위해 다른 작업을 할 필요 없이 @Transactional 애너테이션만 사용하면 됨
    // updqte() 메서드 : Entity의 필드 값이 바뀌면 중간에 에러가 발생해도 제대로 된 값 수정을 보장함
    public Article update(long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        article.update(request.getTitle(), request.getContent());

        return article;
    }
}
