package org.cny.yurayura;

import java.io.*;

/**
 * InputSteam转String Test
 *
 * @author CNY
 * @since 2021-06-02
 */
public class InputStream2String {

    public static void main(String[] args) {

        try {
            InputStream inputStream = new FileInputStream("G:/Download/screenshot/test.txt");

            char[] buffer = new char[1024];    //根据需要的数组大小进行自定义
            StringBuilder out = new StringBuilder();
            Reader in = new InputStreamReader(inputStream, "gb2312");
            for (int numRead; (numRead = in.read(buffer, 0, buffer.length)) > 0; ) {
                out.append(buffer, 0, numRead);
            }
            String myString = out.toString();

            System.out.println("myString = " + myString);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
