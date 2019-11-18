package org.cny.yurayura.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 番剧数据
 * </p>
 *
 * @author CNY
 * @since 2019-11-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("yurayura_comic_count")
@ApiModel(value="ComicCount对象", description="番剧数据")
public class ComicCount implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "名称")
    private String comicName;

    @ApiModelProperty(value = "当前集数")
    private Integer comicNowEpisodes;

    @ApiModelProperty(value = "总集数")
    private Integer comicEpisodes;

    @ApiModelProperty(value = "播放数")
    private Integer comicViews;

    @ApiModelProperty(value = "追番数")
    private Integer comicFavoriteNum;

    @ApiModelProperty(value = "关联comic表id")
    private Integer comicId;


}
