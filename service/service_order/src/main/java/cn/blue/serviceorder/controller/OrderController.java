package cn.blue.serviceorder.controller;


import cn.blue.commonutils.JwtUtils;
import cn.blue.commonutils.Result;
import cn.blue.serviceorder.entity.Order;
import cn.blue.serviceorder.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import org.junit.After;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-03-13
 */
@RestController
@RequestMapping("/eduOrder/order")
@CrossOrigin
public class OrderController {

    @Resource
    private OrderService orderService;

    @ApiOperation("生成订单的方法")
    @PostMapping("createOrder/{courseId}")
    public Result saveOrder(@PathVariable String courseId, HttpServletRequest request) {
        //创建订单，返回订单号
        String orderNo =
                orderService.createOrders(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return Result.success().data("orderId",orderNo);
    }

    @ApiOperation("根据订单id查询订单信息")
    @GetMapping("getOrderInfo/{orderId}")
    public Result getOrderInfo(@PathVariable String orderId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        Order order = orderService.getOne(wrapper);
        return Result.success().data("item",order);
    }
}

