package fr.insalyon.b05.predictifa.util;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author DASI Team
 */
public class Message {
    
    private final static PrintStream OUT = System.out;
    private final static SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd~HH:mm:ss");
    private final static SimpleDateFormat HORODATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy à HH:mm:ss");
    
    private static void debut() {
        Date maintenant = new Date();
        OUT.println();
        OUT.println();
        OUT.println("---<([ MESSAGE @ " + TIMESTAMP_FORMAT.format(maintenant) + " ])>---");
        OUT.println();
    }
    
    private static void fin() {
        OUT.println();
        OUT.println("---<([ FIN DU MESSAGE ])>---");
        OUT.println();
        OUT.println();
    }
    
    public static void envoyerMail(String mailExpediteur, String mailDestinataire, String objet, String corps) {
        
        Date maintenant = new Date();
        Message.debut();
        OUT.println("~~~ E-mail envoyé le " + HORODATE_FORMAT.format(maintenant) + " ~~~");
        OUT.println("De : " + mailExpediteur);
        OUT.println("À  : " + mailDestinataire);
        OUT.println("Obj: " + objet);
        OUT.println();
        OUT.println(corps);
        Message.fin();
    }

    public static void envoyerNotification(String telephoneDestinataire, String message) {
        
        Date maintenant = new Date();
        Message.debut();
        OUT.println("~~~ Notification envoyée le " + HORODATE_FORMAT.format(maintenant) + " ~~~");
        OUT.println("À  : " + telephoneDestinataire);
        OUT.println();
        OUT.println(message);
        Message.fin();
    }
    
}
