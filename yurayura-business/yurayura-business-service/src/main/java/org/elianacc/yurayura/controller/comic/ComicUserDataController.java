package org.elianacc.yurayura.controller.comic;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.elianacc.yurayura.service.comic.IComicUserDataService;
import org.elianacc.yurayura.vo.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 番剧用户数据 controller
 *
 * @author ELiaNaCc
 * @since 2020-04-02
 */
@RestController
@RequestMapping("/api/comicUserData")
@Api(tags = "番剧用户数据API")
public class ComicUserDataController {

    @Autowired
    private IComicUserDataService iComicUserDataService;

    /**
     * 查询番剧用户数据（根据id）
     *
     * @param id
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @GetMapping("/getById")
    @ApiOperation("查询番剧用户数据（根据id）")
    @ApiImplicitParam(name = "id", value = "id", required = true, defaultValue = "1", dataType = "int")
    public ApiResult getById(Integer id) {
        if (ObjectUtils.isEmpty(id)) {
            return ApiResult.warn("id不能为空");
        }
        return ApiResult.success("查询成功", iComicUserDataService.getById(id));
    }
}

