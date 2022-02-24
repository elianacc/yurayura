package org.elianacc.yurayura.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 传递实体主键值组 dto
 *
 * @author ELiaNaCc
 * @since 2022-02-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "传递实体主键值组dto")
public class IdsDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id组
     */
    @ApiModelProperty(value = "id组（例：[1,2]）", required = true)
    private List<Integer> ids;

}
