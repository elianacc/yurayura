package pers.elianacc.yurayura.controller;

import com.baomidou.lock.annotation.Lock4j;
import com.github.pagehelper.PageInfo;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.elianacc.yurayura.dto.IdDTO;
import pers.elianacc.yurayura.dto.SysOrgInsertDTO;
import pers.elianacc.yurayura.dto.SysOrgSelectDTO;
import pers.elianacc.yurayura.dto.SysOrgUpdateDTO;
import pers.elianacc.yurayura.entity.SysOrg;
import pers.elianacc.yurayura.service.ISysOrgService;
import pers.elianacc.yurayura.vo.ApiResult;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统组织 controller
 *
 * @author ELiaNaCc
 * @since 2024-05-16
 */
@RestController
@RequestMapping("/api/sys/org")
@Api(tags = "系统组织API")
public class SysOrgController {

    @Autowired
    private ISysOrgService iSysOrgService;

    /**
     * 查询系统组织（根据系统组织id）
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<pers.elianacc.yurayura.entity.sys.org.SysOrg>
     */
    @GetMapping("/getById")
    @ApiOperation("查询系统组织（根据系统组织id）")
    public ApiResult<SysOrg> getById(IdDTO dto) {
        return ApiResult.success("查询成功", iSysOrgService.getById(dto.getId()));
    }

    /**
     * 查询所有系统组织
     *
     * @param
     * @return pers.elianacc.yurayura.vo.ApiResult<java.util.List<pers.elianacc.yurayura.entity.sys.org.SysOrg>>
     */
    @GetMapping("/getAll")
    @ApiOperation("查询所有系统组织")
    public ApiResult<List<SysOrg>> getAll() {
        return ApiResult.success("查询成功", iSysOrgService.list());
    }

    /**
     * 分页查询系统组织
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<com.github.pagehelper.PageInfo<pers.elianacc.yurayura.entity.sys.org.SysOrg>>
     */
    @PostMapping("/getPage")
    @ApiOperation("分页查询系统组织")
    public ApiResult<PageInfo<SysOrg>> getPage(@Validated @RequestBody SysOrgSelectDTO dto) {
        return ApiResult.success("分页查询成功", iSysOrgService.getPage(dto));
    }

    /**
     * 添加系统组织
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PostMapping("/insert")
    @Lock4j(keys = {"#dto.orgName"}, autoRelease = false)
    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @ApiOperation("添加系统组织")
    public ApiResult<String> insert(@Validated @RequestBody SysOrgInsertDTO dto) {
        SysOrg sysOrg = new SysOrg();
        BeanUtils.copyProperties(dto, sysOrg);
        sysOrg.setOrgCreateTime(LocalDateTime.now());
        iSysOrgService.save(sysOrg);
        return ApiResult.success("添加成功");
    }

    /**
     * 修改系统组织
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PutMapping("/update")
    @Lock4j(keys = {"#dto.id"}, autoRelease = false)
    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @ApiOperation("修改系统组织")
    public ApiResult<String> update(@Validated @RequestBody SysOrgUpdateDTO dto) {
        SysOrg sysOrg = new SysOrg();
        BeanUtils.copyProperties(dto, sysOrg);
        sysOrg.setOrgUpdateTime(LocalDateTime.now());
        iSysOrgService.updateById(sysOrg);
        return ApiResult.success("修改成功");
    }

}
