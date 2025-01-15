import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import org.junit.Test;
import org.springframework.util.DigestUtils;

/**
 * 描述
 *
 * @author ELiaNaCc
 * @since 2025-01-13
 */
public class PassTest {

    @Test
    public void testPass() {
        String pass = "admin";
        String key = "1qazxcvbnmlp-098";
//        String salt = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
        String salt = "c02efa9dfb054bdb";
        System.out.println(salt);
        AES aes = new AES(Mode.CBC, Padding.ZeroPadding, key.getBytes(), salt.getBytes());
        System.out.println(aes.encryptBase64(pass));
        String encryptPass = DigestUtils.md5DigestAsHex(aes.encryptBase64(pass).getBytes());
        System.out.println(encryptPass);

        String pass2 = "kuttKwide3gI9ig0ktQDiA==";
        String decryptStr = aes.decryptStr(Base64.decode(pass2));
        System.out.println(decryptStr);
    }

}
