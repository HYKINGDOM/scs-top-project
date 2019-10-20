package com.scs.top.project.common.exception;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.scs.top.project.common.util.AjaxResult;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.session.UnknownSessionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author admin
 * 全局异常处理
 */
@ControllerAdvice
public class OverallExceptionHandler implements HandlerExceptionResolver {


    private static final Logger logger = LoggerFactory.getLogger(OverallExceptionHandler.class);

    /**
     * 声明要捕获NullPointerException的异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String defultExcepitonHandler(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        if(e instanceof BusinessException) {
            logger.error("业务异常："+e.getMessage(), this.getClass());
            BusinessException businessException = (BusinessException)e;
            return AjaxResult.returnToMessage(false,businessException.getCode() + businessException.getMessage());
        }
        //未知错误
        return AjaxResult.returnToMessage(false, "系统异常：\\n"+e);
    }



    /**
     *
     * @return
     */
    @ExceptionHandler(UserShiroNullPointException.class)
    public ErrorMessage<String> defaultUnauthenticatedExceptionHandler(HttpServletRequest request, SessionNotFoundException exception) throws Exception {
        return handleErrorInfo(request, exception.getMessage(), exception);
    }


//    @ExceptionHandler(SessionNotFoundException.class)
//    @ResponseBody
//    public ErrorMessage<String> sessionNotFoundExceptionHandler(HttpServletRequest request, SessionNotFoundException exception) throws Exception {
//        return handleErrorInfo(request, exception.getMessage(), exception);
//    }

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception ex) {
        ModelAndView mv = new ModelAndView();
        FastJsonJsonView view = new FastJsonJsonView();
        Map<String, Object> attributes = new HashMap<>();
        if (ex instanceof UnauthenticatedException) {
            attributes.put("code", "1000001");
            attributes.put("msg", "token错误");
        } else if (ex instanceof UnauthorizedException) {
            attributes.put("code", "1000002");
            attributes.put("msg", "用户无权限");
        } else if (ex instanceof NullPointerException) {
            attributes.put("success", false);
            attributes.put("message", "session过期请重新登录");
        }else if (ex instanceof UnknownSessionException) {
            attributes.put("success", false);
            attributes.put("message", "session过期请重新登录");
        }else  {
            attributes.put("code", "1000003");
            attributes.put("msg", ex.getMessage());
        }

        view.setAttributesMap(attributes);
        mv.setView(view);
        return mv;
    }

    private ErrorMessage<String> handleErrorInfo(HttpServletRequest request, String message, Exception exception) {
        ErrorMessage<String> errorMessage = new ErrorMessage<>();
        errorMessage.setMessage(message);
        errorMessage.setCode(ErrorMessage.ERROR);
        errorMessage.setData(message);
        errorMessage.setUrl(request.getRequestURL().toString());
        return errorMessage;
    }



}
