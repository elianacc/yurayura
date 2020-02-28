package org.cny.yurayura.enumerate;

import lombok.Getter;

/**
 * 用户状态 enum
 *
 * @author CNY
 * @since 2019年12月30日 16:03
 */
@Getter
public enum UserStatusEnum {

    /**
     * 正常
     */
    NORMAL(0),
    /**
     * 小黑屋3天
     */
    BLACKHOUSE3(3),
    /**
     * 小黑屋7天
     */
    BLACKHOUSE7(7),
    /**
     * 小黑屋30天
     */
    BLACKHOUSE30(30),
    /**
     * 小黑屋365天
     */
    BLACKHOUSE365(365),
    /**
     * 小黑屋永久
     */
    BLACKHOUSE999(999);

    private Integer statusId;

    UserStatusEnum(int statusId) {
        this.statusId = statusId;
    }

}
