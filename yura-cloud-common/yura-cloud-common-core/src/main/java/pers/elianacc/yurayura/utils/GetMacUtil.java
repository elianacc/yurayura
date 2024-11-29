package pers.elianacc.yurayura.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * 获取mac地址 util
 *
 * @author ELiaNaCc
 * @since 2023-02-23
 */
public class GetMacUtil {

    public static String getMac() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);

            byte[] mac = network.getHardwareAddress();
            if (mac == null) {
                return "123456";
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            return sb.toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "123456";
        } catch (SocketException e) {

            e.printStackTrace();
            return "123456";
        }
    }

}
