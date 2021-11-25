package org.elianacc.yurayura.enumerate;

import lombok.Getter;

/**
 * 番剧状态 enum
 *
 * @author ELiaNaCc
 * @since 2019-11-12
 */
@Getter
public enum ComicStatusEnum {

    /**
     * 已完结
     */
    FINISHED(0),
    /**
     * 周一更新
     */
    MONDAY(1),
    /**
     * 周二更新
     */
    TUESDAY(2),
    /**
     * 周三更新
     */
    WEDNESDAY(3),
    /**
     * 周四更新
     */
    THURSDAY(4),
    /**
     * 周五更新
     */
    FRIDAY(5),
    /**
     * 周六更新
     */
    SATURDAY(6),
    /**
     * 周日更新
     */
    SUNDAY(7),
    /**
     * 更新中
     */
    UPDATING(8);

    private Integer statusId;

    ComicStatusEnum(int statusId) {
        this.statusId = statusId;
    }

}
