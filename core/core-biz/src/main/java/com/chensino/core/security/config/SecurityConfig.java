package com.chensino.core.security.config;

import com.chensino.core.security.service.CustomUserDetailsService;
import com.chensino.core.system.service.SysUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    /**
     * 自定义密码加密方式，解密会自动调用PasswordEncoder的match方法
     *
     * @return
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * WebSecurity 处理静态资源不走过滤器，注意区别HttpSecurity,HttpSecurity主要用来处理后端接口，比如login接口虽然可以ignore,但是
     * 还有其他逻辑还要走过滤器，如果使用WebSecurity，则login直接就不会受到任何过滤器处理，代表这个接口已经超脱于Security之外了。一句话：
     * WebSecurity负责过滤不需要处理的静态资源，HttpSecurity负责处理普通的api接口。
     *
     * @return
     */
    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().antMatchers("/user/jumpAllFilterTest","/user/getSession");
    }

    /**
     * 处理接口权限
     */
    @Bean
    @Order(2)
    public SecurityFilterChain webSiteSecurityFilterChain(HttpSecurity http) throws Exception {
        return http.antMatcher("/**")
                .authorizeRequests()
                // 所有请求都必须要认证才可以访问
                .anyRequest()
                .hasRole("ADMIN")
//                .permitAll()
                .and()
                // 禁用csrf
                .csrf()
                .disable()
                // 启用表单登录
                .formLogin()
                .permitAll()
                .and()
                // 捕获成功认证后无权限访问异常，直接跳转到 百度
                .exceptionHandling()
                .accessDeniedHandler((request, response, exception) -> response.sendRedirect("http://www.baidu.com"))
                .and()
                .build();
    }

    @Bean
    UserDetailsService userDetailsService(SysUserService sysUserService) {
//        System.out.println(new BCryptPasswordEncoder().encode("123456"));
        return new CustomUserDetailsService(sysUserService);
    }
}
