package com.ssafy.happyhouse.global.common;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

@Component
public class GoogleMail {

        public void sendmail(String recipients, String title, String content) throws Exception {

            Properties prop = new Properties();

            prop.put("mail.smtp.user", "ajsjdlwj0123@gmail.com");
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.put("mail.smtp.port", "465");
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.debug", "true");
            prop.put("mail.smtp.socketFactory.port", "465");
            prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            prop.put("mail.smtp.socketFactory.fallback", "false");

            prop.put("mail.smtp.ssl.enable", "true");
            prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");


            Authenticator smtpAuth = new MySMTPAuthenticator();
            Session ses = Session.getInstance(prop, smtpAuth);

            // 메일을 전송할 때 상세한 상황을 콘솔에 출력한다.
            ses.setDebug(true);

            MimeMessage msg = new MimeMessage(ses);

            // 제목 설정
            msg.setSubject(title);

            // 보내는 사람의 메일주소
            String sender = "ajsjdlwj0123@gmail.com";
            Address fromAddr = new InternetAddress(sender);
            msg.setFrom(fromAddr);


            String[] recipientArr = recipients.split(",");

            for(String recipient : recipientArr) {
                // 받는 사람의 메일주소
                Address toAddr = new InternetAddress(recipient);
                msg.addRecipient(Message.RecipientType.TO, toAddr);

                ClassPathResource resource = new ClassPathResource("static/html/newsletter.html");
                Path htmlFilePath = resource.getFile().toPath();
                String htmlContent = new String(Files.readAllBytes(htmlFilePath), "UTF-8");

                MutableDataSet options = new MutableDataSet();
                Parser parser = Parser.builder(options).build();
                HtmlRenderer renderer = HtmlRenderer.builder(options).build();
                String userContentHtml = renderer.render(parser.parse(content));

                htmlContent = htmlContent.replace("{{title}}", title);
                htmlContent = htmlContent.replace("{{content}}", userContentHtml);

                // 메시지 본문의 내용과 형식, 캐릭터 셋 설정
                msg.setContent(htmlContent, "text/html;charset=UTF-8");

                // 메일 발송하기
                Transport.send(msg);
            }


        }//end of public void sendmail(String recipient, String certificationCode)----------------


}
