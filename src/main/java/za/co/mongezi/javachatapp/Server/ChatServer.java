package za.co.mongezi.javachatapp.Server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    public static void  main(String[] args)throws IOException {
        ServerSocket serverSocket = new ServerSocket(2000);
        System.out.println("Server started. waiting for clients..");

        //accept client connections
        Socket clientSocket = serverSocket.accept();
        System.out.println("client connected");
    }
}
