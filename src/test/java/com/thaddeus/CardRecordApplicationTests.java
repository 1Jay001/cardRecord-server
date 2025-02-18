package com.thaddeus;

import com.thaddeus.pojo.entity.User;
import com.thaddeus.server.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CardRecordApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {

        System.out.println("1111");
    }

    @Test
    public void getList() {
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

}
