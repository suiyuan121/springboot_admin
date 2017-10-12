package spring.oauth2.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import spring.oauth2.auth.model.User;
import spring.oauth2.auth.service.UserService;

/**
 * Created by Jerry on 2017/5/26.
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users/{id}")
    public User user(@PathVariable("id") Long id) {
        return this.userService.findById(id);
    }

}
