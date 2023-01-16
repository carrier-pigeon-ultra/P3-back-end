package com.revature.controllers;

import com.revature.Utility.GetSiteUrl;
import com.revature.dtos.ForgotPasswordRequest;
import com.revature.exceptions.UserNotFoundException;
import com.revature.services.UserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000",  "http://codepipeline-us-west-2-791209503483.s3-website-us-west-2.amazonaws.com",
        "http://carrier-pigeon-client-not-pipline.s3-website-us-west-2.amazonaws.com"}, allowCredentials = "true")
public class ForgotPasswordController {

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender mailSender;

    GetSiteUrl getSiteUrl;
//    @GetMapping("/reset-password")
//    public String showForgotPasswordForm(){
//        return "forgot_password_form";
//    }

    @PostMapping("/reset-password")
    public void processForgotPasswordForm(@RequestBody ForgotPasswordRequest forgotPasswordRequest) throws UserNotFoundException{

        String to = forgotPasswordRequest.getEmail();
        String token = RandomString.make(45);
        String from = "carrierpigeonultra@gmail.com";
        String host = "smtp.gmail.com";
//        String URL = "localhost:4200";
        String URL = "http://carrier-pigeon-client-not-pipline.s3-website-us-west-2.amazonaws.com";

        //Get System Properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        //generate link
        String resetPasswordLink =  URL +  "/reset-password-form/" + token;
//        System.out.println(resetPasswordLink);

        //Get Session Object and Sign into Account
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("carrierpigeonultra@gmail.com", "zqebbljwcycuomwt");

            }

        });

        // Debug any SMTP issues
        session.setDebug(true);

        userService.updateResetPassword(token,to);

        System.out.println("Email: " + to);
        System.out.println("Token: " + token);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            //Setting to and From
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Subject Header
            message.setSubject("Reset Password Link");

            // Reset Password Message with Reset Link
            message.setContent("<p>Hello,</p>"
                    + "<p>You have requested to reset your password</p>"
                    +"<p>If this was not you, then you may ignore this email.</p>"
                    +"<p>Click the link below if you would like to change your password</p>"
                    +"<p><a href =\"" + resetPasswordLink + "\">Change my password</a></p>", "text/html; charset=utf-8");

            System.out.println("sending...");

            // Send message
//            Transport.send(message);
            System.out.println(message.getContent());
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
