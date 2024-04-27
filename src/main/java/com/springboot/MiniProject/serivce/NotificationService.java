package com.springboot.MiniProject.serivce;

import com.springboot.MiniProject.dto.DemandeDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class NotificationService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void informAdminNewDemandeCreated(DemandeDTO demandeDTO) {
        // Construire le contenu de l'e-mail
        String emailContent = "Une nouvelle demande a été créée.\n\n" +
                "ID de la demande : " + demandeDTO.getId() + "\n" +
                "Sujet : " + demandeDTO.getSujet() + "\n" +
                "Description : " + demandeDTO.getDescription() + "\n" +
                "Date de création : " + demandeDTO.getDateCreation() + "\n" +
                "Statut : " + demandeDTO.getStatut() + "\n"+
                "User : " + demandeDTO.getUserEmail() + "\n";

        // Envoyer l'e-mail à l'administrateur
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo("mahdawihadir9@gmail.com");
            helper.setSubject("Nouvelle Demande Créée");
            helper.setText(emailContent);
            javaMailSender.send(message);
            System.out.println("E-mail de notification envoyé à l'administrateur avec succès !");
        } catch (MailException e) {
            System.out.println("Erreur lors de l'envoi de l'e-mail de notification à l'administrateur : " + e.getMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
