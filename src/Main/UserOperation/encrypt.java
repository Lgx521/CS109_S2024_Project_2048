package Main.UserOperation;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/*
使用Base64算法进行密码加密
 */
public class encrypt {
    public String encryptBASE64(String InitialText) throws Exception {
        final Base64.Encoder encoder = Base64.getEncoder();
        final String text = InitialText;
        final byte[] textByte = text.getBytes(StandardCharsets.UTF_8);
        final String encodedText = encoder.encodeToString(textByte);
        return encodedText;
    }

    public String decryptBASE64(String encodedText) throws Exception {
        final Base64.Decoder decoder = Base64.getDecoder();
        String decodedText = new String(decoder.decode(encodedText), StandardCharsets.UTF_8);
        return decodedText;
    }

}
