package com.chensino.core.security.config;

import com.chensino.core.security.entrypoint.CustomAuthenticationEntryPoint;
import com.chensino.core.security.filter.TokenAuthenticationFilter;
import com.chensino.core.security.provider.CustomMobileAuthenticationProvider;
import com.chensino.core.security.service.CustomUserDetailsService;
import com.chensino.core.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private TokenAuthenticationFilter tokenAuthenticationFilter;

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

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
        return web -> web.ignoring().antMatchers("/user/jumpAllFilterTest", "/user/getSession");
    }


    @Autowired
    private CustomMobileAuthenticationProvider customMobileAuthenticationProvider;
    /**
     * 处理接口权限
     */
    @Bean
    @Order(2)
    public SecurityFilterChain webSiteSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .mvcMatchers("/login/**","/login/phone","/management","/management/**")
                .permitAll()
                .anyRequest()//剩下所有的请求
                .authenticated()  // 所有请求都必须要认证才可以访问

                .and()
                // 禁用csrf
                .csrf()
                .disable()
                // 启用表单登录
//                .formLogin()
//                .permitAll()
//                .and()
                // 异常处理
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)

                .and()
//                .authenticationProvider(customMobileAuthenticationProvider)
//                .authenticationProvider(new DaoAuthenticationProvider())
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //禁止生成session,也不会向客户端返回session
                .and()
                .build();
    }


    /**
     * 新版本security获取AuthenticationManager的两种方法
     * @param authenticationConfiguration
     * @return
     * @throws Exception
     */
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }


    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(customMobileAuthenticationProvider);
        authenticationManagerBuilder.authenticationProvider(authProvider());

        return authenticationManagerBuilder.build();
    }


    /**
     * 默认AuthenticationProvider,如果创建了自定义AuthenticationProvider，则默认的就不会被注入到AuthenticationManager,
     * 所以如果还想保留默认的，需要手动创建bean,并在AuthenticationManager中注入
     * @return
     */
    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }


}
