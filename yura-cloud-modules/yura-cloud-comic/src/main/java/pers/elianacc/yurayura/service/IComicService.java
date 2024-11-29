package pers.elianacc.yurayura.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import pers.elianacc.yurayura.dto.ComicInsertDTO;
import pers.elianacc.yurayura.dto.ComicSelectDTO;
import pers.elianacc.yurayura.dto.ComicUpdateDTO;
import pers.elianacc.yurayura.dto.IdsDTO;
import pers.elianacc.yurayura.entity.Comic;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 番剧 service
 *
 * @author ELiaNaCc
 * @since 2019-10-27
 */
public interface IComicService extends IService<Comic> {

    /**
     * 分页查询番剧
     *
     * @param dto
     * @return com.github.pagehelper.PageInfo<pers.elianacc.yurayura.entity.comic.Comic>
     */
    public PageInfo<Comic> getPage(ComicSelectDTO dto);

    /**
     * 添加番剧
     *
     * @param dto
     * @return void
     */
    public void insert(ComicInsertDTO dto) throws IOException;

    /**
     * 批量删除番剧（根据番剧id组）
     *
     * @param dto
     * @return void
     */
    public void deleteBatchByIds(IdsDTO dto);

    /**
     * 修改番剧
     *
     * @param dto
     * @return void
     */
    public void update(ComicUpdateDTO dto) throws IOException;

    /**
     * 导出excel
     *
     * @param dto
	 * @param response
     * @return void
     */
    public void exportExcel(ComicSelectDTO dto, HttpServletResponse response) throws IOException;

    /**
     * 导入excel
     *
     * @param file
     * @return java.lang.String
     */
    public void importExcel(MultipartFile file) throws Exception;

    /**
     * 下载导入模板
     *
     * @param
     * @return org.springframework.http.ResponseEntity<org.springframework.core.io.FileSystemResource>
     */
    public ResponseEntity<FileSystemResource> downloadImportTplt() throws IOException;
}
