package fly.kasane.banana.handler;

import fly.kasane.banana.result.Result;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 全局控制层参数拦截
 * @author kasane
 * @date 2021/3/1
 */
@RestControllerAdvice
public class GlobalControllerHandler {//implements ResponseBodyAdvice<Object> {
//    /**
//     * 判断哪些需要拦截
//     * @param methodParameter
//     * @param aClass
//     * @return
//     */
//    @Override
//    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
//        return true;
//    }
//
//    @Override
//    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest request, ServerHttpResponse response) {
//        //如果返回的数据是ResultObjectModel、Byte、String类型则不进行封装
//        if( body instanceof Result || body instanceof Byte || body instanceof String) {
//            return body;
//        }
//        return this.getWrapperResponse(request , body);
//    }
//
//    /**
//     * 返回正常的信息
//     * @param request
//     * @param data
//     * @return
//     */
//    private Result<Object> getWrapperResponse(ServerHttpRequest request, Object data) {
//        return new Result<>(true, "请求成功" , data);
//    }
}
