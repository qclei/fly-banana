package fly.kasane.banana.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import fly.kasane.banana.base.BaseEntity;
import fly.kasane.banana.validator.constraints.LengthForUtf8;
import lombok.Data;
import org.springframework.validation.annotation.Validated;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@TableName(value = "app_user")
public class User extends BaseEntity {


    @TableId
    @NotNull
    private String userId;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @LengthForUtf8(max = 32, message = "用户超过最大长度")
    private String userName;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @LengthForUtf8(max = 100)
    @JsonIgnore
    private String password;

    /**
     * 密码盐
     */
    @JsonIgnore
    private String salt;
    

    @Email(message = "邮箱格式不正确")
    private String email;

    @JsonIgnore
    @LengthForUtf8(max = 20)
    private String mobile;

    /**
     * 性别
     */
    @LengthForUtf8(max = 1)
    private String sex;



}
