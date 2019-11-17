package org.cny.yurayura.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.cny.yurayura.entity.Comic;

/**
 * <p>
 * 番剧 service
 * </p>
 *
 * @author CNY
 * @since 2019-10-27
 */
public interface IComicService extends IService<Comic> {

    /**
     * 分页查询所有番剧
     *
     * @param pageNum
     * @return com.baomidou.mybatisplus.core.metadata.IPage<org.cny.yurayura.entity.Comic>
     */
    public PageInfo<Comic> getPageToAll(Integer pageNum);

    /**
     * 分页查询番剧（根据番剧名）
     *
     * @param pageNum
	 * @param comicName
     * @return com.github.pagehelper.PageInfo<org.cny.yurayura.entity.Comic>
     */
    public PageInfo<Comic> getPageByName(Integer pageNum, String comicName);

}
