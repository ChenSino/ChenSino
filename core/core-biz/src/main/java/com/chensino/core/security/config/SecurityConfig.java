package com.chensino.core.security.config;

import com.chensino.core.security.service.CustomUserDetailsService;
import com.chensino.core.system.service.SysUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
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
     * 还有其他逻辑还要走过滤器，如果使用WebSecurity，则login直接就不会受到任何过滤器处理，代表这个接口已经超脱于Security之外了
     *
     * @return
     */
    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().antMatchers("/user/list");
    }

/*    @Bean
    SecurityFilterChain securityFilterChain() {
        List<Filter> filters = new ArrayList<>();
        return new DefaultSecurityFilterChain(new AntPathRequestMatcher("/**"), filters);
    }*/

    @Bean
    UserDetailsService userDetailsService(SysUserService sysUserService) {
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
        return new CustomUserDetailsService(sysUserService);
    }
}
