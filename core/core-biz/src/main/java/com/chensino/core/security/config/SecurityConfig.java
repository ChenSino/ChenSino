package com.chensino.core.security.config;

import com.chensino.common.security.component.PermitAllRequestMatcher;
import com.chensino.common.security.component.properties.SecurityProperties;
import com.chensino.core.security.entrypoint.CustomAuthenticationEntryPoint;
import com.chensino.core.security.filter.TokenAuthenticationFilter;
import com.chensino.core.security.provider.GithubAuthenticationProvider;
import com.chensino.core.security.provider.PhoneAuthenticationProvider;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import java.util.List;

/**
 * @author chenkun
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Data
public class SecurityConfig {


    private final UserDetailsService userDetailsService;
    private final TokenAuthenticationFilter tokenAuthenticationFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final PhoneAuthenticationProvider phoneAuthenticationProvider;
    private final GithubAuthenticationProvider githubAuthenticationProvider;
    private final PermitAllRequestMatcher permitAllRequestMatcher;
    private final SecurityProperties securityProperties;
    private final LogoutHandler customLogoutHandler;
    private final LogoutSuccessHandler customLogoutSuccessHandler;


    /**
     * 自定义密码加密方式，解密会自动调用PasswordEncoder的match方法
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * WebSecurity 处理静态资源不走过滤器，注意区别HttpSecurity,HttpSecurity主要用来处理后端接口，比如login接口虽然可以ignore,但是
     * 还有其他逻辑还要走过滤器，如果使用WebSecurity，则login直接就不会受到任何过滤器处理，代表这个接口已经超脱于Security之外了。一句话：
     * WebSecurity负责过滤不需要处理的静态资源，HttpSecurity负责处理普通的api接口。
     */
    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(securityProperties.getStaticUrlList().toArray(new String[0]));
    }


    /**
     * 处理接口权限
     */
    @Bean
    @Order(2)
    public SecurityFilterChain webSiteSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(tokenAuthenticationFilter, LogoutFilter.class)
                // 使用新的请求授权配置
                .authorizeHttpRequests(authorize -> authorize
                        // 自定义放行的url
                        .requestMatchers(permitAllRequestMatcher).permitAll()
                        // 其他请求都需要认证
                        .anyRequest().authenticated()
                )
                // 禁用 CSRF
                .csrf(AbstractHttpConfigurer::disable)
                // 配置异常处理
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                )
                // 禁止生成 session
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                //登出配置
                .logout(logout ->
                        logout.logoutUrl("/logout")
                                .addLogoutHandler(customLogoutHandler)
                                .logoutSuccessHandler(customLogoutSuccessHandler));
        return http.build();
    }


    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(phoneAuthenticationProvider);
        authenticationManagerBuilder.authenticationProvider(githubAuthenticationProvider);
        authenticationManagerBuilder.authenticationProvider(daoAuthenticationProvider());
        return authenticationManagerBuilder.build();
    }


    /**
     * 默认AuthenticationProvider,如果创建了自定义AuthenticationProvider，则默认的就不会被注入到AuthenticationManager,
     * 所以如果还想保留默认的，需要手动创建bean,并在AuthenticationManager中注入
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }

    /**
     * 防火墙配置
     *
     * @return HttpFirewall
     */
    @Bean
    public HttpFirewall strictHttpFirewall() {
        StrictHttpFirewall strictHttpFirewall = new StrictHttpFirewall();
        //允许的请求方法
        strictHttpFirewall.setAllowedHttpMethods(List.of("DELETE", "GET", "HEAD", "OPTIONS", "PATCH", "POST", "PUT"));
        return strictHttpFirewall;
    }
}
