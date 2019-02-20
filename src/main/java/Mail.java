import java.io.IOException;

public class Mail {

    public static void main(String args[ ]) {
        try {
            new Message();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
