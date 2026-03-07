package com.main.Email;

import com.main.Entity.User;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendWelcomeEmail(User user) {

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo(user.getEmail());
            helper.setSubject("Welcome to HMS System");
            helper.setFrom("mouryaankeet@gmail.com.");

            String htmlContent =
                    "<h2>Welcome to HMS System</h2>" +
                            "<p>Hi " + user.getFirstName() + ",</p>" +
                            "<p>You can now:</p>" +
                            "<ul>" +
                            "<li>Book appointments</li>" +
                            "<li>View medical records</li>" +
                            "<li>Manage your profile</li>" +
                            "<li>Access billing history</li>" +
                            "</ul>" +
                            "<p>We are happy to have you onboard.</p>" +
                            "<br><p>Regards,<br>HMS Team</p>";

            helper.setText(htmlContent, true);

            mailSender.send(mimeMessage);

        } catch (Exception e) {
            e.printStackTrace();
//            logger.error("Failed to send email", e);
        }
    }
}
