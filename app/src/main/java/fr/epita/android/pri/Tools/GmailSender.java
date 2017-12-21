package fr.epita.android.pri.Tools;

import java.security.Security;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/**
 * Created by sadekseridj on 25/11/2017.
 */


public class GmailSender extends javax.mail.Authenticator {

    private String mailhost = "smtp.gmail.com";
    private String user;
    private String password;
    private Session session;

    static {
        Security.addProvider(new JSSEProvider());
    }

    public GmailSender(String user, String password) {
        this.user = user;
        this.password = password;

        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.host", mailhost);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false");
        properties.setProperty("mail.smtp.quitwait", "false");

        session = Session.getDefaultInstance(properties, this);
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user, password);
    }

    public synchronized void sendMail(String subject, String body, String sender, String recipient) throws Exception {
        try {
            MimeMessage message = new MimeMessage(session);
            ByteArrayDataSource byteArrayDataSource = new ByteArrayDataSource(body.getBytes(), "text/plain");
            DataSource dataSource = DataSource.class.cast(byteArrayDataSource);
            DataHandler handler = new DataHandler(dataSource);
            message.setSender(new InternetAddress(sender));
            message.setSubject(subject);
            message.setDataHandler(handler);
            if (recipient.indexOf(',') > 0)
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            else
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


