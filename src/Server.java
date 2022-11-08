import netWork.SMTP.SMTPserver;

public class Server {
    public static void main(String[] args) {
        try {
            SMTPserver.main(null);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            SMTPserver.main(null);
        }
    }
}

