package org.cny.yurayura.service.comic;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.cny.yurayura.entity.comic.Comic;

/**
 * 番剧 service
 *
 * @author CNY
 * @since 2019-10-27
 */
public interface IComicService extends IService<Comic> {

    /**
     * 分页查询所有番剧
     *
     * @param pageNum
     * @return com.github.pagehelper.PageInfo<org.cny.yurayura.entity.comic.Comic>
     */
    public PageInfo<Comic> getPageToAll(Integer pageNum);

    /**
     * 分页查询番剧（根据名称）
     *
     * @param pageNum
     * @param comicName
     * @return com.github.pagehelper.PageInfo<org.cny.yurayura.entity.comic.Comic>
     */
    public PageInfo<Comic> getPageByName(Integer pageNum, String comicName);

}
