package com.springboot.MiniProject.config;
import com.springboot.MiniProject.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoDetails implements UserDetails {


    private String name;
    private String password;
    private String token;
    private List<GrantedAuthority> authorities;
/******************************************************************************************/
   /* public UserInfoDetails(UserInfo userInfo) {
        name=userInfo.getNom();
        password=userInfo.getPassword();
        authorities= Arrays.stream(userInfo.getRoles().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }*/

/*********************************************************************************************/

public UserInfoDetails(User userInfo) {
    name=userInfo.getNom();
    password=userInfo.getPassword();
    authorities= Arrays.stream(userInfo.getRoles().split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());}
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
