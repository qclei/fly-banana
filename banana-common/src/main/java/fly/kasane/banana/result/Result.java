package fly.kasane.banana.result;

import fly.kasane.banana.enums.CodeEnums;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Result<T> implements Serializable {
    /**
     * 返回是否成功
     */
    private Integer success;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 返回数据内容
     */
    private T data;

    public Result() {
    }

    public Result(Integer success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public Result(Integer success, String message) {
        this.success = success;
        this.message = message;
        this.data = null;
    }

    /**
     * 返回成功
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result ok(T data){
        return new Result(CodeEnums.OK.getCode(), CodeEnums.OK.getMessage(), data);
    }

    /**
     * 返回成功
     * @param message
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result ok(String message , T data){
        return new Result(CodeEnums.OK.getCode() , message , data);
    }

    /**
     * 失败
     * @param message
     * @return
     */
    public static Result error(String message){
        return new Result(CodeEnums.ERROR.getCode() , message , null);
    }

    /**
     * 失败
     * @param message
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result error(String message , T data){
        return new Result(CodeEnums.ERROR.getCode() , message , data);
    }
}
