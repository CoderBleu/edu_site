package cn.blue.educms.controller;


import cn.blue.commonutils.Result;
import cn.blue.educms.entity.CrmBanner;
import cn.blue.educms.service.CrmBannerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前台bannber显示
 * </p>
 *
 * @author testjava
 * @since 2020-03-07
 */
@RestController
@RequestMapping("/eduCms/bannerFront")
@CrossOrigin
public class BannerFrontController {

    @Resource
    private CrmBannerService bannerService;

    @ApiOperation("查询所有banner")
    @GetMapping("getAllBanner")
    public Result getAllBanner() {
        List<CrmBanner> list = bannerService.selectAllBanner();
        return Result.success().data("list",list);
    }
}

