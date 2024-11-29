package pers.elianacc.yurayura.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 系统子菜单添加 dto
 *
 * @author ELiaNaCc
 * @since 2022-02-23
 */
@Data
@ApiModel(value = "系统子菜单添加dto")
public class SysMenuSubInsertDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 标题
     */
    @NotBlank(message = "标题不能为空")
    @Size(max = 20, message = "标题不能超过20个字符")
    @ApiModelProperty(value = "标题", required = true)
    private String menuTitle;

    /**
     * 标识
     */
    @NotBlank(message = "标识不能为空")
    @Size(max = 20, message = "标识不能超过20个字符")
    @Pattern(regexp = "^[a-z][a-z_]*$", message = "标识只能包含小写字母下划线，以小写字母开头")
    @ApiModelProperty(value = "标识", required = true)
    private String menuName;

    /**
     * 路径
     */
    @NotBlank(message = "路径不能为空")
    @ApiModelProperty(value = "路径", required = true)
    private String menuIndex;

    /**
     * 图标样式
     */
    @NotBlank(message = "图标样式不能为空")
    @Size(max = 40, message = "图标样式不能超过40个字符")
    @ApiModelProperty(value = "图标样式", required = true)
    private String menuIconClass;

    /**
     * 父菜单id
     */
    @NotNull(message = "父菜单id不能为空")
    @ApiModelProperty(value = "父菜单id", required = true, example = "1")
    private Integer menuPid;

    /**
     * 序号
     */
    @NotNull(message = "序号不能为空")
    @ApiModelProperty(value = "序号", required = true, example = "1")
    private Integer menuSeq;

    /**
     * 状态- 0：隐藏，1：显示
     */
    @NotNull(message = "状态不能为空")
    @ApiModelProperty(value = "状态- 0：隐藏，1：显示", required = true, example = "1")
    private Integer menuStatus;

}
