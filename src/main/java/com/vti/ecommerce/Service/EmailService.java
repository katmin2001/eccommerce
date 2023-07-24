package com.vti.ecommerce.Service;

import com.vti.ecommerce.Model.DTO.OrderDTO;
import com.vti.ecommerce.Model.Order;
import com.vti.ecommerce.Model.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private JavaMailSender javaMailSender;
    public void sendMail(Order order, User user){
        try{
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            String contextMail = "Thank you for order. Click on the link to show your order " + "http://localhost:8082/user/order/detail/" + order.getId();
            helper.setTo(user.getEmail());
            helper.setSubject("Email confirm order");
            helper.setText(contextMail);
            javaMailSender.send(message);
        }catch (MessagingException e){
            e.printStackTrace();
        }
    }
}
