package org.elianacc.yurayura.bo;

import lombok.Data;
import org.elianacc.yurayura.entity.sys.menu.MenuSub;

import java.io.Serializable;
import java.util.List;

/**
 * 系统菜单树形展示查询用 bo
 *
 * @author ELiaNaCc
 * @since 2021-03-16
 */
@Data
public class MenuTreeSelectBo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer id;

    /**
     * 标题
     */
    private String menuTitle;

    /**
     * 标识
     */
    private String menuName;

    /**
     * 图标样式
     */
    private String menuIconClass;

    /**
     * 类型- 1：一级菜单，2：二级菜单
     */
    private Integer menuType;

    /**
     * 序号
     */
    private Integer menuSeq;

    /**
     * 状态- 0：禁用，1：启用
     */
    private Integer menuStatus;

    /**
     * 子菜单列表
     */
    private List<MenuSub> menuSubList;

}
