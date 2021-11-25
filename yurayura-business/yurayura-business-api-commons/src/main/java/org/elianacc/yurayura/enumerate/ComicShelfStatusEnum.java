package org.elianacc.yurayura.enumerate;

import lombok.Getter;

/**
 * 番剧上架状态 enum
 *
 * @author ELiaNaCc
 * @since 2019-11-16
 */
@Getter
public enum ComicShelfStatusEnum {
    /**
     * 上架
     */
    MONDAY(1),
    /**
     * 下架
     */
    FINISHED(0);

    private Integer statusId;

    ComicShelfStatusEnum(int statusId) {
        this.statusId = statusId;
    }

}
