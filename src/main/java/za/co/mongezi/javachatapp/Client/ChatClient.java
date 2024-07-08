package za.co.mongezi.javachatapp.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class ChatClient {
    private Socket socket = null;
    private BufferedReader inputConsole = null;
    private PrintWriter out = null;
    private BufferedReader in = null;

    public ChatClient(String address, int port) {
        try {
            //connect to chat server
            socket = new Socket(address, port);
            System.out.println("Connected to chat server");

            //initialize input and output streams
            inputConsole = new BufferedReader(new InputStreamReader(System.in));
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String line = "";
            //read from the console and send to the server
            while (!line.equals("exit")) {
                line = inputConsole.readLine();
                out.println(line);
                System.out.println(in.readLine());
            }
            //close resources
            socket.close();
            inputConsole.close();
            out.close();
        } catch (UnknownHostException u) {
            System.out.println("Host unkown" + u.getMessage());
        } catch (IOException e) {
            System.out.println("unexpected exception:" + e.getMessage());
            throw new RuntimeException();
        }
    }

    public static void main(String[] args) {
        ChatClient client= new ChatClient("127.0.0.1",2000);
    }
}