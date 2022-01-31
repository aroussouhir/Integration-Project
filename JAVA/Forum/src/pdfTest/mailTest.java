/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdfTest;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author arous souhir
 */
public class mailTest {
    
    private String username = "souhir.arous@esprit.tn";
private String password = "182JFT2439";

public void envoyer(String message1, String emailUser) {
// Etape 1 : Création de la session
Properties props = new Properties();
props.put("mail.smtp.auth", "true");
props.put("mail.smtp.starttls.enable","true");
props.put("mail.smtp.host","smtp.gmail.com");
props.put("mail.smtp.port","587");
Session session = Session.getInstance(props,
new javax.mail.Authenticator() {
protected PasswordAuthentication getPasswordAuthentication() {
return new PasswordAuthentication(username, password);
}
});
try {
// Etape 2 : Création de l'objet Message
Message message = new MimeMessage(session);
message.setFrom(new InternetAddress("souhir.arous@esprit.tn"));
message.setRecipients(Message.RecipientType.TO,
InternetAddress.parse(emailUser)); //dest
message.setSubject("OuiBike Message");
message.setText(message1);
// Etape 3 : Envoyer le message
Transport.send(message);
System.out.println("Message_envoye");
} catch (MessagingException e) {
throw new RuntimeException(e);
} }
//Etape 4 : Tester la méthode
public static void main(String[] args) {
/*mailTest test = new mailTest();
test.envoyer("salut");*/
} 
}
