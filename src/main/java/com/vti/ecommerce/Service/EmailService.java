package com.vti.ecommerce.Service;

import com.vti.ecommerce.Model.Order;
import com.vti.ecommerce.Model.User;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class EmailService {
    @Resource
    private JavaMailSender javaMailSender;
    public void sendMail(Order order, User user){
        try{
            MimeMessage message = javaMailSender.createMimeMessage();
            message.setFrom(new InternetAddress("anh.trannguyenduc@vti.com.vn","Hakuda Shop"));
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            String contextMail = "Thank you for your order. Click on the link to show your order detail: " + "http://localhost:8082/user/order/detail/" + order.getId()
                    +" \nTên: "+user.getName()
                    +"\nĐịa chỉ: "+user.getAddress()
                    +"\nThành tiền: "+order.getTotal_price() + "VNĐ"
                    +"\nTrạng thái: "+order.getStatus_shipping()
                    +"\nNgày mua: "+order.getCreated_date();
            helper.setTo(user.getEmail());
            helper.setSubject("Email confirm order by " + user.getName());
            helper.setText(contextMail);
            javaMailSender.send(message);
        }catch (MessagingException e){
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
