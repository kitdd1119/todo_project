package com.example.my.config.security.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public class CustomUserDetails implements UserDetails {

  private User user;

  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  @Builder
  public static class User {
    private Long idx;
    private String id;
    private String password;
    private List<String> roleList;
  }

  @Override // GrantedAuthority이놈은 getAuthority() 이놈만 리턴, 구현하면 됨.
  public Collection<? extends GrantedAuthority> getAuthorities() { // 계정이 갖고 있는 권한 목록을 리턴한다.

    // 화살표 함수로는 이렇게 표현한다.
    // return user.roleList
    // .stream()
    // .map(role -> (GrantedAuthority) () -> "ROLE_" + role)
    // .toList();

    Collection<GrantedAuthority> grantedAuthorityList = new ArrayList<>();

    for (String role : user.roleList) {
      // 익명 클래스를 사용하여 GrantedAuthority를 구현하고 권한을 설정한다.
      GrantedAuthority grantedAuthority = new GrantedAuthority() {
        @Override
        public String getAuthority() {
          return "ROLE_" + role;
        }
      };
      grantedAuthorityList.add(grantedAuthority);
    }

    return grantedAuthorityList;

  }

  @Override
  public String getPassword() { // 비밀번호를 리턴한다.
    return user.getPassword();
  }

  @Override
  public String getUsername() { // 사용자명을 리턴한다.
    return user.getId();
  }

  @Override
  public boolean isAccountNonExpired() { // 계정이 만료되지 않았는지 리턴한다. (true: 만료되지 않음)
    return true;
  }

  @Override
  public boolean isAccountNonLocked() { // 계정이 잠겨있지 않았는지 리턴한다. (true: 잠겨있지 않음)
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() { // 계정의 비밀번호가 만료되지 않았는지 리턴한다. (true: 만료되지 않음)
    return true;
  }

  @Override
  public boolean isEnabled() { // 계정이 활성화(사용가능)인지 리턴한다. (true: 활성화)
    return true;
  }

}
