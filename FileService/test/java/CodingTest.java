import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class CodingTest {
    @Test
    public void test() throws UnsupportedEncodingException {
        String a = "&#20116;&#31508;&#23383;&#26681;";
        byte[] c = a.getBytes();
        for(byte i:c){
            System.out.println(i);
        }
        String b = new String("gbk");
        System.out.println(System.getProperty("file.encoding")+" "+Charset.defaultCharset());
        System.out.println(b);
    }
}
