import netWork.SMTP.MailClient;

public class Client {
    public static void main(String[] args) {
        try {
            while (true) {
                MailClient.main(null);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            main(null);
        }

    }
}
