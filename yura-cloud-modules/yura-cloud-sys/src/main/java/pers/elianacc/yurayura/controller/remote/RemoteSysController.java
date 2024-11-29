package pers.elianacc.yurayura.controller.remote;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.elianacc.yurayura.service.ISysDictService;
import pers.elianacc.yurayura.service.ISysManagerService;
import pers.elianacc.yurayura.vo.ApiResult;
import pers.elianacc.yurayura.vo.SysDictVO;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 远程调用sys服务 controller
 *
 * @author ELiaNaCc
 * @since 2024-09-09
 */
@RestController
@RequestMapping("/remote/sys")
@ApiIgnore
public class RemoteSysController {

    @Autowired
    private ISysManagerService iSysManagerService;

    @Autowired
    private ISysDictService iSysDictService;

    /**
     * 查询管理员拥有角色的所有权限（根据管理员id）
     *
     * @param managerId
     * @return pers.elianacc.yurayura.vo.ApiResult<java.util.List<java.lang.String>>
     */
    @RequestMapping("/manager/getManagerRolePermission")
    public ApiResult<List<String>> getManagerRolePermission(@RequestParam Integer managerId) {
        return ApiResult.success("查询成功", iSysManagerService.getManagerRolePermission(managerId));
    }

    /**
     * 查询系统数据字典（根据字典编码）
     *
     * @param dictCode
     * @return pers.elianacc.yurayura.vo.ApiResult<java.util.List<pers.elianacc.yurayura.vo.SysDictVO>>
     */
    @GetMapping("/dict/getByDictCode")
    public ApiResult<List<SysDictVO>> getByDictCode(@RequestParam String dictCode) {
        List<SysDictVO> sysDictVOS = iSysDictService
                .getByDictCode(dictCode)
                .stream()
                .map(dict -> {
                    SysDictVO sysDictVO = new SysDictVO();
                    BeanUtils.copyProperties(dict, sysDictVO);
                    return sysDictVO;
                })
                .collect(Collectors.toList());
        return ApiResult.success("查询成功", sysDictVOS);
    }

}
