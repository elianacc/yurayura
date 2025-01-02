package pers.elianacc.yurayura.controller;

import cn.hutool.core.lang.Assert;
import com.baomidou.lock.annotation.Lock4j;
import com.github.pagehelper.PageInfo;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pers.elianacc.yurayura.dto.*;
import pers.elianacc.yurayura.entity.Comic;
import pers.elianacc.yurayura.service.IComicService;
import pers.elianacc.yurayura.vo.ApiResult;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 番剧 controller
 *
 * @author ELiaNaCc
 * @since 2022-10-09
 */
@RestController
@RequestMapping("/api/comic")
@Api(tags = "番剧API")
public class ComicController {

    @Autowired
    private IComicService iComicService;

    /**
     * 查询番剧（根据番剧id）
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<pers.elianacc.yurayura.entity.comic.Comic>
     */
    @GetMapping("/getById")
    @ApiOperation("查询番剧（根据番剧id）")
    public ApiResult<Comic> getById(IdDTO dto) {
        return ApiResult.success("查询成功", iComicService.getById(dto.getId()));
    }

    /**
     * 分页查询番剧
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<PageInfo<Comic>>
     */
    @PostMapping("/getPage")
    @Lock4j(keys = {"T(cn.dev33.satoken.stp.StpUtil).getTokenValue()"}, autoRelease = false)
    @ApiOperation("分页查询番剧")
    public ApiResult<PageInfo<Comic>> getPage(@Validated @RequestBody ComicSelectDTO dto) {
        return ApiResult.success("分页查询成功", iComicService.getPage(dto));
    }

    /**
     * 添加番剧
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PostMapping("/insert")
    @Lock4j(keys = {"T(cn.dev33.satoken.stp.StpUtil).getTokenValue()", "#dto.comicName"}, autoRelease = false)
    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @ApiOperation("添加番剧")
    public ApiResult<String> insert(@Validated ComicInsertDTO dto) throws IOException {
        iComicService.insert(dto);
        return ApiResult.success("添加成功");
    }

    /**
     * 批量删除番剧（根据番剧id组）
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PutMapping("/deleteBatchByIds")
    @Lock4j(keys = {"T(cn.dev33.satoken.stp.StpUtil).getTokenValue()"}, autoRelease = false)
    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @ApiOperation("批量删除番剧（根据番剧id组）")
    public ApiResult<String> deleteBatchByIds(@Validated @RequestBody IdsDTO dto) {
        iComicService.deleteBatchByIds(dto);
        return ApiResult.success("删除成功");
    }

    /**
     * 修改番剧
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PutMapping("/update")
    @Lock4j(keys = {"T(cn.dev33.satoken.stp.StpUtil).getTokenValue()", "#dto.id"}, autoRelease = false)
    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @ApiOperation("修改番剧")
    public ApiResult<String> update(@Validated ComicUpdateDTO dto) throws IOException {
        iComicService.update(dto);
        return ApiResult.success("修改成功");
    }

    /**
     * 导出
     *
     * @param dto
	 * @param response
     * @return void
     */
    @GetMapping("/export")
    @Lock4j(keys = {"T(cn.dev33.satoken.stp.StpUtil).getTokenValue()"}, expire = 10000, autoRelease = false)
    @ApiOperation("导出")
    public void export(ComicSelectDTO dto, @ApiIgnore HttpServletResponse response)
            throws IOException {
        iComicService.exportExcel(dto, response);
    }

    /**
     * 导入
     *
     * @param file
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PostMapping("/import")
    @Lock4j(keys = {"T(cn.dev33.satoken.stp.StpUtil).getTokenValue()"}, autoRelease = false)
    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @ApiOperation("导入")
    public ApiResult<String> importExcel(@RequestPart("file") MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        String fileEndName = fileName.substring(fileName.lastIndexOf("."));
        Assert.isTrue(fileEndName.equals(".xlsx"), "上传文件格式不正确");
        Assert.isTrue(file.getSize() < 10000 * 1024, "上传文件大小不能超过10M");
        iComicService.importExcel(file);
        return ApiResult.success("导入成功");
    }

    /**
     * 下载导入模板
     *
     * @param
     * @return org.springframework.http.ResponseEntity<org.springframework.core.io.InputStreamResource>
     */
    @GetMapping("/downloadImportTplt")
    @Lock4j(keys = {"T(cn.dev33.satoken.stp.StpUtil).getTokenValue()"}, expire = 10000, autoRelease = false)
    @ApiOperation("下载导入模板")
    public ResponseEntity<InputStreamResource> downloadImportTplt() throws IOException {
        return iComicService.downloadImportTplt();
    }

}
