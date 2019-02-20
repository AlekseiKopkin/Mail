import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

public class Messeger {
    public Messeger() throws IOException {
        String mail="@mail.ru";
        String gmail="@gmail.com";
        Scanner scanner = new Scanner(System.in);
        System.out.println("Почтой сервис");
        if (mail.equals(scanner.nextLine())) {
            MailMessager("src/main/resources/MailSMTP.properties");
        }
        if (gmail.equals(scanner.nextLine())) {
            MailMessager("src/main/resources/GmailSMTP.properties");
        }
    }
    private void MailMessager(String file) throws IOException {
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

            Multipart multipart = new MimeMultipart();

            System.out.println("Будет сообщение?");
            if(scanner.nextLine().equals("да")) {
                MimeBodyPart textBodyPart = new MimeBodyPart();
                // текст
                System.out.println("Сообщение");
                textBodyPart.setText(scanner.nextLine());

                multipart.addBodyPart(textBodyPart);  // add the text part
            }

            System.out.println("Будет вложение?");
            if(scanner.nextLine().equals("да")) {
                MimeBodyPart attachmentBodyPart = new MimeBodyPart();

                System.out.println("Введите адрес вложения");
                String attachementPath =scanner.nextLine();
                DataSource source = new FileDataSource(attachementPath); // ex : "C:\\test.pdf"

                System.out.println("Введите название файла");
                String fileName =scanner.nextLine();
                attachmentBodyPart.setDataHandler(new DataHandler(source));
                attachmentBodyPart.setFileName(fileName); // ex : "test.pdf"

                multipart.addBodyPart(attachmentBodyPart); // add the attachement part
            }

            message.setContent(multipart);

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
