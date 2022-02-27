package org.elianacc.yurayura.entity.comic;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 番剧用户数据 entity
 *
 * @author ELiaNaCc
 * @since 2022-02-26
 */
@Data
@TableName("yurayura_comic_user_data")
@ApiModel(value = "ComicUserData对象", description = "番剧用户数据")
public class ComicUserData implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 名称
     */
    @TableField("comic_name")
    @ApiModelProperty(value = "名称")
    private String comicName;

    /**
     * 播放数
     */
    @TableField("comic_play_num")
    @ApiModelProperty(value = "播放数")
    private Integer comicPlayNum;

    /**
     * 追番数
     */
    @TableField("comic_favorite_num")
    @ApiModelProperty(value = "追番数")
    private Integer comicFavoriteNum;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("comic_user_data_create_time")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime comicUserDataCreateTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("comic_user_data_update_time")
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime comicUserDataUpdateTime;

    /**
     * 关联comic表id
     */
    @TableField("comic_id")
    @ApiModelProperty(value = "关联comic表id")
    private Integer comicId;


}
