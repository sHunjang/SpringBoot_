package org.example.springbootdeveloper.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity // 엔티티로 지정
@Getter  // 클래스 필드에 대해 별도 코드 없이 모든 필드에 대한 접근자 메서드를 만들 수 있게 함
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 접근 제어자가 PROTECTED인 기본 생성자를 별도의 코드 없이 생성함
public class Article {

    @Id // id 필드를 기본키로 지정 = DB 테이블의 기본키 (PK)와 객체의 필드를 매핑시켜주는 Annotation
    // @Id 만 사용할 경우 기본 키(PK)를 직접 할당 해주어야 함
    // 기본 키를 직접 할당하는 대신 DB가 생성해주는 값을 사용하려면 @GeneratedValue 사용
    // @GeneratedValue : 기본 키(PK)를 자동으로 생성해주는 Annotation
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키를 자동으로 1씩 증가
    // IDENTITY 전략 : 기본 키 생성을 DB에 위임하는 전략, AUTO_INCREMENT 처럼 DB에 값을 저장하고 나서 기본 키 값을 구할 수 있을 때 사용
    @Column(name = "id", updatable = false)  // @Column : 객체 필드를 테이블의 컬럼에 매핑 시켜 주는 Annotaion
    // name 속성 : DB 테이블의 "id" 컬럼을 지정
    // updatable 속성 : "id" 컬럼이 DB에 업데이트 가능한지 여부를 지정
    //'updatable = false' 설정 시 -> "id" 컬럼은 업데이트 대상에서 제외됨, 해당 필드의 값을 변경해도 해당 컬럼은 업데이트 되지 않음.
    private Long id;

    @Column(name = "title", nullable = false) // 'title'이라는 not null 컬럼과 매칭
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    // Entity 에 생성, 수정 시간 추가
    // 글의 생성 시간을 뷰에서 확인
    @CreatedDate // Entity 가 생성될 때 생성 시간을 "created_at" 컬럼에 저장
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate  // Entity 가 수정될 때 마지막으로 수정된 시간을 "updated_at" 칼럼에 저장
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder  // 빌더 패턴으로 객체 생성
    // @Builder 애너테이션 : 롬북에서 지원 & 생성자 위에 입력 시 빌더 패턴 방식으로 객체 생성 (편리)
    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 엔티티에 요청받은 내용으로 값을 수정하는 메서드 : update() 메서드
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }



}
