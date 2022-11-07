package netWork.SMTP;

import java.beans.Encoder;
import java.util.Base64;

public class UserMail {
    public String SMTPserver;
    public String ID;
    //ID값만 받는다.
    public String userEmail;
    public String PW;
    public String TO;
    public String subject;
    public String content;
    public String auth;

    public UserMail(String SMTPserver, String ID, String PW, String TO, String subject, String content) {
        this.SMTPserver = SMTPserver;
        if (SMTPserver.equals("smtp.naver.com")) {
            this.userEmail = ID + "@naver.com";
        } else if (SMTPserver.equals("smtp.gmail.com")) {
            this.userEmail = ID + "@gmail.com";
        } else {
            this.userEmail = null;
        }
        this.ID = ID;
        this.PW = PW;
        this.TO = TO;
        this.subject = subject;
        this.content = content;
        if (SMTPserver.equals("smtp.naver.com")) {
            this.auth = base64EncodeUser(ID, PW);
        } else if (SMTPserver.equals("smtp.gmail.com")) {
            this.auth = base64EncodeUser(userEmail, PW);
        }
        if (PW.equals("")) {
            this.auth = auth;
        }

    }

    public UserMail(String SMTPserver, String ID, String TO, String subject, String content) {
        this.SMTPserver = SMTPserver;
        if (SMTPserver.equals("smtp.naver.com")) {
            this.userEmail = ID + "@naver.com";
        } else if (SMTPserver.equals("smtp.gmail.com")) {
            this.userEmail = ID + "@gmail.com";
        } else {
            this.userEmail = null;
        }
        this.ID = ID;
        this.TO = TO;
        this.subject = subject;
        this.content = content;

    }
    public UserMail(String SMTPserver, String ID, String PW, String TO, String subject, String content,String auth) {
        this.SMTPserver = SMTPserver;
        if (SMTPserver.equals("smtp.naver.com")) {
            this.userEmail = ID + "@naver.com";
        } else if (SMTPserver.equals("smtp.gmail.com")) {
            this.userEmail = ID + "@gmail.com";
        } else {
            this.userEmail = null;
        }
        this.ID = ID;
        this.PW = PW;
        this.TO = TO;
        this.subject = subject;
        this.content = content;
        if (PW.equals("")) {
            this.auth = auth;
        }

    }

    static public String base64EncodeUser(String ID, String PW) {
        Base64.Encoder encoder = Base64.getEncoder();
//        "\000id\000pw"
        String authText = "\000" + ID + "\000" + PW;
        byte[] authBytes = encoder.encode(authText.getBytes());

        return new String(authBytes);
    }

    public void setAuth(String auth){
        this.auth= auth;
    }

}
