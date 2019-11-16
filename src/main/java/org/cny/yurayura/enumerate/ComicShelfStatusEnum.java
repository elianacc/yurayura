package org.cny.yurayura.enumerate;

import lombok.Getter;

/**
 * 番剧上架状态枚举
 *
 * @author CNY
 * @date 2019年11月16日 18:10
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
