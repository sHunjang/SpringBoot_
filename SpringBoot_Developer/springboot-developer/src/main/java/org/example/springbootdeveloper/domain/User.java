package org.example.springbootdeveloper.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.catalina.UserDatabase;
import org.hibernate.annotations.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


// User 클래스가 상속한 UserDetails 클래스 : Spring Security 에서 사용자의 인증 정보를 담아 두는 인터페이스

@Table(name = "users")
// @Table : 맵핑할 테이블을 지정
// naem : 매핑할 테이블의 이름을 지정
// catelog : DB의 catalog를 맵핑
// schema : DB 스키마와 맵핑
// uniqueConstraint : DDL 쿼리를 작성할 때 제약 조건을 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User implements UserDetails { // UserDetails 를 상혹받아 인증 객체로 사용

    @Id  // @Id : JPA가 객체를 관리할 때 식별할 기본키를 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Builder
    public User(String email, String password, String auth) {
        this.email = email;
        this.password = password;
    }


    @Override  // 권환 반환
    public Collection<? extends GrantedAuthority> getAuthorities() {
    // getAuthorities() : 사용자가 가지고 있는 권한의 목록을 반환 / 현재 코드에서는 사용자 이외의 권한이 없기 때문에 user 권한만 담아 반환함
    // 반환 타입 : Collection<? extends GrantedAuthority>
        return List.of(new SimpleGrantedAuthority("user"));
    }

    // 사용자의 id를 반환(고유한 값)
    @Override
    public String getUsername() {
    // getUsername() : 사용자를 식별할 수 있는 사용자 이름을 반환 / 이때 사용되는 사용자 이름은 반드시 고유해야 함 / 현재 코드는 유니크 속성이 적용된 이메일을 반환
    // 반환 타입 : String
        return email;
    }

    // 사용자의 패스워드 반환
    @Override
    public String getPassword() {
    // getPassword() : 사용자의 비밀번호를 반환 / 이때 저장되어 있는 비밀번호는 암호화해서 저장해야 함
    // 반환 타입 : String
        return password;
    }

    // 계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
    // isAccountNonExpired() : 계정이 만료되었는지 확인하는 메서드 / 만약 만료되지 않은 때는 'true' 를 반환
    // 반환 타입 : boolean
        // 만료되었는지 확인하는 로직
        return true;  // true -> 만료되지 않았음
    }

    // 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
    // isAccountNonLocked() : 계정이 잠금되었는지 확인하는 메서드 / 만약 만료되지 않은 때는 'true' 를 반환
    // 반환 타입 : boolean
        // 계정 잠금되었는지 확인하는 로직
        return true;  // true -> 잠금되지 않았음
    }

    // 패스워드의 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
    // isCredentialsNonExpired() : 비밀번호가 만료되었는지 확인하는 메서드 / 만약 만료되지 않은 때는 'true' 를 반환
    // 반환 타입 : boolean
        // 패스워드가 만료되었는지 확인하는 로직
        return true;  // true -> 만료되지 않았음
    }

    // 계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
    // isEnabled() : 계정이 사용 가능한지 확인하는 메서드 / 만약 사용 가능하다면 'tru' 를 반환
    // 반환 타입 : boolean
        // 계정이 사용 가능한지 확인하는 로직
        return true;  // true -> 사용 가능
    }

}
