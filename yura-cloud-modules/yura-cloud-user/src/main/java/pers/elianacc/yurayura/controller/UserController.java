package pers.elianacc.yurayura.controller;

import com.baomidou.lock.annotation.Lock4j;
import com.github.pagehelper.PageInfo;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.elianacc.yurayura.dto.IdDTO;
import pers.elianacc.yurayura.dto.UserSelectDTO;
import pers.elianacc.yurayura.dto.UserUpdateStatusDTO;
import pers.elianacc.yurayura.entity.User;
import pers.elianacc.yurayura.service.IUserService;
import pers.elianacc.yurayura.vo.ApiResult;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户 controller
 *
 * @author ELiaNaCc
 * @since 2022-10-14
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
     * @return pers.elianacc.yurayura.vo.ApiResult<pers.elianacc.yurayura.entity.user.User>
     */
    @GetMapping("/getById")
    @ApiOperation("查询用户（根据用户id）")
    public ApiResult<User> getById(IdDTO dto) {
        return ApiResult.success("查询成功", iUserService.getById(dto.getId()));
    }

    /**
     * 分页查询用户
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<PageInfo<User>>
     */
    @PostMapping("/getPage")
    @ApiOperation("分页查询用户")
    public ApiResult<PageInfo<User>> getPage(@Validated @RequestBody UserSelectDTO dto) {
        return ApiResult.success("分页查询成功", iUserService.getPage(dto));
    }

    /**
     * 修改状态（根据用户id）
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PutMapping("/updateStatus")
    @Lock4j(keys = {"#dto.id"}, autoRelease = false)
    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @ApiOperation("修改状态（根据用户id）")
    public ApiResult<String> updateStatus(@Validated @RequestBody UserUpdateStatusDTO dto) {
        iUserService.updateStatus(dto);
        return ApiResult.success("修改状态成功");
    }

    /**
     * 重置为默认头像（根据用户id）
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PutMapping("/updateAvatarDefault")
    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @ApiOperation("重置为默认头像（根据用户id）")
    public ApiResult<String> updateAvatarDefault(@Validated @RequestBody IdDTO dto) {
        iUserService.updateAvatarDefault(dto);
        return ApiResult.success("重置为默认头像成功");
    }

    /**
     * 导出
     *
     * @param dto
	 * @param response
     * @return void
     */
    @GetMapping("/export")
    @ApiOperation("导出")
    public void export(UserSelectDTO dto, @ApiIgnore HttpServletResponse response) throws IOException {
        iUserService.exportExcel(dto, response);
    }

}
