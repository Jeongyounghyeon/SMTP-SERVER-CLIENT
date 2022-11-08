package netWork.SMTP;

import javax.net.ssl.*;
import java.io.*;

import java.security.KeyStore;


//connection

public class SSLmail {
    //ssl post
    private static int port = 465;
    private static char[] password = "network123".toCharArray();

    UserMail userMail = null;
    SMTP protocol = null;
    String line = null;

    public SSLmail(UserMail userMail) {
        this.userMail = userMail;
        this.protocol = new SMTP(userMail);
    }

    public SSLSocket connectSMTPserver(String smtpServer) throws Exception {
        //SSL 연결.
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

        System.out.println(smtpServer + " SMTP 서버에 연결되었습니다");

        return socket;
    }


    public boolean connectionOK(BufferedReader br) throws Exception {
        System.out.println("*************** connection OK ***************");
        line = br.readLine();
        System.out.println(line);
        if (!line.startsWith("220")) {
            throw new Exception(line);
        }
        System.out.println("*********************************************");
        return true;
    }

    public boolean heloServer(BufferedReader br, PrintWriter pw) throws Exception {
        System.out.println("*************** HELO SERVER ***************");
        pw.println(this.protocol.helo());
        while ((line = br.readLine()) != null) {
            System.out.println(line);
            if (!line.startsWith("250")) {
                throw new Exception(line);
            }
            if (line.isEmpty()) break;
            if (!br.ready()) break;
        }
        System.out.println("*********************************************");

        return true;
    }

    public boolean authLogin(BufferedReader br, PrintWriter pw) throws Exception {
        System.out.println("*************** AUTH LOGIN ***************");

        System.out.println("AUTH login 명령을 전송합니다");

        pw.println(this.protocol.authLogin());
        while ((line = br.readLine()) != null) {
            System.out.println(line);
            if (!line.startsWith("235")) {
                throw new Exception(line);
            }
            if (line.isEmpty()) break;
            if (!br.ready()) break;
        }
        System.out.println("*********************************************");
        return true;
    }

    public boolean fromAndRcptTo(BufferedReader br, PrintWriter pw) throws Exception {
        System.out.println("*************** MAIL FROM ***************");
        System.out.println(this.protocol.mailFrom());
        pw.println(this.protocol.mailFrom());
        while ((line = br.readLine()) != null) {
            System.out.println(line);
            if (!line.startsWith("250")) {
                throw new Exception(line);
            }
            if (line.isEmpty()) break;
            if (!br.ready()) break;
        }
        System.out.println("*********************************************");


        System.out.println("*************** RCPT TO ***************");

        System.out.println("RCPT TO 명령을 전송합니다");
        System.out.println(this.protocol.rcptTO());
        pw.println(this.protocol.rcptTO());
        while ((line = br.readLine()) != null) {
            System.out.println(line);
            if (!line.startsWith("250")) {
                throw new Exception(line);
            }
            if (line.isEmpty()) break;
            if (!br.ready()) break;

        }
        System.out.println("*********************************************");

        return true;
    }

    public boolean dataSend(BufferedReader br, PrintWriter pw) throws Exception {
        System.out.println("*************** data ***************");
        System.out.println(this.protocol.data());
        pw.println(this.protocol.data());
        while ((line = br.readLine()) != null) {
            System.out.println(line);
            if (!line.startsWith("354")) {
                throw new Exception(line);
            }
            if (line.isEmpty()) break;
            if (!br.ready()) break;
        }
        System.out.println("*********************************************");

        return true;
    }

    public boolean sendMessege(BufferedReader br, PrintWriter pw) throws Exception {
        String line;
        System.out.println("*************** messege ***************");
        System.out.println(this.protocol.messege());
        pw.println(this.protocol.header() + "\r\n"
                + this.protocol.sbject() + "\r\n" + "\r\n"
                + this.protocol.messege() + "\r\n"
                + "." + "\r\n"
                + this.protocol.quit());
        System.out.println("******************************************");
        return true;
    }

    public void sendMail() throws Exception {

        // ----------타사 메일서비스와 통신------------
        SSLSocket SSLsocket = connectSMTPserver(userMail.SMTPserver);
        BufferedReader br = new BufferedReader(new InputStreamReader(SSLsocket.getInputStream()));
        PrintWriter pw = new PrintWriter(SSLsocket.getOutputStream(), true);
        // --------------------------------------

        System.out.println("=======외부 SMTP SERVER=======");

        String line;
        System.out.println("------------프로토콜------------");


        System.out.println(this.protocol.sendProtocol());
        //한번에 보내기..(Status 코드 안넘어옴)
//        pw.println(this.protocol.sendProtocol());
        System.out.println("-------------------------------");


        System.out.println("------------- 전송 ------------");
        connectionOK(br);
        heloServer(br, pw);
        authLogin(br, pw);
        fromAndRcptTo(br, pw);
        dataSend(br, pw);
        sendMessege(br, pw);
//        boolean connection = connectionOK(br);
//        if (connection) {
//            boolean heloServer = heloServer(br, pw);
//            if (heloServer) {
//                boolean login = authLogin(br, pw);
//                if (login) {
//                    boolean fromandRcpt = fromAndRcptTo(br, pw);
//                    if (fromandRcpt) {
//                        boolean dataSend = dataSend(br, pw);
//                        if (dataSend) {
//                            sendMessege(br, pw);
//                        }
//                    }
//                }
//            }
//        }

        System.out.println("--------------------------------------");

        while ((line = br.readLine()) != null) {
            System.out.println(line);
            if (line.isEmpty()) break;
            if (!br.ready()) break;
        }

        System.out.println("------------메일이 전송되었습니다------------");
        System.out.println("=======================================");
    }

}


