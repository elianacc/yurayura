package pers.elianacc.yurayura.bo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 番剧导入 bo
 *
 * @author ELiaNaCc
 * @since 2024-11-19
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class ComicImportBO extends ExcelImportVerifyBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 图片
     */
    @Excel(name = "图片", type = 2)
    private String comicImage;

    /**
     * 番剧名
     */
    @Excel(name = "番剧名")
    private String comicName;

    /**
     * 评分
     */
    @Excel(name = "评分")
    private Double comicScore;

    /**
     * 简介
     */
    @Excel(name = "简介")
    private String comicContent;

    /**
     * 状态 0：已完结  8：更新中
     */
    @Excel(name = "状态", replace = {"已完结_0", "更新中_8"})
    private Integer comicStatus;

    /**
     * 更新时间 1：周一更新...
     */
    @Excel(name = "更新时间", replace = {"周一更新_1", "周二更新_2", "周三更新_3", "周四更新_4", "周五更新_5", "周六更新_6", "周日更新_7"})
    private Integer comicUdTime;

    /**
     * 当前话数
     */
    @Excel(name = "当前话数")
    private Integer comicCurrentEpisodes;

    /**
     * 标签
     */
    @Excel(name = "标签")
    private String comicLabel;

    /**
     * 自定义标签
     */
    @Excel(name = "自定义标签")
    private String comicCustomLabel;

    /**
     * 放送时间
     */
    @Excel(name = "放送时间")
    private String comicTime;

    /**
     * 链接
     */
    @Excel(name = "链接")
    private String comicLink;

}
