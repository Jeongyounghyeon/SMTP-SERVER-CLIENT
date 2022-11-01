package netWork.SMTP;

import java.util.Date;

public class SMTP {
    UserMail userMail;

    public SMTP(UserMail userMail) {
        this.userMail = userMail;
    }

    public String helo() {
        return "EHLO " + this.userMail.SMTPserver;
    }

    public String authLogin() {
        return "AUTH PLAIN " + this.userMail.auth;
    }

    public String mailFrom() {
        if(this.userMail.SMTPserver.equals("smtp.naver.com")){
            return "MAIL FROM: " + "<" + this.userMail.ID + ">";

        }else if(this.userMail.SMTPserver.equals("smtp.gmail.com")){
            return "MAIL FROM: " + "<" + this.userMail.userEmail + ">";

        }
        return "";
    }

    public String rcptTO() {
        return "RCPT TO: " + "<" + this.userMail.TO + ">";
    }

    public String data() {
        return "data";
    }

    public String header() {
        String date = "DATE: " + new Date() + "\r\n";
        String to = "TO: " + this.userMail.TO + "\r\n";
        String from = "FROM: " + this.userMail.userEmail;
        return date + to + from;
    }

    public String sbject() {
        return "subject: " + this.userMail.subject;
    }

    public String messege() {
        return this.userMail.content + "\r\n" + ".";
    }

    public String quit() {
        return "QUIT";
    }

    public String sendProtocol() {
        String allProtocol = helo() + "\r\n"
                + authLogin() + "\r\n"
                + mailFrom() + "\r\n"
                + rcptTO() + "\r\n"
                + data() + "\r\n"
                + header() + "\r\n"
                + sbject() + "\r\n"
                + messege() + "\r\n"
                + quit();

        return allProtocol;
    }
}
