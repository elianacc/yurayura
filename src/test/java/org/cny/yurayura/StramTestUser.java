package org.cny.yurayura;

/**
 * 测试-用户
 *
 * @author CNY
 * @since 2020-11-13
 */
public class StramTestUser {
    private Long id;
    private String userName;
    private String userType;
    private String groupType;
    private String userCode;
    private String userTemp;

    public StramTestUser(Long id, String userName, String userCode) {
        this.id = id;
        this.userName = userName;
        this.userCode = userCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserTemp() {
        return userTemp;
    }

    public void setUserTemp(String userTemp) {
        this.userTemp = userTemp;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userType='" + userType + '\'' +
                ", groupType='" + groupType + '\'' +
                ", userCode='" + userCode + '\'' +
                ", userTemp='" + userTemp + '\'' +
                '}';
    }
}
