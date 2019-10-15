import elon.mapper.UserMapper;
import elon.pojo.User;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/resources")
@ContextConfiguration(locations = {"classpath:spring/spring-*.xml"})

public class mapperTest {

    @Autowired
    UserMapper userMapper;
    @Autowired
    SqlSession sqlSession;
    @Test
    public void testMapper(){
        System.out.println(userMapper);
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.selectByPrimaryKey(5);
        System.out.println(user.getName()+" "+user.getPassword());
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        for(int i=0;i<1000;i++) {
            String uid = UUID.randomUUID().toString().substring(0,5)+i;
            mapper.insertSelective(new User(null,uid,uid+"@163"));
        }
    }
}
