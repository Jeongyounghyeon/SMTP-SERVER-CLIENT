package netWork.SMTP;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SMTPserver extends Thread {
    private static int port = 25;
    private Socket sock;

    public SMTPserver(Socket sock) {
        this.sock = sock;
    }

    public void run() {
        //쓰레드가 할일 작성
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            DataOutputStream out = new DataOutputStream(sock.getOutputStream());

            System.out.println(sock + ": 연결완료");
            String SMTPserver = null;
            String ID = null;
            //ID값만 받는다.
            String userEmail = "@";
            String PW = "";
            String TO = null;
            String subject = null;
            String content = "";
            String auth = null;
            String line;

            // 메세지 파싱
            System.out.println("=======클라이언트에서 받은 내용=======");
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                if (line.startsWith("EHLO") || line.startsWith("HELO")) {
                    SMTPserver = line.substring(5);
//                    System.out.println(SMTPserver);
                } else if (line.startsWith("AUTH")) {
                    line = line.replace("AUTH", "");
                    line = line.replace("PLAIN", "");
                    auth = line.trim();
//                    System.out.println(auth);
                } else if (line.startsWith("MAIL FROM:")) {
                    line = line.replace("MAIL FROM: <", "");
                    userEmail = line.replace(">", "").trim();
//                     String[] userEmai = line.split("(\\b<\\b)(.*?)(\\b>\\b)");
//                    System.out.println(userEmail);
                } else if (line.startsWith("RCPT TO:")) {
                    line = line.replace("RCPT TO: <", "");
                    TO = line.replace(">", "").trim();
//                     String[] userEmai = line.split("(\\b<\\b)(.*?)(\\b>\\b)");
//                    System.out.println(TO);
                } else if (line.startsWith("subject: ")) {
                    subject = line.replace("subject: ", "");
                } else if (line.equals(".")) {
                    break;
                } else if (line.startsWith("QUIT")) {
//                    System.out.println(line);
                    break;
                } else if (line.startsWith("DATE")) {

                } else if (line.startsWith("TO")) {

                } else if (line.startsWith("FROM")) {

                } else if (line.startsWith("data")) {
                } else {
                    content = content + line;
//                    System.out.println(content);

                }
                if (SMTPserver.equals("smtp.gmail.com")) {
                    int golbangIdx = userEmail.indexOf("@");
                    ID = userEmail.substring(0, golbangIdx);
                } else if (SMTPserver.equals("smtp.naver.com")) {
                    ID = userEmail;
                }
            }
            System.out.println("======================");

//            UserMail userMail = new UserMail(SMTPserver, ID, PW, TO, subject, content, auth);
            UserMail userMail = new UserMail(SMTPserver, ID, TO, subject, content);
            userMail.setAuth(auth);
//
            SSLmail sslmail = new SSLmail(userMail);
            System.out.println(sslmail.protocol);
            sslmail.sendMail();
            out.write("221-메일을 성공적으로 보냈습니다.".getBytes("UTF-8"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            try {
                DataOutputStream out = new DataOutputStream(sock.getOutputStream());
                out.write(e.getMessage().getBytes());
                sock.close();
            } catch (Exception err) {

            }

        } finally {
            try {
                if (sock != null) {
                    sock.close();
                }

            } catch (IOException e) {

            }


        }

    }


    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println(serverSocket + ": 서버소켓 생성");
        while (true) {
            Socket client = serverSocket.accept();

            SMTPserver smtpServer = new SMTPserver(client);

            // 쓰레드 시작
            smtpServer.start();

        }

    }
}
