package org.cny.yurayura;

import org.cny.yurayura.domain.TestListGrpBo;
import org.junit.Test;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 描述
 *
 * @author CNY
 * @since 2021-06-02
 */
public class SomeTest {

    @Test
    public void dayDiffMinuteTest() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date1 = format.parse("2021-02-03 11:14:22");
            Date date2 = format.parse("2021-02-05 11:17:59");

            // 两个日期相差毫秒
            long diffMillisecond = date2.getTime() - date1.getTime();
            // 两个日期相差分钟
            float diffMinute = (float) TimeUnit.MILLISECONDS.toSeconds(diffMillisecond) / 60;
            System.out.println(diffMinute);
            // 四舍五入到小数点后两位
            DecimalFormat df = new DecimalFormat("0.00");
            float res = Float.parseFloat(df.format(diffMinute));
            System.out.println(res);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getNextMonth() {
        String s = "202111";
        String lastMonth = this.getNextMonth(s);
        System.out.println(lastMonth);
    }

    public String getNextMonth(String planMonth) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        try {
            Date date = format.parse(planMonth);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            cal.add(Calendar.MONTH, 1);
            date = cal.getTime();
            return format.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Test
    public void testSplit() {
        String s = "hh";
        String[] split = s.split(",");
        System.out.println(Arrays.toString(split));
    }

    @Test
    public void testListToStr() {
        List<String> objects = new ArrayList<>();
        objects.add("123");
        objects.add("656");
        String s = objects.toString();
        System.out.println(s.substring(1).substring(0, s.length() - 2));
    }

    @Test
    public void testListGrp() {
        TestListGrpBo testListGrpBo1 = new TestListGrpBo();
        testListGrpBo1.setMenuPermId(1);
        testListGrpBo1.setButtonPermId(2);
        TestListGrpBo testListGrpBo2 = new TestListGrpBo();
        testListGrpBo2.setMenuPermId(1);
        testListGrpBo2.setButtonPermId(3);
        TestListGrpBo testListGrpBo3 = new TestListGrpBo();
        testListGrpBo3.setMenuPermId(1);
        testListGrpBo3.setButtonPermId(4);
        TestListGrpBo testListGrpBo4 = new TestListGrpBo();
        testListGrpBo4.setMenuPermId(2);
        testListGrpBo4.setButtonPermId(1);
        TestListGrpBo testListGrpBo5 = new TestListGrpBo();
        testListGrpBo5.setMenuPermId(2);
        testListGrpBo5.setButtonPermId(2);
        List<TestListGrpBo> list = new ArrayList<>();
        list.add(testListGrpBo1);
        list.add(testListGrpBo2);
        list.add(testListGrpBo3);
        list.add(testListGrpBo4);
        list.add(testListGrpBo5);
        Map<Integer, List<TestListGrpBo>> collect = list.stream().collect(Collectors.groupingBy(TestListGrpBo::getMenuPermId));
        System.out.println(collect);
    }

}
