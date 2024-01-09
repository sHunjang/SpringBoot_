package org.example.springbootdeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateArticleRequest {
    // 블로그 글 수정 요층을 받은 DTO
    // 글에서 수정해야 하는 내용은 제목과 내용이므로 그에 맞게 제목과 내용 필드로 구성
    private String title;
    private String content;
}
