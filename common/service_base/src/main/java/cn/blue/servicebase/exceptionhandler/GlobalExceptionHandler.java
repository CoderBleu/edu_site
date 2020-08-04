package cn.blue.servicebase.exceptionhandler;

import cn.blue.commonutils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ControllerAdvice 全局异常处理 全局数据绑定 全局数据预处理
 * @author coderblue
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 指定出现什么异常执行这个方法
     * ResponseBody 为了返回数据
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        e.printStackTrace();
        return Result.error().message("执行了全局异常处理..");
    }

    /**
     * 特定异常
     */
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result error(ArithmeticException e) {
        e.printStackTrace();
        return Result.error().message("执行了ArithmeticException异常处理..");
    }

    /**
     * 自定义异常
     * 需要手动try-catch捕获异常，然后throw new 自定义异常
     */
    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public Result error(GuliException e) {
        // 如果程序出现异常，把异常信息输出到日志文件中
        log.error(e.getMessage());
        e.printStackTrace();
        return Result.error().code(e.getCode()).message(e.getMsg());
    }
}
