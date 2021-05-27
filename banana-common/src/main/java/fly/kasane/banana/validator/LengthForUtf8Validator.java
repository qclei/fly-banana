package fly.kasane.banana.validator;

import fly.kasane.banana.exception.BadRequestException;
import fly.kasane.banana.validator.constraints.LengthForUtf8;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.UnsupportedEncodingException;

public class LengthForUtf8Validator implements ConstraintValidator<LengthForUtf8, String> {
    private static final String CHARSET_NAME = "UTF-8";
    private int min;
    private int max;

    @Override
    public void initialize(LengthForUtf8 constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
        this.validateParameters();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null){
            return true;
        }else {
            boolean flag =false;

            int length;
            try {
                length = value.getBytes(CHARSET_NAME).length;
            }catch (UnsupportedEncodingException unsupportedEncodingException){
                unsupportedEncodingException.printStackTrace();
                return  false;
            }
            return length >= this.min && length <= this.max;
        }
    }

    /**
     * 效验参数
     */
    public void validateParameters(){
        if(this.min < 0){
            throw new BadRequestException("min不能为负数");
        }else if ( this.max < 0) {
            throw new BadRequestException("max不能为负数");
        }else if (this.max < this.min) {
            throw new BadRequestException("长度不能为负数");
        }
    }
}
