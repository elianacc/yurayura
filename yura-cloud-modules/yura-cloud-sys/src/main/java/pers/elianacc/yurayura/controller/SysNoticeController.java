package pers.elianacc.yurayura.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.elianacc.yurayura.dto.IdDTO;
import pers.elianacc.yurayura.entity.SysNotice;
import pers.elianacc.yurayura.enumerate.AdminOrgEnum;
import pers.elianacc.yurayura.service.ISysNoticeService;
import pers.elianacc.yurayura.vo.ApiResult;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统通知 controller
 *
 * @author ELiaNaCc
 * @since 2024-12-13
 */
@RestController
@RequestMapping("/api/sys/notice")
@Api(tags = "系统通知API")
public class SysNoticeController {

    @Autowired
    private ISysNoticeService iSysNoticeService;

    /**
     * 查询系统通知（根据系统通知id）
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<pers.elianacc.yurayura.entity.SysNotice>
     */
    @GetMapping("/getById")
    @ApiOperation("查询系统通知（根据系统通知id）")
    public ApiResult<SysNotice> getById(IdDTO dto) {
        return ApiResult.success("查询成功", iSysNoticeService.getById(dto.getId()));
    }

    /**
     * 查询最近一个月系统通知
     *
     * @param
     * @return pers.elianacc.yurayura.vo.ApiResult<java.util.List<pers.elianacc.yurayura.entity.SysNotice>>
     */
    @GetMapping("/getRecentMonthList")
    @ApiOperation("查询最近一个月系统通知")
    public ApiResult<List<SysNotice>> getRecentMonthList() {
        Integer managerOrg = (Integer) StpUtil.getExtra("managerOrg");
        LocalDateTime today0Time = LocalDate.now().atStartOfDay();
        return ApiResult.success("查询成功", iSysNoticeService
                .list(Wrappers.<SysNotice>lambdaQuery()
                        .eq(!managerOrg.equals(AdminOrgEnum.ADMIN_ORG.getOrg()), SysNotice::getNoticeOrg, managerOrg)
                        .ge(SysNotice::getNoticeCreateTime, today0Time.minusDays(30))
                        .orderByDesc(SysNotice::getNoticeCreateTime)
                ));
    }

}

