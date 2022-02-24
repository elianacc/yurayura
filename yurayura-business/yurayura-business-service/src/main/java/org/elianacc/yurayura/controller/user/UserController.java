package org.elianacc.yurayura.controller.user;


import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.elianacc.yurayura.dto.IdDto;
import org.elianacc.yurayura.dto.UserSelectDto;
import org.elianacc.yurayura.dto.UserUpdateStatusDto;
import org.elianacc.yurayura.entity.user.User;
import org.elianacc.yurayura.service.user.IUserService;
import org.elianacc.yurayura.system.annotation.PreventRepeatSubmit;
import org.elianacc.yurayura.vo.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 用户 controller
 *
 * @author ELiaNaCc
 * @since 2019-10-27
 */
@RestController
@RequestMapping("/api/user")
@Api(tags = "用户API")
public class UserController {

    @Autowired
    private IUserService iUserService;

    /**
     * 查询用户（根据用户id）
     *
     * @param dto
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @GetMapping("/getById")
    @ApiOperation("查询用户（根据用户id）")
    public ApiResult getById(IdDto dto) {
        if (ObjectUtils.isEmpty(dto.getId())) {
            return ApiResult.warn("id不能为空");
        }
        return ApiResult.success("查询成功", iUserService.getById(dto.getId()));
    }

    /**
     * 分页查询用户
     *
     * @param dto
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @PostMapping("/getPage")
    @ApiOperation("分页查询用户")
    public ApiResult getPage(@RequestBody UserSelectDto dto) {
        if (ObjectUtils.isEmpty(dto.getPageNum())) {
            return ApiResult.warn("页码不能为空");
        } else if (ObjectUtils.isEmpty(dto.getPageSize())) {
            dto.setPageSize(10); // 页记录数默认10
        }
        PageInfo<User> pageInfo = iUserService.getPage(dto);
        if (pageInfo.getTotal() == 0) {
            return ApiResult.warn("查询不到数据");
        }
        return ApiResult.success("分页查询成功", pageInfo);
    }

    /**
     * 修改状态（根据用户id）
     *
     * @param dto
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @PreventRepeatSubmit
    @PutMapping("/updateStatus")
    @ApiOperation("修改状态（根据用户id）")
    public ApiResult updateStatus(@RequestBody UserUpdateStatusDto dto) {
        if (ObjectUtils.isEmpty(dto.getId())) {
            return ApiResult.warn("id不能为空");
        } else if (ObjectUtils.isEmpty(dto.getUserStatus())) {
            return ApiResult.warn("状态不能为空");
        }
        iUserService.updateStatus(dto);
        return ApiResult.success("修改状态成功");
    }

    /**
     * 重置为默认头像（根据用户id）
     *
     * @param dto
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @PutMapping("/updateAvatarDefault")
    @ApiOperation("重置为默认头像（根据用户id）")
    public ApiResult updateAvatarDefault(@RequestBody IdDto dto) {
        if (ObjectUtils.isEmpty(dto.getId())) {
            return ApiResult.warn("id不能为空");
        }
        iUserService.updateAvatarDefault(dto);
        return ApiResult.success("重置为默认头像成功");
    }

}

