import org.junit.Test;

public class StringTest {
    @Test
    public void test(){
        String a = "123-456-789";
        System.out.println(a.substring(0,a.lastIndexOf('-')));
        System.out.println(a.substring(a.lastIndexOf('-')+1));
    }
}
