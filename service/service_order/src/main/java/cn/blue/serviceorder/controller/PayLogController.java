package cn.blue.serviceorder.controller;


import cn.blue.commonutils.Result;
import cn.blue.serviceorder.service.PayLogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-03-13
 */
@RestController
@RequestMapping("/eduOrder/paylog")
@CrossOrigin
public class PayLogController {

    @Resource
    private PayLogService payLogService;


    @ApiOperation("生成微信支付二维码接口 - 参数是订单号")
    @GetMapping("createNative/{orderNo}")
    public Result createNative(@PathVariable String orderNo) {
        //返回信息，包含二维码地址，还有其他需要的信息
        Map map = payLogService.createNatvie(orderNo);
        System.out.println("****返回二维码map集合:"+map);
        return Result.success().data(map);
    }

    @ApiOperation("查询订单支付状态 - 参数：订单号，根据订单号查询 支付状态")
    @GetMapping("queryPayStatus/{orderNo}")
    public Result queryPayStatus(@PathVariable String orderNo) {
        Map<String,String> map = payLogService.queryPayStatus(orderNo);
        System.out.println("*****查询订单状态map集合:"+map);
        if(map == null) {
            return Result.error().message("支付出错了");
        }
        //如果返回map里面不为空，通过map获取订单状态
        if("SUCCESS".equals(map.get("trade_state"))) {
            //添加记录到支付表，更新订单表订单状态
            payLogService.updateOrdersStatus(map);
            return Result.success().message("支付成功");
        }
        return Result.success().code(25000).message("支付中");
    }
}

