import netWork.SMTP.SMTPserver;

public class Server {
    public static void main(String[] args) throws Exception {
        try {
        SMTPserver.main(null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

