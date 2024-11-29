package pers.elianacc.yurayura.controller.block;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.github.pagehelper.PageInfo;
import pers.elianacc.yurayura.dto.ComicSelectDTO;
import pers.elianacc.yurayura.entity.Comic;
import pers.elianacc.yurayura.vo.ApiResult;

/**
 * 番剧 自定义限流处理逻辑
 *
 * @author ELiaNaCc
 * @since 2022-08-30
 */
public class ComicBlockHandler {

    public static ApiResult<PageInfo<Comic>> getPageBlockHandler(ComicSelectDTO dto, BlockException exception) {
        return ApiResult.warn("操作过于频繁！");
    }

}
