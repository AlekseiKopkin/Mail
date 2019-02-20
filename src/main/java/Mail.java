import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;
import javax.mail.*;
import javax.mail.internet.*;

public class Mail {

    public static void main(String args[ ]) throws IOException {
        String mail="@mail.ru";
        String gmail="@gmail.com";
        Scanner scanner = new Scanner(System.in);
        System.out.println("Почтой сервис");
        if (mail.equals(scanner.nextLine())) {
            new Mail().MailMessager("src/main/resources/MailSMTP.properties");
        }
        if (gmail.equals(scanner.nextLine())) {
            new Mail().MailMessager("src/main/resources/GmailSMTP.properties");
        }
    }

    private void MailMessager(String file) throws IOException{
        Scanner scanner=new Scanner(System.in);
        System.out.println("Почта отправителя");
        final String username = scanner.nextLine();
        System.out.println("Пароль от почты");
        final String password = scanner.nextLine();
        System.out.println("Почта получателя");
        final String yourname = scanner.nextLine();

        Properties props = new Properties();
        FileInputStream input = new FileInputStream(file);

        // load a properties file
        props.load(input);

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            // от кого
            message.setFrom(new InternetAddress(username));
            //кому
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(yourname));
            // тема
            System.out.println("Тема сообщения");
            message.setSubject(scanner.nextLine());
            // текст
            System.out.println("Сообщение");
            message.setText(scanner.nextLine());

            Transport transport= session.getTransport("smtp");
            transport.connect(null,password);
            transport.sendMessage(message,message.getAllRecipients());
            transport.close();
            System.out.println("Сообщение передано!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
