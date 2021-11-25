package org.elianacc.yurayura.enumerate;

import lombok.Getter;

/**
 * 上传图片类别 enum
 *
 * @author ELiaNaCc
 * @since 2021-04-15
 */
@Getter
public enum ImgUploadCategoryEnum {

    /**
     * 番剧图片
     */
    COMICIMG("comicImg"),
    /**
     * 用户头像
     */
    USERAVATAR("userAvatar");

    private String category;

    ImgUploadCategoryEnum(String category) {
        this.category = category;
    }

}
