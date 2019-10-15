import Service.RedisSessionService;
import org.junit.Test;

public class RedisSessionServiceTest {
    @Test
    public void test(){
        String ID = "f772431d-ff66-4131-ac5b-9ae7ef2e967e";
        String key = "user";
        String username = RedisSessionService.getAttr(ID,key);
        if(username==null){
            System.out.println("null");
        }
    }
}
