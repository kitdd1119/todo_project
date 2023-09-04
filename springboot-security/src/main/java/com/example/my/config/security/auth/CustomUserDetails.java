package com.example.my.config.security.auth;

import com.example.my.common.dto.LoginUserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@AllArgsConstructor
@Getter
public class CustomUserDetails implements UserDetails {

    private LoginUserDTO loginUserDTO;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // getAuthorities 권한 리스트를 달라
        return loginUserDTO.getUser().getRoleList()
                .stream()
                .map(role -> (GrantedAuthority) () -> "ROLE_" + role)
                .toList();
    }

    @Override
    public String getPassword() {
        return loginUserDTO.getUser().getPassword();
    }

    @Override
    public String getUsername() {
        return loginUserDTO.getUser().getId();
    }

    // 이 밑의 메소드들은 아직 쓰지 않을 것이라 true로 반환하도록 해놓음.
    @Override
    public boolean isAccountNonExpired() { // 계정이 만료되었는지 확인
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { // 계정이 잠겼는지 확인
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { // 비밀번호가 만료되었는지 확인
        return true;
    }

    @Override
    public boolean isEnabled() { // 계정이 활성화되었는지 확인
        return true;
    }
}
