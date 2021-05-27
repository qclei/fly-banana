package fly.kasane.banana.controller;


import fly.kasane.banana.entity.User;
import fly.kasane.banana.entity.vo.UserRegisterVo;


import fly.kasane.banana.exception.BadRequestException;
import fly.kasane.banana.result.Result;
import fly.kasane.banana.service.UserService;

import fly.kasane.banana.utils.VerifyCodeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Api(value = "用户管理", tags = "系统有关的API")
@RestController
@RequestMapping(value = "/app/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;
//
//    @Autowired
//    private CaptchaService captchaService;

    @ApiOperation(value = "用户注册", notes = "用于注册用户信息")
    @PostMapping(value = "/register")
    public Result<User> register(@RequestBody @Validated UserRegisterVo form){
        User user = this.userService.getById("U" + form.getEmail());
        if(user != null){
            return Result.error("该用户已经存在");

        }
        String password = form.getPassword();

        user = new User();
        user.setUserId("U" + form.getEmail());
        user.setUserName(form.getEmail());
        user.setEmail(form.getEmail());
        user.setPassword(password);
        user.setCreateBy(form.getEmail());
        userService.save(user);

        return  Result.ok(user);
    }

    @ApiOperation(value = "用户登录", notes = "用于登录验证")
    @PostMapping(value = "/login")
    public Result<User> login(@RequestBody UserRegisterVo form){
        User user = this.userService.getById("U" + form.getEmail());
        throw new BadRequestException("用户登录测试异常");
        //return  Result.ok(user);
    }

    @ApiOperation(value = "验证码", notes = "用户获取验证码")
    @GetMapping("/verifyCode.jpg")
    public void verifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /*禁止缓存*/
        response.setDateHeader("Expires",0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        /*获取验证码*/
        String code = VerifyCodeUtils.generateVerifyCode(4);
        /*验证码已key，value的形式缓存到redis 存放时间一分钟*/
        String uuid = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(uuid,code,1, TimeUnit.MINUTES);
        Cookie cookie = new Cookie("captcha",uuid);
        /*key写入cookie，验证时获取*/
        response.addCookie(cookie);
        ServletOutputStream outputStream = response.getOutputStream();
        //ImageIO.write(bufferedImage,"jpg",outputStream);
        VerifyCodeUtils.outputImage(110,40,outputStream,code);
        outputStream.flush();
        outputStream.close();
    }



//    @PostMapping("/get")
//    public ResponseModel get(@RequestBody CaptchaVO data, HttpServletRequest request) {
//        assert request.getRemoteHost()!=null;
//        data.setBrowserInfo(getRemoteId(request));
//        return captchaService.get(data);
//    }

//    public static final String getRemoteId(HttpServletRequest request) {
//        String xfwd = request.getHeader("X-Forwarded-For");
//        String ip = getRemoteIpFromXfwd(xfwd);
//        String ua = request.getHeader("user-agent");
//        if (StringUtils.isNotBlank(ip)) {
//            return ip + ua;
//        }
//        return request.getRemoteAddr() + ua;
//    }
//
//    private static String getRemoteIpFromXfwd(String xfwd) {
//        if (StringUtils.isNotBlank(xfwd)) {
//            String[] ipList = xfwd.split(",");
//            for (int i = ipList.length - 1; i >= 0; i--) {
//                String ip = ipList[i];
//                ip = StringUtils.trim(ip);
//                return ip;
//            }
//        }
//        return null;
//    }

}
