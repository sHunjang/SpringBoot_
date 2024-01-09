package org.example.springbootdeveloper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.springbootdeveloper.domain.Article;
import org.example.springbootdeveloper.dto.AddArticleRequest;
import org.example.springbootdeveloper.dto.UpdateArticleRequest;
import org.example.springbootdeveloper.repository.BlogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

// BlogApiControllerTest.java = ObjectMapper Class
// ObjectMapper 클래스로 만든 객체는 자바 객체를 JSON 데이터로 변환하는 직렬화 Serialization 또는
// 반대로 JSON 데이터를 자바에서 사용하기 위해 자바 객체로 변환하는 역직렬화 Deserialization 할 때 사용
// ObjectMapper 클래스 => Jackson 라이브러리에서 제공

@SpringBootTest
// 테스트용 애플리케이션 컨텍스트
// 통합 테스트를 제공하는 기본적인 Spring Boot Test Annotation
@AutoConfigureMockMvc
// MockMvc 생성 및 자동 구성
// Mock 테스트 시 필요한 의존성을 제공 -> MockMvc mvc;
// @SpringBootTest + @AutoConfigureMockMvc
// => 프로젝트 내부에 있는 스프링 빈을 모두 등록하여 테스트에 필요한 의존성 추가
// => 실제 운영 환경에서 사용될 클래스들을 통합하여 테스트
// => 단위 테스트와 같이 기능 검증을 위한 것이 아니라, Spring Framework에서 전체적으로 Flow가 제대로 동작하는지 검증하기 위해 사용
class BlogApiControllerTest {

        @Autowired  // (중요) @Autowired : Spring Container 에 있는 '빈 Bean' 이라는 것을 주입하는 역할
        protected MockMvc mockMvc;

        @Autowired
        protected ObjectMapper objectMapper;  // 직렬화, 역직렬화를 위한 클래스

        @Autowired
        private WebApplicationContext context;

        @Autowired
        BlogRepository blogRepository;

        @BeforeEach  // 테스트 실행 전 실행하는 메서드
        // @BeforeEach == @Before
        // 현재 클래스의 각 @Test, @REepeatedTest, @ParameterizedTes or @TestFactory 메소드들 보다 먼저 실행되어야 함을 의미
        // 비즈니스 로직이 복잡해지고 테스트에 여러 초기화가 필요하다면 여러 개의 @BeforeEach 메소드를 만들 수 있음
        public void mockMvcSetUp() {
                this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                        .build();
                blogRepository.deleteAll();
        }

        @DisplayName("addArticle : 블로그 글 추가에 성공한다.")
        @Test
        public void addArticle() throws Exception {

        // given
        // 블로그 글 추가에 필요한 요청 객체를 만듬
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";
        final AddArticleRequest userRequest = new AddArticleRequest(title, content);

        final String requestBody = objectMapper.writeValueAsString(userRequest);
        // writeValueAsString() 메서드 : 객체를 JSON으로 직렬화

        // when
        // 블로그 글 추가 API에 요청을 보냄. 이때 요청 타입은 JSON이며, given절에서 미리 만들어둔 객체를 요청 본문으로 함께 보냄
        // mockMvc 사용 : 설정한 내용을 바탕으로 요청 전송
        // + HTTP 메서드, URL, 요청 본문, 요청 타입 등 설정
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                // contentType() 메서드 : 요청을 보낼 때 JSON, XMl 등 다양한 타입 중 하나를 골라 요청을 보냄
                // 여기서는 JSON 타입 요청을 보낸다고 명시
                .content(requestBody));

        // then
        // 응답 코드가 201 Created인지 확인. Blog를 전체 조회해 크기가 1인지 확인하고, 실제로 저장된 데이터와 요청 값을 비교
        result.andExpect(status().isCreated());

        List<Article> articles = blogRepository.findAll();

        // asserThat() : 주로 테스트 코드에서 사용되는 메서드
        // 특정 조건이 참인지 여부를 검증하는데 쓰임
        assertThat(articles.size()).isEqualTo(1);  // 블로그 글의 크기가 1인지 검증
        assertThat(articles.get(0).getTitle()).isEqualTo(title);  // 블로그 글의 title이 같은지 검증
        assertThat(articles.get(0).getContent()).isEqualTo(content);  // 블로그 글의 content가 같은지 검증
        }

        @DisplayName("findAllArticles : 블로그 글 목록 조회에 성공한다.")
        @Test
        public void findAllArticles() throws Exception {
                // given
                // 블로그 글을 저장
                final String url = "/api/articles";
                final String title = "title";
                final String content = "content";

                // 테스트 데이터로 사용할 블로그 글을 저장
                blogRepository.save(Article.builder()
                        .title(title)
                        .content(content)
                        .build());

                // when
                // 목록 조회 API를 호출
                final ResultActions resultActions = mockMvc.perform(get(url)
                        .accept(MediaType.APPLICATION_JSON));

                // then
                // 응답 코드가 200 OK이고, 반환 받은 값 중에 0번째 요소의 content와 title이 저장된 값과 같은지 확인
                resultActions
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$[0].content").value(content))
                        // JSON 응답의 항목의 "content" 속성 값이 정의한 content 와 일치하는지 확인
                        .andExpect(jsonPath("$[0].title").value(title));
                        // JSON 응답 항목의 "title" 속성 값이 정의한 title과 일치하는지 확인
        }

        @DisplayName("findArticle : 블로그 글 조회에 성공한다.")
        @Test
        public void findArticle() throws Exception {
                // given
                // 블로그 글을 저장
                final String url = "/api/articles/{id}";
                final String title = "title";
                final String content = "content";

                Article savedArticle = blogRepository.save(Article.builder()
                        .title(title)
                        .content(content)
                        .build());

                // when
                // 저장한 블로그 글의 id 값으로 API를 호출
                final ResultActions resultActions = mockMvc.perform(get(url, savedArticle.getId()));

                // then
                // 응답 코드가 200 OK이고, 반환받은 content와 title이 저장된 값과 같은지 확인
                resultActions
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.content").value(content))
                        .andExpect(jsonPath("$.title").value(title));
        }

        @DisplayName("deleteArticle : 블로그 글 삭제에 성공한다.")
        @Test
        public void deleteArticle() throws Exception {
                // given
                // 블로그 글을 저장
                final String url = "/api/articles/{id}";
                final String title = "title";
                final String content = "content";

                Article savedArticle = blogRepository.save(Article.builder()
                        .title(title)
                        .content(content)
                        .build());

                // when
                // 저장한 블로그 글의 id 값으로 삭제 API를 호출
                mockMvc.perform(delete(url, savedArticle.getId()))
                        .andExpect(status().isOk());

                // then
                // 응답 코드가 200 OK 이고, 블로그 글 리스트를 전체 조회해 조회한 배열 크기가 0인지 확인
                List<Article> articles = blogRepository.findAll();

                assertThat(articles).isEmpty();

        }

        @DisplayName("updateArticle : 블로그 글 수정에 성공한다. ")
        @Test
        public void updateArticle() throws Exception {
                // given
                // 블로그 글을 저장하고, 블로그 글 수정에 필요한 요청 객체를 만듬
                final String url = "/api/articles/{id}";
                final String title = "title";
                final String content = "content";

                Article savedArticle = blogRepository.save(Article.builder()
                        .title(title)
                        .content(content)
                        .build());

                final String newTitle = "new Title";
                final String newContent = "new Content";

                UpdateArticleRequest request = new UpdateArticleRequest(newTitle, newContent);

                // when
                // UPDATE API 로 수정 요청을 보냄
                // 이때 요청 타입은 JSON이며, given 절에서 미리 만들어둔 객체를 요청 본문으로 함께 보냄
                ResultActions result = mockMvc.perform(put(url, savedArticle.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)));

                // then
                // 응답 코드가 200 OK인지 확인
                // 블로그 글 id 로 조회한 후에 갑이 수정되었는지 확인
                result.andExpect(status().isOk());

                Article article = blogRepository.findById(savedArticle.getId()).get();

                assertThat(article.getTitle()).isEqualTo(newTitle);
                assertThat(article.getContent()).isEqualTo(newContent);
        }

}