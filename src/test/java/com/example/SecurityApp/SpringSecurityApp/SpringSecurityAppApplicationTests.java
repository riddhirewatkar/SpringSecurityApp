package com.example.SecurityApp.SpringSecurityApp;

import com.example.SecurityApp.SpringSecurityApp.entity.UserEntity;
import com.example.SecurityApp.SpringSecurityApp.service.JWTService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringSecurityAppApplicationTests {

    @Autowired
    private JWTService jwtService;

	@Test
	void contextLoads() {
        UserEntity userEntity = new UserEntity(4L, "riddhi@gmail.com", "riddhi");

        String token = jwtService.generateToken(userEntity);
        System.out.println(token);

        Long id = jwtService.getUserIdFromToken(token);
        System.out.println(id);
	}

}
