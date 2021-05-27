package fly.kasane.banana.entity.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserRegisterVo {

    @NotBlank
    @Email(message = "邮箱格式不正确")
    private String email;

    @NotBlank
    private String password;
}
