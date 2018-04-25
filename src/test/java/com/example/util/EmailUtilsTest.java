package com.example.util;

import com.example.service.AssignmentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by 白 on 2018/4/24.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailUtilsTest {

    @Value("${spring.mail.username}")
    private String sender;


    @Autowired
    private JavaMailSender javaMailSender;

    @Test
    public void sendSimpleEmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        String userEmail = "1342746626@qq.com";
        String activityCode= "66663333";
        message.setFrom(sender);
        message.setTo(userEmail);

        message.setSubject("用户激活");
        message.setText("激活赏金猎人账号"+"http://127.0.0.1/bountyhunter?activityCode="+activityCode);

        javaMailSender.send(message);
    }

}