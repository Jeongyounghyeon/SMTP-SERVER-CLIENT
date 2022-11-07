package netWork.SMTP;

import java.io.*;
import java.net.Socket;
public class MailClient {

    public static void main(String[] args) throws Exception {
//
        try {
            while (true) {
            Socket socket = new Socket("127.0.0.1", 25);

            ServerHandler serverHandler = new ServerHandler(socket);
            serverHandler.start();
            DataOutputStream dataout = new DataOutputStream(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));



            System.out.println("MAIL SERVICE");
            System.out.println("MAIL SERVICE를 골라주세요");
            System.out.println("1. gmail 2. naver (숫자를 입력해주세요)\r\nSMTP 설정을 해주셔야 이용가능합니다.");
            String service = in.readLine();
            String SMTPserver;
            if (service.equals("1")) {
                SMTPserver = "smtp.gmail.com";
                System.out.println("gmail 아이디를 입력해주세요.");
            } else if (service.equals("2")) {
                SMTPserver = "smtp.naver.com";
                System.out.println("naver 아이디를 입력해주세요.");
            } else {
                throw new Exception("1. gmail 2. naver 를 입력해주세요.");
            }

            String user = in.readLine();

            System.out.println("비밀번호를 입력해주세요.");
            String password = in.readLine();

            System.out.println("받는사람의 e-mail을 입력해주세요.");
            String to = in.readLine();

            System.out.println("메일 제목을 입력해주세요.");
            String subject = in.readLine();

            System.out.println("메일 내용을 입력해주세요.");
            String content = in.readLine();

            //유저메일 정리
            UserMail userMail = new UserMail(SMTPserver, user, password, to, subject, content);

            //
            SMTP smtp = new SMTP(userMail);

            String protocol = smtp.sendProtocol();

//            System.out.println(protocol);
            dataout.write(protocol.getBytes("UTF-8"));

                try {
                    System.out.println("Press any key to continue...");
                    in.read();
                    for(int i=0;i<80;i++){
                        System.out.println("\n");
                    }

                } catch (Exception err) {
                    err.printStackTrace();
                }

            }


        } catch (Exception e) {
            System.out.println("Error : 서버상태를 확인해주세요.");
//            e.printStackTrace();
//
        }


    }

}
