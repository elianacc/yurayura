## 建表

```sql
DROP TABLE IF EXISTS `yurayura_sys_dict`;
CREATE TABLE `yurayura_sys_dict` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `dict_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典编码',
  `dict_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典名',
  `dict_val` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典值',
  `dict_status` int(1) NOT NULL COMMENT '状态- 0：禁用，1：启用',
  `dict_seq` int(5) NOT NULL COMMENT '序号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 COMMENT='系统数据字典';

DROP TABLE IF EXISTS `yurayura_sys_manager`;
CREATE TABLE `yurayura_sys_manager` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `manager_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '管理员名',
  `manager_password` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '管理员密码',
  `manager_status` int(1) NOT NULL COMMENT '状态- 0：禁用，1：启用',
  `manager_create_time` datetime NOT NULL COMMENT '创建时间',
  `manager_update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统管理员';

DROP TABLE IF EXISTS `yurayura_sys_manager_permission`;
CREATE TABLE `yurayura_sys_manager_permission` (
  `manager_id` int(12) NOT NULL COMMENT '管理员id',
  `permission_id` int(12) NOT NULL COMMENT '权限id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统管理员-系统权限中间表';

DROP TABLE IF EXISTS `yurayura_sys_menu`;
CREATE TABLE `yurayura_sys_menu` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `menu_title` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标题',
  `menu_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标识',
  `menu_icon_class` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图标样式',
  `menu_type` int(1) NOT NULL COMMENT '类型- 1：一级菜单，2：二级菜单',
  `menu_seq` int(5) NOT NULL COMMENT '序号',
  `menu_status` int(1) NOT NULL COMMENT '状态- 0：隐藏，1：显示',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='系统菜单';

DROP TABLE IF EXISTS `yurayura_sys_menu_sub`;
CREATE TABLE `yurayura_sys_menu_sub` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `menu_title` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标题',
  `menu_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标识',
  `menu_index` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '路径',
  `menu_icon_class` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图标样式',
  `menu_pid` int(12) NOT NULL COMMENT '父菜单id',
  `menu_type` int(1) NOT NULL COMMENT '类型- 1：一级菜单，2：二级菜单',
  `menu_seq` int(5) NOT NULL COMMENT '序号',
  `menu_status` int(1) NOT NULL COMMENT '状态- 0：隐藏，1：显示',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='系统子菜单';

DROP TABLE IF EXISTS `yurayura_sys_permission`;
CREATE TABLE `yurayura_sys_permission` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `permission_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限编码',
  `permission_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限名称',
  `permission_type` int(1) NOT NULL COMMENT '权限类型- 1：菜单，2：按钮',
  `permission_status` int(1) NOT NULL COMMENT '状态- 0：禁用，1：启用',
  `permission_belong_submenu_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '所属子菜单标识',
  `permission_seq` int(5) NOT NULL COMMENT '序号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='系统权限';

DROP TABLE IF EXISTS `yurayura_comic`;
CREATE TABLE `yurayura_comic` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `comic_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称',
  `comic_score` double(5,1) DEFAULT NULL COMMENT '评分',
  `comic_content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '简介',
  `comic_time` date NOT NULL COMMENT '放送时间',
  `comic_image_url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '图片地址',
  `comic_link` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '链接',
  `comic_label` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '标签',
  `comic_status` int(1) NOT NULL COMMENT '状态- 0：已完结，1：周一更新...，8：更新中',
  `comic_current_episodes` int(5) DEFAULT NULL COMMENT '当前话数',
  `comic_shelf_status` int(1) NOT NULL COMMENT '上架状态- 1：上架，0：下架',
  `comic_create_time` datetime NOT NULL COMMENT '创建时间',
  `comic_update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='番剧';

DROP TABLE IF EXISTS `yurayura_comic_user_data`;
CREATE TABLE `yurayura_comic_user_data` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `comic_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称',
  `comic_play_num` int(11) NOT NULL COMMENT '播放数',
  `comic_favorite_num` int(11) NOT NULL COMMENT '追番数',
  `comic_user_data_create_time` datetime NOT NULL COMMENT '创建时间',
  `comic_user_data_update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `comic_id` int(12) NOT NULL COMMENT '关联comic表id',
  PRIMARY KEY (`id`),
  KEY `comic_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='番剧用户数据';

DROP TABLE IF EXISTS `yurayura_user`;
CREATE TABLE `yurayura_user` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `user_password` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `user_nickname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '昵称',
  `user_signature` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '签名',
  `user_sex` int(1) DEFAULT NULL COMMENT '性别- 1：男，0：女',
  `user_birthday` date DEFAULT NULL COMMENT '生日',
  `user_province` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '省份',
  `user_city` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '城市',
  `user_avatar_url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '头像地址',
  `user_email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '邮箱',
  `user_phone_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '手机号',
  `user_status` int(5) NOT NULL COMMENT '状态- 0：正常，-3：小黑屋3天，-7：小黑屋7天，-30：小黑屋30天，-365：小黑屋365天，-999：小黑屋永久',
  `user_reg_time` datetime NOT NULL COMMENT '注册时间',
  `user_current_edit_time` datetime DEFAULT NULL COMMENT '最近编辑时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户';

```

## 初始化系统表基础数据

```sql
INSERT INTO `yurayura_sys_dict` VALUES ('1', 'comicLabel', '热血', '热血', '1', '1');
INSERT INTO `yurayura_sys_dict` VALUES ('2', 'comicLabel', '机战', '机战', '1', '2');
INSERT INTO `yurayura_sys_dict` VALUES ('3', 'comicLabel', '萌系', '萌系', '1', '3');
INSERT INTO `yurayura_sys_dict` VALUES ('4', 'comicLabel', '催泪', '催泪', '1', '4');
INSERT INTO `yurayura_sys_dict` VALUES ('5', 'comicLabel', '搞笑', '搞笑', '1', '5');
INSERT INTO `yurayura_sys_dict` VALUES ('6', 'comicLabel', '治愈', '治愈', '1', '6');
INSERT INTO `yurayura_sys_dict` VALUES ('7', 'comicLabel', '恐怖', '恐怖', '1', '7');
INSERT INTO `yurayura_sys_dict` VALUES ('8', 'comicLabel', '推理', '推理', '1', '8');
INSERT INTO `yurayura_sys_dict` VALUES ('9', 'comicLabel', '战斗', '战斗', '1', '9');
INSERT INTO `yurayura_sys_dict` VALUES ('10', 'comicLabel', '萝莉', '萝莉', '1', '10');
INSERT INTO `yurayura_sys_dict` VALUES ('11', 'comicLabel', '校园', '校园', '1', '11');
INSERT INTO `yurayura_sys_dict` VALUES ('12', 'comicLabel', '百合', '百合', '1', '12');
INSERT INTO `yurayura_sys_dict` VALUES ('13', 'comicLabel', '日常', '日常', '1', '13');
INSERT INTO `yurayura_sys_dict` VALUES ('14', 'comicLabel', '致郁', '致郁', '1', '14');
INSERT INTO `yurayura_sys_dict` VALUES ('15', 'comicLabel', '科幻', '科幻', '1', '15');
INSERT INTO `yurayura_sys_dict` VALUES ('16', 'comicLabel', '奇幻', '奇幻', '1', '16');
INSERT INTO `yurayura_sys_dict` VALUES ('17', 'comicLabel', '冒险', '冒险', '1', '17');
INSERT INTO `yurayura_sys_dict` VALUES ('18', 'comicLabel', '剧场版', '剧场版', '1', '18');
INSERT INTO `yurayura_sys_dict` VALUES ('19', 'comicStatus', '已完结', '0', '1', '1');
INSERT INTO `yurayura_sys_dict` VALUES ('20', 'comicStatus', '周一更新', '1', '1', '2');
INSERT INTO `yurayura_sys_dict` VALUES ('21', 'comicStatus', '周二更新', '2', '1', '3');
INSERT INTO `yurayura_sys_dict` VALUES ('22', 'comicStatus', '周三更新', '3', '1', '4');
INSERT INTO `yurayura_sys_dict` VALUES ('23', 'comicStatus', '周四更新', '4', '1', '5');
INSERT INTO `yurayura_sys_dict` VALUES ('24', 'comicStatus', '周五更新', '5', '1', '6');
INSERT INTO `yurayura_sys_dict` VALUES ('25', 'comicStatus', '周六更新', '6', '1', '7');
INSERT INTO `yurayura_sys_dict` VALUES ('26', 'comicStatus', '周日更新', '7', '1', '8');
INSERT INTO `yurayura_sys_dict` VALUES ('27', 'comicStatus', '更新中', '8', '1', '9');
INSERT INTO `yurayura_sys_dict` VALUES ('28', 'comicUpdtTime', '周一更新', '1', '1', '1');
INSERT INTO `yurayura_sys_dict` VALUES ('29', 'comicUpdtTime', '周二更新', '2', '1', '2');
INSERT INTO `yurayura_sys_dict` VALUES ('30', 'comicUpdtTime', '周三更新', '3', '1', '3');
INSERT INTO `yurayura_sys_dict` VALUES ('31', 'comicUpdtTime', '周四更新', '4', '1', '4');
INSERT INTO `yurayura_sys_dict` VALUES ('32', 'comicUpdtTime', '周五更新', '5', '1', '5');
INSERT INTO `yurayura_sys_dict` VALUES ('33', 'comicUpdtTime', '周六更新', '6', '1', '6');
INSERT INTO `yurayura_sys_dict` VALUES ('34', 'comicUpdtTime', '周日更新', '7', '1', '7');
INSERT INTO `yurayura_sys_dict` VALUES ('35', 'userStatus', '正常', '0', '1', '1');
INSERT INTO `yurayura_sys_dict` VALUES ('36', 'userStatus', '小黑屋3天', '-3', '1', '2');
INSERT INTO `yurayura_sys_dict` VALUES ('37', 'userStatus', '小黑屋一周', '-7', '1', '3');
INSERT INTO `yurayura_sys_dict` VALUES ('38', 'userStatus', '小黑屋一月', '-30', '1', '4');
INSERT INTO `yurayura_sys_dict` VALUES ('39', 'userStatus', '小黑屋一年', '-365', '1', '5');
INSERT INTO `yurayura_sys_dict` VALUES ('40', 'userStatus', '小黑屋永久', '-999', '1', '6');
INSERT INTO `yurayura_sys_dict` VALUES ('41', 'permissionBtn', '添加', 'insert', '1', '1');
INSERT INTO `yurayura_sys_dict` VALUES ('42', 'permissionBtn', '修改', 'update', '1', '2');
INSERT INTO `yurayura_sys_dict` VALUES ('43', 'permissionBtn', '删除', 'delete', '1', '3');
INSERT INTO `yurayura_sys_dict` VALUES ('44', 'permissionBtn', '批量删除', 'deleteBatch', '1', '4');

INSERT INTO `yurayura_sys_manager` VALUES ('1', 'admin', 'db69fc039dcbd2962cb4d28f5891aae1', '1', '2019-12-30 15:09:20', NULL);
INSERT INTO `yurayura_sys_manager` VALUES ('2', 'business', '87d9bb400c0634691f0e3baaf1e2fd0d', '1', '2021-04-19 09:40:05', NULL);

INSERT INTO `yurayura_sys_manager_permission` VALUES ('1', '1');
INSERT INTO `yurayura_sys_manager_permission` VALUES ('1', '2');
INSERT INTO `yurayura_sys_manager_permission` VALUES ('1', '3');
INSERT INTO `yurayura_sys_manager_permission` VALUES ('1', '4');
INSERT INTO `yurayura_sys_manager_permission` VALUES ('1', '5');
INSERT INTO `yurayura_sys_manager_permission` VALUES ('1', '6');
INSERT INTO `yurayura_sys_manager_permission` VALUES ('1', '7');
INSERT INTO `yurayura_sys_manager_permission` VALUES ('1', '8');
INSERT INTO `yurayura_sys_manager_permission` VALUES ('1', '9');
INSERT INTO `yurayura_sys_manager_permission` VALUES ('1', '10');
INSERT INTO `yurayura_sys_manager_permission` VALUES ('1', '11');
INSERT INTO `yurayura_sys_manager_permission` VALUES ('1', '12');
INSERT INTO `yurayura_sys_manager_permission` VALUES ('1', '13');
INSERT INTO `yurayura_sys_manager_permission` VALUES ('1', '14');
INSERT INTO `yurayura_sys_manager_permission` VALUES ('1', '15');
INSERT INTO `yurayura_sys_manager_permission` VALUES ('1', '16');
INSERT INTO `yurayura_sys_manager_permission` VALUES ('1', '17');
INSERT INTO `yurayura_sys_manager_permission` VALUES ('1', '18');
INSERT INTO `yurayura_sys_manager_permission` VALUES ('2', '17');
INSERT INTO `yurayura_sys_manager_permission` VALUES ('2', '18');

INSERT INTO `yurayura_sys_menu` VALUES ('1', '系统管理', 'sys', 'el-icon-s-tools', '1', '1', '1');
INSERT INTO `yurayura_sys_menu` VALUES ('2', '番剧管理', 'comic', 'el-icon-ship', '1', '2', '1');
INSERT INTO `yurayura_sys_menu` VALUES ('3', '用户管理', 'user', 'el-icon-user', '1', '3', '1');

INSERT INTO `yurayura_sys_menu_sub` VALUES ('1', '菜单管理', 'sys_menu', '/business/sys_menu', 'fa fa-th-large fa-lg', '1', '2', '1', '1');
INSERT INTO `yurayura_sys_menu_sub` VALUES ('2', '数据字典管理', 'sys_dict', '/business/sys_dict', 'fa fa-book fa-lg', '1', '2', '2', '1');
INSERT INTO `yurayura_sys_menu_sub` VALUES ('3', '管理员管理', 'sys_manager', '/business/sys_manager', 'fa fa-user-secret fa-lg', '1', '2', '3', '1');
INSERT INTO `yurayura_sys_menu_sub` VALUES ('4', '权限管理', 'sys_permission', '/business/sys_permission', 'fa fa-key fa-lg', '1', '2', '4', '1');
INSERT INTO `yurayura_sys_menu_sub` VALUES ('5', '番剧信息管理', 'comic_info', '/business/comic_info', 'fa fa-github-alt fa-lg', '2', '2', '1', '1');
INSERT INTO `yurayura_sys_menu_sub` VALUES ('6', '用户资料管理', 'user_info', '/business/user_info', 'fa fa-address-card fa-lg', '3', '2', '1', '1');

INSERT INTO `yurayura_sys_permission` VALUES ('1', 'sys_menu_select', '菜单管理查看', '1', '1', 'sys_menu', '1');
INSERT INTO `yurayura_sys_permission` VALUES ('2', 'sys_menu_insert', '菜单管理添加', '2', '1', 'sys_menu', '2');
INSERT INTO `yurayura_sys_permission` VALUES ('3', 'sys_menu_update', '菜单管理修改', '2', '1', 'sys_menu', '3');
INSERT INTO `yurayura_sys_permission` VALUES ('4', 'sys_dict_select', '数据字典管理查看', '1', '1', 'sys_dict', '1');
INSERT INTO `yurayura_sys_permission` VALUES ('5', 'sys_dict_insert', '数据字典管理添加', '2', '1', 'sys_dict', '2');
INSERT INTO `yurayura_sys_permission` VALUES ('6', 'sys_dict_update', '数据字典管理修改', '2', '1', 'sys_dict', '3');
INSERT INTO `yurayura_sys_permission` VALUES ('7', 'sys_manager_select', '管理员管理查看', '1', '1', 'sys_manager', '1');
INSERT INTO `yurayura_sys_permission` VALUES ('8', 'sys_manager_insert', '管理员管理添加', '2', '1', 'sys_manager', '2');
INSERT INTO `yurayura_sys_permission` VALUES ('9', 'sys_manager_update', '管理员管理修改', '2', '1', 'sys_manager', '3');
INSERT INTO `yurayura_sys_permission` VALUES ('10', 'sys_permission_select', '权限管理查看', '1', '1', 'sys_permission', '1');
INSERT INTO `yurayura_sys_permission` VALUES ('11', 'sys_permission_insert', '权限管理添加', '2', '1', 'sys_permission', '2');
INSERT INTO `yurayura_sys_permission` VALUES ('12', 'sys_permission_update', '权限管理修改', '2', '1', 'sys_permission', '3');
INSERT INTO `yurayura_sys_permission` VALUES ('13', 'comic_info_select', '番剧信息管理查看', '1', '1', 'comic_info', '1');
INSERT INTO `yurayura_sys_permission` VALUES ('14', 'comic_info_insert', '番剧信息管理添加', '2', '1', 'comic_info', '2');
INSERT INTO `yurayura_sys_permission` VALUES ('15', 'comic_info_update', '番剧信息管理修改', '2', '1', 'comic_info', '3');
INSERT INTO `yurayura_sys_permission` VALUES ('16', 'comic_info_deleteBatch', '番剧信息管理批量删除', '2', '1', 'comic_info', '4');
INSERT INTO `yurayura_sys_permission` VALUES ('17', 'user_info_select', '用户资料管理查看', '1', '1', 'user_info', '1');
INSERT INTO `yurayura_sys_permission` VALUES ('18', 'user_info_update', '用户资料管理修改', '2', '1', 'user_info', '2');

```

