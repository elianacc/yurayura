package org.cny.yurayura.domain;

/**
 * 描述
 *
 * @author CNY
 * @since 2021-08-14
 */
public class TestListGrpBo {

    private Integer menuPermId;

    private Integer buttonPermId;

    public Integer getMenuPermId() {
        return menuPermId;
    }

    public void setMenuPermId(Integer menuPermId) {
        this.menuPermId = menuPermId;
    }

    public Integer getButtonPermId() {
        return buttonPermId;
    }

    public void setButtonPermId(Integer buttonPermId) {
        this.buttonPermId = buttonPermId;
    }

    @Override
    public String toString() {
        return "TestListGrpBo{" +
                "menuPermId=" + menuPermId +
                ", buttonPermId=" + buttonPermId +
                '}';
    }
}
