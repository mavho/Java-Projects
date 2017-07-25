package website_bot;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
public class email {
	protected email(){}
	final static String username = "websitetrckmav@gmail.com";
	final static String password = "cloudharmonics123";
	
	public static void sendEmail(String company, String siteStr, String changedStr){
		 Properties props = new Properties();
		 props.put("mail.smtp.auth", true);
		 props.put("mail.smtp.starttls.enable", true);
		 props.put("mail.smtp.host", "smtp.gmail.com");
		 props.put("mail.smtp.port", "587");
		 props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		
		Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				  });

				try {

					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress("websitetrckmav@gmail.com"));
					message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse("homaverick@gmail.com"));
					message.setSubject("Website Change");
					message.setText("Dear Recipient,"
						+ "\n\n The " + company + " website has been changed. The changes are listed below,\n"+ changedStr+
						"\n The link to the product page: " + siteStr);
					Transport.send(message);
					System.out.println("Done");

				} catch (MessagingException e) {
					throw new RuntimeException(e);
				}
	   }
}
