package fly.kasane.banana.handler;

import fly.kasane.banana.enums.CodeEnums;
import fly.kasane.banana.exception.BadRequestException;
import fly.kasane.banana.exception.ImageCodeException;
import fly.kasane.banana.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * Description:全局异常捕获
 * @author
 * @date 2021/03/02
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(Exception e){
        e.printStackTrace();
        Result result = new Result(CodeEnums.ERROR.getCode(), "系统出小差了，让网站管理员来处理吧 ಥ_ಥ");
        return result;
    }


    /**
     * 验证码出错
     * @param e
     * @return
     */
    @ExceptionHandler(ImageCodeException.class)
    public Result exceptionHandler(ImageCodeException e){
        e.printStackTrace();
        Result result = new Result(CodeEnums.ERROR.getCode(),"验证码获取失败");
        return result;
    }

    /**
     * 数据效验异常
     * @param e 效验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result exceptionHandler(MethodArgumentNotValidException e){
        log.error("------------------------------------数据效验异常---------------------------------");
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        // defaultMessage就是 验证出错内容
        String message = allErrors.stream().map(s -> s.getDefaultMessage()).collect(Collectors.joining(";"));
        Result result = new Result(CodeEnums.ERROR.getCode(),message);
        return result;
    }

    @ExceptionHandler(BadRequestException.class)
    public Result exceptionHandler(BadRequestException e){
        log.error("自定义返回自定义异常");
        String message = e.getMessage();
        Result result = new Result(CodeEnums.ERROR.getCode(),message);
        return result;
    }

    public static void main(String[] args) {
        String taskName = "2021年05月中旬自贸区南宁片区企业统计旬报报表";

        String yearString = taskName.substring(0, 4);
        String monthString = taskName.substring(5, 7);

        Integer month = Integer.valueOf(monthString);
        try {
            Integer.valueOf(taskName);
        }catch (NumberFormatException exception){
            exception.printStackTrace();
        }

    }
}
