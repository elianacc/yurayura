package org.cny.yurayura.enumerate;

import lombok.Getter;

/**
 * 图片上传结果 enum
 *
 * @author CNY
 * @since 2020-01-26
 */
@Getter
public enum ImgUploadResultEnum {

    /**
     * 上传图片为空
     */
    NULL("0"),
    /**
     * 上传图片格式必须是.gif,jpeg,jpg,png中的一种
     */
    FORMATNOTALLOW("1"),
    /**
     * 上传图片不能超过100KB
     */
    SIZEBEYOND("2");

    private String result;

    ImgUploadResultEnum(String result) {
        this.result = result;
    }

}
