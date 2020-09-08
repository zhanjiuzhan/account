package org.account.cl.permissions;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


/**
 * 实现了security的UserDetails的user model类
 * @author Administrator
 */
public class JwtUser implements UserDetails {

    private static final long serialVersionUID = -4763736303745689605L;

    private String username;
    private String password;

    /**
     * 帐号是否过期 true没过期 false过期
     */
    private boolean expired;

    /**
     * 帐号是否锁定 true没锁  false锁
     */
    private boolean locked;

    /**
     * 帐号是否凭证过期 true没有  false过期了
     */
    private boolean credentialsExpired;

    /**
     * 帐号是否被禁用  true没禁用  false禁用
     */
    private boolean enabled;

    private Collection<? extends GrantedAuthority> authorities;

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
        return username;
    }


    @Override
    public boolean isAccountNonExpired() {
        return expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public JwtUser setUsername(String username) {
        this.username = username;
        return this;
    }

    public JwtUser setPassword(String password) {
        this.password = password;
        return this;
    }

    public JwtUser setExpired(boolean expired) {
        this.expired = expired;
        return this;
    }

    public JwtUser setLocked(boolean locked) {
        this.locked = locked;
        return this;
    }

    public JwtUser setCredentialsExpired(boolean credentialsExpired) {
        this.credentialsExpired = credentialsExpired;
        return this;
    }

    public JwtUser setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public JwtUser setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
        return this;
    }
}
