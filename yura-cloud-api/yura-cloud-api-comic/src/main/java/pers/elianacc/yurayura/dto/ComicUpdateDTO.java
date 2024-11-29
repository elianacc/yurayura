package pers.elianacc.yurayura.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 番剧修改 dto
 *
 * @author ELiaNaCc
 * @since 2022-02-21
 */
@Data
@ApiModel(value = "番剧修改dto")
public class ComicUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @NotNull(message = "id不能为空")
    @ApiModelProperty(value = "id", required = true, example = "1")
    private Integer id;

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空")
    @Size(max = 30, message = "名称不能超过30个字符")
    @ApiModelProperty(value = "名称", required = true)
    private String comicName;

    /**
     * 评分
     */
    @NotNull(message = "评分不能为空")
    @ApiModelProperty(value = "评分", required = true, example = "1.0")
    private Double comicScore;

    /**
     * 简介
     */
    @Size(max = 500, message = "简介不能超过500个字符")
    @ApiModelProperty(value = "简介")
    private String comicContent;

    /**
     * 放送时间
     */
    @NotBlank(message = "放送时间不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "放送时间", required = true)
    private String comicTime;

    /**
     * 链接
     */
    @ApiModelProperty(value = "链接")
    private String comicLink;

    /**
     * 标签
     */
    @ApiModelProperty(value = "标签")
    private String comicLabel;

    /**
     * 当前话数
     */
    @NotNull(message = "当前话数不能为空")
    @ApiModelProperty(value = "当前话数", required = true, example = "1")
    private Integer comicCurrentEpisodes;

    /**
     * 上架状态 1：上架  0：下架
     */
    @NotNull(message = "上架状态不能为空")
    @ApiModelProperty(value = "上架状态 1：上架  0：下架", required = true, example = "1")
    private Integer comicShelfStatus;

    /**
     * 状态 0：已完结  8：更新中
     */
    @NotNull(message = "状态不能为空")
    @ApiModelProperty(value = "状态 0：已完结  8：更新中", required = true, example = "0")
    private Integer comicStatus;

    /**
     * 更新时间 1：周一更新...
     */
    @ApiModelProperty(value = "更新时间 1：周一更新...")
    private Integer comicUdTime;

    /**
     * 图片文件
     */
    @ApiModelProperty(value = "图片文件")
    private MultipartFile comicImgFile;

}
