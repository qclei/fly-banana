package fly.kasane.banana.enums;


public enum CodeEnums {
    OK(200, "请求成功"),
    BAD_REQUEST(400, "这个一个失败的请求"),
    ERROR(500, "请求失败"),
    ;
    private Integer code;
    private String message;


    CodeEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
