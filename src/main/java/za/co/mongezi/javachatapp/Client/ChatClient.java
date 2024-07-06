package za.co.mongezi.javachatapp.Client;

import java.io.IOException;
import java.net.Socket;


public class ChatClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 2000);
        System.out.println("connected to server");
    }
}
