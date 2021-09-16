package org.cny.yurayura;

import org.cny.yurayura.domain.StramTestUser;

import java.util.*;
import java.util.stream.Collectors;

/**
 * StreamApi测试类
 *
 * @author CNY
 * @since 2020-11-13
 */
public class StreamApiDemo {

    private List<StramTestUser> initData() {
        List<StramTestUser> userList = new ArrayList<>();
        String stringUserNameFormat = "userName: %s";
        String userTypeFormat = "%s";
        String groupTypeFormat = "groupType: %s";
        String userCodeFormat = "userCode: %s";
        for (int i = 0; i < 10; i++) {
            StramTestUser u = new StramTestUser((long) i,
                    String.format(stringUserNameFormat, i),
                    String.format(userCodeFormat, i));
            u.setGroupType(String.format(groupTypeFormat, i / 2 == 0 ? "even" : "odd"));
            u.setUserType(String.format(userTypeFormat, i / 5 == 0 ? "0" : "1"));
            userList.add(u);
        }
        return userList;
    }

    public static void main(String... args) {
        StreamApiDemo streamApiDemo = new StreamApiDemo();
        List<StramTestUser> userList = streamApiDemo.initData();
        userList.forEach(System.out::println);
        // 创建流
        streamApiDemo.createStream();
        // foreach
        streamApiDemo.forEachDemo();
        // Map操作
        streamApiDemo.mapDemo(userList);
        // filter操作
        streamApiDemo.filterDemo(userList);
        // limit操作
        streamApiDemo.limitDemo(userList);
        // sort操作
        streamApiDemo.sortDemo(userList);
        // collectors的toMap
        streamApiDemo.collectorsToMapDemo(userList);
        // collectors的groupingBy
        streamApiDemo.collectorsGroupByDemo(userList);
    }

    /**
     * 根据对象的属性转换为map 如果key冲突根据k1值
     * toMap参数:
     * map中的key
     * map中的value
     * key冲突时返回的key
     *
     * @param list 测试集合
     */
    private void collectorsToMapDemo(List<StramTestUser> list) {
        System.out.println(list);
        Map<String, StramTestUser> userTypeMap = list.stream().collect(Collectors.toMap(StramTestUser::getUserCode, a -> a, (k1, k2) -> k1));
//        for (String key : userTypeMap.keySet()) {
//            System.out.println(userTypeMap.get(key).getUserName());
//        }
        System.out.println(userTypeMap);
        userTypeMap.forEach((key, value) -> System.out.println(key + "---" + value));
    }

    /**
     * 根据指定的属性给集合对象分组并返回map
     *
     * @param list 测试集合
     */
    private void collectorsGroupByDemo(List<StramTestUser> list) {
        Map<String, List<StramTestUser>> userGroupMap = list.stream().collect(Collectors.groupingBy(StramTestUser::getGroupType));
//        for (String key : userGroupMap.keySet()) {
//            List<User> userList = userGroupMap.get(key);
//            userList.forEach(u -> System.out.println(u.getUserName()));
//        }
        userGroupMap.forEach((key, value) -> System.out.println(key + "---" + value));
    }

    /**
     * 根据条件进行排序
     * sorted的默认参数可以参考Comparator接口
     * reverseOrder自然逆序
     *
     * @param list 测试集合
     */
    private void sortDemo(List<StramTestUser> list) {
        Random random = new Random();
        list.forEach(u -> {
            u.setUserTemp(String.valueOf(random.nextInt()));
            System.out.println(u);
        });
        List<StramTestUser> userList = list.stream().sorted(Comparator.comparing(StramTestUser::getUserTemp)).collect(Collectors.toList());
        userList.forEach(u -> System.out.println(u.getUserName()));
    }


    /**
     * limit 方法用于获取指定数量的流
     *
     * @param list 测试集合
     */
    private void limitDemo(List<StramTestUser> list) {
        List<StramTestUser> userList = list.stream().limit(5).collect(Collectors.toList());
        userList.forEach(u -> System.out.println(u.getUserName()));

        list.stream().limit(5).forEach(u -> System.out.println(u.getUserName()));
    }


    /**
     * filter 方法用于通过设置的条件过滤出元素
     * 获取userType为1的数据
     * 获取userType为1的数量
     *
     * @param list 测试集合
     */
    private void filterDemo(List<StramTestUser> list) {
        List<StramTestUser> userList = list.stream().filter(u -> u.getUserType().equals("1")).distinct().collect(Collectors.toList());
        userList.forEach(u -> System.out.println(u.getUserName()));
        Long userListNum = list.stream().filter(u -> u.getUserType().equals("1")).count();
        System.out.println(userListNum);

    }

    /**
     * stream() − 为集合创建串行流。
     * parallelStream() − 为集合创建并行流
     * 使用filter过滤
     */
    private void createStream() {
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
        List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
        filtered.forEach(System.out::println);
    }

    private void forEachDemo() {
        Random random = new Random();
        random.ints().limit(10).forEach(System.out::println);
    }

    /**
     * map 方法用于映射每个元素到对应的结果
     * 获取对象中属性并返回一个list
     *
     * @param list 测试集合
     */
    private void mapDemo(List<StramTestUser> list) {
        List<String> userNameList = list.stream().map(StramTestUser::getUserName).distinct().collect(Collectors.toList());
        userNameList.forEach(System.out::println);
    }
}
