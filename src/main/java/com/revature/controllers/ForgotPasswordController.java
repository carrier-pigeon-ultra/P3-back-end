package com.revature.controllers;

import com.revature.Utility.GetSiteUrl;
import com.revature.exceptions.UserNotFoundException;
import com.revature.services.UserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
public class ForgotPasswordController {

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/reset-password")
    public String showForgotPasswordForm(Model model){
//        model.addAttribute("pageTitle", "Forgot Password");
        return "forgot_password_form";
    }

    @PostMapping("/reset-password")
    public String processForgotPasswordForm(HttpServletRequest request) throws UserNotFoundException, MessagingException, UnsupportedEncodingException {
        String email = request.getParameter("email");
        String token = RandomString.make(45);

        //generate link
        String resetPasswordLink = GetSiteUrl.getSiteURL(request) + "/reset_password?token=" + token;
        System.out.println(resetPasswordLink);


        //send email
        sendEmail(email, resetPasswordLink);



        userService.updateResetPassword(token,email);

//        System.out.println("Email: " + email);
//        System.out.println("Token: " + token);

        return "forgot_password_form";
    }
    private void sendEmail(String email, String resetPasswordLink) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("carrierpigeonsupport@gmail.com", "Carrier Pigeon Support");
        helper.setTo(email);

        String subject = "Reset Password Link";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password</p>"
                +"<p>If this was not you, then you may ignore this email.</p>"
                +"<p>Click the link below if you would like to change your password</p>"
                +"<p><a href =\"" + resetPasswordLink + "\">Change my password</a></p>";
        helper.setSubject(subject);
        helper.setText(content,true);

        mailSender.send(message);
    }
}
