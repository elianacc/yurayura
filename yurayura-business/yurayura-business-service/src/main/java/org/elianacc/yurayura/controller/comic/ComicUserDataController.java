package org.elianacc.yurayura.controller.comic;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.elianacc.yurayura.dto.IdDto;
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
     * 查询番剧用户数据（根据番剧用户数据id）
     *
     * @param dto
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @GetMapping("/getById")
    @ApiOperation("查询番剧用户数据（根据番剧用户数据id）")
    public ApiResult getById(IdDto dto) {
        if (ObjectUtils.isEmpty(dto.getId())) {
            return ApiResult.warn("id不能为空");
        }
        return ApiResult.success("查询成功", iComicUserDataService.getById(dto.getId()));
    }
}

