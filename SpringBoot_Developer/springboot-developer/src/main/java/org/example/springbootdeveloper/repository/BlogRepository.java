package org.example.springbootdeveloper.repository;

import org.example.springbootdeveloper.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

// 엔티티 Article & 엔티티의 PK 타입 Long을 인수로 넣음
public interface BlogRepository extends JpaRepository<Article, Long> {
}
