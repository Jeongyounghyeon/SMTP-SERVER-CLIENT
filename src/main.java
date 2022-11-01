package netWork.SMTP;

import javax.net.ssl.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.Date;
import java.util.Timer;

public class main {
    public static void sendMail(String smtpServer, String sender, String recipient, String content) throws Exception {
//        System.setProperty("javax.net.ssl.keyStore", "./prgrms_keystore.p12");
//        System.setProperty("javax.net.ssl.trustStore", "./prgrms_truststore.p12");
//
//        System.setProperty("javax.net.ssl.keyStorePassword", "network123");
//        System.setProperty("javax.net.debug", "ssl");
        System.out.println("&&&&&& keyStore : " + System.getProperty("javax.net.ssl.keyStore"));
        System.out.println("&&&&&& trustStore : " + System.getProperty("javax.net.ssl.trustStore"));
        int port = 465;
        char[] password = "network123".toCharArray();
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
        SSLSocket socket = (SSLSocket)sslsocketfactory.createSocket("smtp.gmail.com", port);


        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
        System.out.println("서버에 연결되었습니다");

        String line = br.readLine();
        System.out.println("응답 : " + line);
        if (!line.startsWith("220")) {
            throw new Exception("SMTP서버가 아님");
        }

        System.out.println("HELO 명령을 전송합니다");
        pw.println("EHLO localhost");
        while ((line = br.readLine()) != null) {
            if (line.isEmpty()) break;
            System.out.println(line);
            if (!br.ready()) break;


            if (!line.startsWith("250")) {
                throw new Exception("HELO 실패");
            }
        }


        System.out.println("AUTH login 명령을 전송합니다");
        pw.println("AUTH PLAIN AGRlbm92b2xpZmUwMDAwAHBjZXdkbW1iZGlocGluYmo=");
        while ((line = br.readLine()) != null) {
            if (line.isEmpty()) break;
            System.out.println(line);
            if (!br.ready()) break;

//            if (!line.startsWith("250")) {
//                throw new Exception("HELO 실패");
//            }

        }

        System.out.println("MAIL FROM 명령을 전송합니다");
        pw.println("MAIL FROM:  <vo0v0000>");
        while ((line = br.readLine()) != null) {
            if (line.isEmpty()) break;
            System.out.println(line);
            if (!br.ready()) break;

//            if (!line.startsWith("250")) {
//                throw new Exception("HELO 실패");
//            }

        }
//

        System.out.println("RCPT TO 명령을 전송합니다");
        pw.println("RCPT TO: <vo0v0000@naver.com>");
        while ((line = br.readLine()) != null) {
            if (line.isEmpty()) break;
            System.out.println(line);
            if (!br.ready()) break;

//            if (!line.startsWith("250")) {
//                throw new Exception("HELO 실패");
//            }

        }

        System.out.println("data 명령을 전송합니다");
        pw.println("data");
        while ((line = br.readLine()) != null) {
            if (line.isEmpty()) break;
            System.out.println(line);
            if (!br.ready()) break;

//            if (!line.startsWith("250")) {
//                throw new Exception("HELO 실패");
//            }

        }
//
        System.out.println("Date:" +new Date());
        pw.println("Date:" +new Date());
        System.out.println("TO:");
        pw.println("TO: vo0v0000@naver.com");
        System.out.println("from: vo0v0000@naver.com");
        pw.println("from: vo0v0000@naver.com");
        System.out.println("subject: it's test 명령을 전송합니다");
        pw.println("subject: it's test3\r\nasdfadsf\r\n.\r\nQuit");
        System.out.println(br.readLine());

        System.out.println("Quit 명령을 전송합니다");
        pw.println("Quit");

    }

    public static void main(String args[]) throws IOException{
        ServerSocket serverSocket = new ServerSocket(25);
        System.out.println("Listening for Connection on " + 25);

        while (true){
            Socket socket = serverSocket.accept();
            InputStream is = socket.getInputStream();


        }
//        try {
//            main.sendMail(
//                    "server",
//                    "sender",
//                    "recipient",
//                    "내용"
//            );
//            System.out.println("==========================");
//            System.out.println("메일이 전송되었습니다.");
//        } catch (Exception e) {
//            System.out.println("==========================");
//            System.out.println("메일이 발송되지 않았습니다.");
//            System.out.println(e.toString());
//        }
    }
}


