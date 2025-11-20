/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package scheduler;

import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;

public class MailService {

    private static final String USER = "tanvmhe186791@fpt.edu.vn";
    private static final String PASS = "ozuj pvoj paux nzpv";

    public static void sendExpiredEmail(String to, String eventTitle) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(USER, PASS);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USER));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Sự kiện của bạn đã hết hạn");
            message.setText("Xin chào,\n\nSự kiện \"" + eventTitle + "\" đã hết hạn.\nHãy kiểm tra lại trên hệ thống.\n\nTrân trọng.");

            Transport.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
