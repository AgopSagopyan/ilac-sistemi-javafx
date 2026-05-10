package com.ilacsistemi.util;

import java.util.Properties;
/*
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
*/

public class EpostaServisi {

    // E-posta gönderimini simüle eder veya çalıştırır
    public static boolean epostaGonder(String aliciEposta, String konu, String icerik) {
        System.out.println("E-Posta gönderimi simüle ediliyor...");
        System.out.println("Alıcı: " + aliciEposta);
        System.out.println("Konu: " + konu);
        System.out.println("İçerik: " + icerik);
        
        // E-posta kütüphanesini indirdiğinizde aşağıdaki kodu aktif edebilirsiniz:
        /*
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", Ayarlar.SMTP_HOST);
        props.put("mail.smtp.port", Ayarlar.SMTP_PORT);

        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Ayarlar.GONDEREN_EPOSTA, Ayarlar.EPOSTA_SIFRE);
            }
          });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Ayarlar.GONDEREN_EPOSTA));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(aliciEposta));
            message.setSubject(konu);
            message.setText(icerik);

            Transport.send(message);
            System.out.println("E-posta başarıyla gönderildi!");
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
        */
        return true; // Simülasyon için true dönüyoruz
    }
}
