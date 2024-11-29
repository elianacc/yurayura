package pers.elianacc.yurayura.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * 传递实体主键值组 dto
 *
 * @author ELiaNaCc
 * @since 2022-02-18
 */
@Data
@ApiModel(value = "传递实体主键值组dto")
public class IdsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id组
     */
    @Size(min = 1, message = "id组不能为空")
    @ApiModelProperty(value = "id组（例：[1,2]）", required = true)
    private List<Integer> ids;

}
