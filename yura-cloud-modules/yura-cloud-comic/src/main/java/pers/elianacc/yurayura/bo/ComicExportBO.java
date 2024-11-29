package pers.elianacc.yurayura.bo;

import cn.afterturn.easypoi.entity.ImageEntity;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 番剧导出 bo
 *
 * @author ELiaNaCc
 * @since 2024-11-04
 */
@Data
public class ComicExportBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 图片
     */
    private ImageEntity comicImage;

    /**
     * 名称
     */
    private String comicName;

    /**
     * 评分
     */
    private Double comicScore;

    /**
     * 简介
     */
    private String comicContent;

    /**
     * 当前状态
     */
    private String comicCurtStatus;

    /**
     * 标签
     */
    private String comicLabel;

    /**
     * 放送时间
     */
    private LocalDate comicTime;

}
