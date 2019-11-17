package org.cny.yurayura.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author CNY
 * @since 2019-11-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("yurayura_comic_count")
public class ComicCount implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    private String comicName;

    /**
     * 当前集数
     */
    private Integer comicNowEpisodes;

    /**
     * 总集数
     */
    private Integer comicEpisodes;

    /**
     * 播放数
     */
    private Integer comicViews;

    /**
     * 追番数
     */
    private Integer comicFavoriteNum;

    /**
     * 关联comic表id
     */
    private Integer comicId;


}
