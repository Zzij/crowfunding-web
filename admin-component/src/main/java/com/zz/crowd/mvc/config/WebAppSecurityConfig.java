package com.zz.crowd.mvc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
// 启用全局方法控制功能，并且设置prePostEnabled = true 保证@PreAuthrity、@PostAuthority、@PreFilter等生效
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;


    @Bean
    public BCryptPasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //测试使用内存登陆
        //auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN");

        auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // security的配置
        http
                // 对请求授权
            .authorizeRequests()
                // 过滤 匹配
            .antMatchers("/admin/do/login/page")
                // 放行
            .permitAll()
            .antMatchers("/bootstrap/**","/css/**","/fonts/**","/img/**","/jquery/**"," /layer/**","/script/**","/ztree/**")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
                // 禁用跨站访问伪请求
            .csrf()
            .disable()
                //开启表单登录
            .formLogin()
                //指定登陆页面地址
            .loginPage("/admin/do/login/page")
                //指定登录处理请求的地址
            .loginProcessingUrl("/security/admin/do/login")
                //登录成功地址
            .defaultSuccessUrl("/admin/do/main/page")
            .usernameParameter("loginAcct")
            .passwordParameter("userPwd")
            .and()
                //退出配置
            .logout()
                //指定退出地址
            .logoutUrl("/security/admin/do/loginout")
                //推出成功前往的地址
            .logoutSuccessUrl("/admin/do/login/page");
    }
}
