package elon.service;

import elon.mapper.UserMapper;
import elon.pojo.User;
import elon.pojo.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;
    /*
    保存用户
     */
    public void saveUser(User user) {
        userMapper.insertSelective(user);
    }

    /*
    检验用户名是否存在
     */
    public boolean checkUser(String name) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andNameEqualTo(name);
        long count = userMapper.countByExample(userExample);
        return count == 0;
    }

    /*
    检验用户名和密码是否匹配
     */
    public boolean validateUser(User user){
        boolean validation = false;
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andNameEqualTo(user.getName());
        List<User> userList= userMapper.selectByExample(userExample);
        for(User u:userList){
            if (u.getPassword().equals(user.getPassword()))
                validation = true;
        }
        return validation;
    }
}
