package fly.kasane.banana.config;

import cn.hutool.http.server.HttpServerRequest;
import cn.hutool.http.server.HttpServerResponse;
import com.alibaba.fastjson.JSONObject;
import fly.kasane.banana.filter.ImageCodeFilter;
import fly.kasane.banana.result.Result;
import fly.kasane.banana.utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.concurrent.TimeUnit;

/**
 * @author kasane
 * @desc:安全配置
 * @date 2021/03/01
 */
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    private static final Logger log = LoggerFactory.getLogger(Security.class);
//
//    @Autowired
//    private RedisTemplate redisTemplate;
//
//    @Autowired
//    private ImageCodeFilter imageCodeFilter;
//
//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;
//
//    //token过滤器
////    @Bean
////    public JwtAuthenticationFilter authenticationTokenFilterBean() {
////        return new JwtAuthenticationFilter();
////    }
//
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        http.addFilterBefore(imageCodeFilter, UsernamePasswordAuthenticationFilter.class)
//                .authorizeRequests()
//                .antMatchers(HttpMethod.GET, "/", "/*.html", "favicon.ico", "/**/*.html", "/**/*.html", "/**/*.css", "/**/*.js").permitAll()
//                .antMatchers("/user/**", "login").permitAll()
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .antMatchers("/hwc/**").hasRole("USER")
//                .anyRequest().authenticated()
//                .and().formLogin().loginProcessingUrl("/user/login")
//                .successHandler((HttpServletRequest request, HttpServletResponse response, Authentication authentication) -> {
//                    log.info("用户为====>" + request.getParameter("username") + "登录成功");
//                    response.setContentType("application/json;charset=utf-8");
//                    /* 获取用户信息*/
//                    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//                    String token = jwtTokenUtil.generateToken(userDetails);
//                    /* 存储redis并设置过期时间 */
//                    redisTemplate.boundValueOps(userDetails.getUsername() + "hwc").set(token, 10, TimeUnit.MINUTES);
//
//                    JSONObject jsonObject = new JSONObject();
//                    jsonObject.put("code", "true");
//                    jsonObject.put("msg", "登录成功");
//                    /*认证信息写入header*/
//                    response.setHeader("Authorization", token);
//                    response.getWriter().write(jsonObject.toJSONString());
//                }).failureHandler((HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) -> {
//            log.info("用户为====>" + request.getParameter("username") + "登录失败");
//            String content = authenticationException.getMessage();
//            String temp = "Bad credentials";
//            if (temp.equals(authenticationException.getMessage())) {
//                content = "用户名或者密码错误";
//            }
//            response.setContentType("application/json;charset=utf-8");
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("code", "false");
//            jsonObject.put("msg", content);
//            jsonObject.put("content", authenticationException.getMessage());
//            response.getWriter().write(jsonObject.toJSONString());
//        })/*无权限访问处理*/
//                .and().exceptionHandling().accessDeniedHandler((HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) -> {
//            httpServletResponse.setContentType("application/json;charset=utf-8");
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("code", HttpStatus.FORBIDDEN);
//            jsonObject.put("msg", "无权限访问");
//            jsonObject.put("content", e.getMessage());
//            httpServletResponse.getWriter().write(jsonObject.toJSONString());
//        })/*匿名用户访问无权限资源时的异常*/
//                .and().exceptionHandling().authenticationEntryPoint((HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) -> {
//            response.setContentType("application/json;charset=utf-8");
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("code", HttpStatus.FORBIDDEN);
//            jsonObject.put("msg", "无访问权限");
//            response.getWriter().write(jsonObject.toJSONString());
//        }).and().authorizeRequests()
//                /*基于token，所以不需要session*/
//                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                /*由于使用的是jwt，这里不需要csrf防护并且禁用缓存*/
//                .and().csrf().disable().headers().cacheControl();
//        /*token过滤*/
//        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
//
//
//        super.configure(http);
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception
//    {
//        authenticationManagerBuilder.userDetailsService(sysUserDetailsService).passwordEncoder(new PasswordEncoder()
//        {
//            /**
//             *
//             * Description:用户输入的密码加密
//             * @param charSequence
//             * @author huangweicheng
//             * @date 2019/10/21
//             */
//            @Override
//            public String encode(CharSequence charSequence)
//            {
//                try {
//                    return Common.md5(charSequence.toString());
//                }catch (NoSuchAlgorithmException e){
//                    e.printStackTrace();
//                }
//                return null;
//            }
//
//            /**
//             *
//             * Description: 与数据库的密码匹配
//             * @param charSequence 用户密码
//             * @param encodedPassWord 数据库密码
//             * @author huangweicheng
//             * @date 2019/10/21
//             */
//            @Override
//            public boolean matches(CharSequence charSequence, String encodedPassWord)
//            {
//                try {
//                    return encodedPassWord.equals(Common.md5(charSequence.toString()));
//                }catch (NoSuchAlgorithmException e){
//                    e.printStackTrace();
//                }
//                return false;
//            }
//        });
//    }
}
