import java.io.IOException;

public class Mail {

    public static void main(String args[ ]) {
        try {
            new Messeger();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
