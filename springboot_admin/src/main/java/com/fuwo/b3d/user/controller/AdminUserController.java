package com.fuwo.b3d.user.controller;

import com.fuwo.b3d.user.model.UserInfo;
import com.fuwo.b3d.user.service.UserService;
import com.fuwo.b3d.user.utils.password.DjangoPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.security.Principal;

import static com.fuwo.b3d.GlobalConstant.BASE_MAPPING;

@Controller
@RequestMapping(BASE_MAPPING + "/user")
public class AdminUserController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/")
    public String display(Model model, @NotNull Principal principal) {
        UserInfo userInfo = this.userService.get(principal.getName());
        model.addAttribute("userInfo", userInfo);
        return "user/index";
    }

    @PostMapping(value = "/")
    public String modifyUserInfo(Model model, @Valid UserInfo userInfo, String oldPassword, String newPassword,
                                 String confirmPassword, @NotNull Principal principal) {

        String errorMessages = null;
        //判断密码是否为空
        if (oldPassword == null || oldPassword.length() == 0) {
            errorMessages = "原密码不能为空";
        }
        //判断新密码是否为空
        if (newPassword == null || newPassword.length() == 0) {
            errorMessages = "新密码不能为空";
        }

        //判断确认密码是否为空
        if (confirmPassword == null || confirmPassword.length() == 0) {
            errorMessages = "确认密码不能为空";
        }

        //判断两次密码是否一致
        if (!newPassword.equals(confirmPassword)) {
            errorMessages = "两次输入的密码不一致";
        }

        PasswordEncoder pwdEncoder = new DjangoPasswordEncoder();

        UserInfo entity = this.userService.get(principal.getName());
        if (!pwdEncoder.matches(oldPassword, entity.getPassword())) {
            errorMessages = "原密码输入不正确";
        }

        if (errorMessages != null) {
            model.addAttribute("errorMessages", errorMessages);
            return "user/index";
        }

        userInfo.setUsername(principal.getName());
        userInfo.setPassword(pwdEncoder.encode(newPassword));

        userInfo = this.userService.update(userInfo);
        model.addAttribute("userInfo", userInfo);

        return "redirect:" + BASE_MAPPING + "/user/";
    }

}
