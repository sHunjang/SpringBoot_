package org.example.springbootdeveloper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing  // created_at, updated_at 자동 업데이트하기 위함
// @EnableJpaAuditing : Entity 객체가 생성되거나 변경되었을 때 자동으로 갑을 등록할 수 있게 함
@SpringBootApplication
public class SpringBootDeveloperApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDeveloperApplication.class, args);
    }
}
