package org.elianacc.yurayura.enumerate;

import lombok.Getter;

/**
 * 图片上传结果 enum
 *
 * @author ELiaNaCc
 * @since 2020-01-26
 */
@Getter
public enum ImgUploadResultEnum {

    /**
     * 上传图片为空
     */
    NULL("图片为空"),
    /**
     * 上传图片格式必须是.gif,jpeg,jpg,png中的一种
     */
    FORMATNOTALLOW("图片格式必须是.gif,jpeg,jpg,png中的一种"),
    /**
     * 上传图片不能超过限制大小
     */
    SIZEBEYOND("图片不能超过100KB");

    private String result;

    ImgUploadResultEnum(String result) {
        this.result = result;
    }

}
