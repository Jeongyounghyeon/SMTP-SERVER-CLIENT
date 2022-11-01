package netWork.SMTP;

import javax.net.ssl.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.Date;
import java.util.Timer;


public class SSLmail {
    //ssl post
    private static int port = 465;
    private static char[] password = "network123".toCharArray();

    UserMail userMail = null;
    SMTP protocol = null;

    public SSLmail(UserMail userMail) {
        this.userMail = userMail;
        this.protocol = new SMTP(userMail);
    }

    public SSLSocket connectSMTPserver(String smtpServer) throws Exception {
        FileInputStream fin = new FileInputStream("prgrms_keystore.p12");
        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(fin, password);

        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks, password);

        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(ks);

        SSLContext ctx = SSLContext.getInstance("SSL");
        ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

        SSLSocketFactory sf = ctx.getSocketFactory();
        SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();

//        SSLSocket socket = (SSLSocket)sf.createSocket("smtp.gmail.com", port);
        SSLSocket socket = (SSLSocket) sslsocketfactory.createSocket(smtpServer, port);

        System.out.println(smtpServer + "SMTP 서버에 연결되었습니다");


        return socket;
    }

    //    public static String makeMessege() {
//
//    }
//

    public  boolean connectionOK(BufferedReader br) throws Exception {
        String line = br.readLine();
        System.out.println(line);
        if (!line.startsWith("220")) {
            throw new Exception("SMTP서버가 아니거나, 연결에 실패하였습니다");
        }
        return true;
    }

    public boolean heloServer(BufferedReader br, PrintWriter pw) throws Exception {
        System.out.println("HELO 명령을 전송합니다");
        pw.println(this.protocol.helo());
        String line;
        while ((line = br.readLine()) != null) {
            if (line.isEmpty()) break;
            System.out.println(line);
            if (!br.ready()) break;
            if (line.startsWith("451")) {
                throw new Exception("TIME OUT.");
            }
            if (!line.startsWith("250")) {
                throw new Exception("EHLO 요청을 실패하였습니다.");
            }
        }
        return true;
    }

    public boolean authLogin(BufferedReader br, PrintWriter pw) throws Exception {
        System.out.println("AUTH login 명령을 전송합니다");
        System.out.println("null뜨지마 "+this.protocol.authLogin());


        pw.println(this.protocol.authLogin());
        String line;
        while ((line = br.readLine()) != null) {
            if (line.isEmpty()) break;
            System.out.println(line);
            if (!br.ready()) break;
            if (!line.startsWith("234")) {
                throw new Exception("로그인에 실패하였습니다.");
            }

        }
        return true;
    }

    public boolean fromAndRcptTo(BufferedReader br, PrintWriter pw) throws Exception {
        System.out.println("MAIL FROM 명령을 전송합니다");
        System.out.println(this.protocol.mailFrom());
        pw.println(this.protocol.mailFrom());
        String line;
        while ((line = br.readLine()) != null) {
            if (line.isEmpty()) break;
            System.out.println(line);
            if (!br.ready()) break;
            if (!line.startsWith("250")) {
                throw new Exception("MAIL FROM 실패");
            }
        }

        System.out.println("RCPT TO 명령을 전송합니다");
        System.out.println(this.protocol.rcptTO());
        pw.println(this.protocol.rcptTO());
        while ((line = br.readLine()) != null) {
            if (line.isEmpty()) break;
            System.out.println(line);
            if (!br.ready()) break;

            if (!line.startsWith("250")) {
                throw new Exception("RCPT TO 실패");
            }

        }
        return true;
    }

    public boolean send(BufferedReader br, PrintWriter pw) throws Exception {
        System.out.println("data 명령을 전송합니다");
        System.out.println(this.protocol.data());
        pw.println(this.protocol.data());
        System.out.println(br.readLine());

        String line;
        while ((line = br.readLine()) != null) {
            if (line.isEmpty()) break;
//            System.out.println(line);
            if (!br.ready()) break;
            if (!line.startsWith("354")) {
                throw new Exception("data 명령전송 실패");
            }
        }
        System.out.println("header 명령을 전송합니다");
        System.out.println(this.protocol.header());
        pw.println(this.protocol.header());
//        System.out.println(br.readLine());
        System.out.println("sbject 명령을 전송합니다");
        System.out.println(this.protocol.sbject());
        pw.println(this.protocol.sbject());

        System.out.println("messege 명령을 전송합니다");
        System.out.println(this.protocol.messege());
        pw.println(this.protocol.messege());
        pw.println(".");

        System.out.println("quit 명령을 전송합니다");
        System.out.println(this.protocol.quit());
        pw.println(this.protocol.quit());
        System.out.println("---------");

//        System.out.println("---------");
        return true;
    }


    public void sendMail() throws Exception {
//
        SSLSocket socket = connectSMTPserver(userMail.SMTPserver);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);

        pw.println(this.protocol.sendProtocol());
        String line;
        while ((line = br.readLine()) != null) {
            if (line.isEmpty()) break;
//            System.out.println(line);
            if (!br.ready()) break;
            if (!line.startsWith("354")) {
                throw new Exception("data 명령전송 실패");
            }
        }

//        connectionOK(br);
//        heloServer(br, pw);
//        authLogin(br, pw);
//        fromAndRcptTo(br, pw);
//        send(br, pw);

//        try {
//            connectionOK(br);
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }
//        try {
//            heloServer(br, pw);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }try{
//            authLogin(br, pw);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        try {
//            fromAndRcptTo(br, pw);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }
//        try {
//            send(br, pw);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        System.out.println("==========================");
            System.out.println("메일이 전송되었습니다.");

    }


//    public static void main(String args[])  {
//        try {
//            SSLmail.sendMail();
//            System.out.println("==========================");
//            System.out.println("메일이 전송되었습니다.");
//        } catch (Exception e) {
//            System.out.println("==========================");
//            System.out.println("메일이 발송되지 않았습니다.");
//            System.out.println(e.toString());
//
//        }
//
//
//    }

}


