package za.co.mongezi.javachatapp.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {

    //List to keep track of all connected clients
    private static List<ClientHandler> clients = new ArrayList<>();


    public static void  main(String[] args)throws IOException {
        //create server socket listening on port 2000
        ServerSocket serverSocket = new ServerSocket(2000);
        System.out.println("Server started. waiting for clients..");

        //continuously accept client connections
        while(true) {
            //accept client connections
            Socket clientSocket = serverSocket.accept();
            System.out.println("client connected" + clientSocket);

            //create new thread for each client
            ClientHandler clientThread = new ClientHandler(clientSocket, clients);
            clients.add(clientThread);
            new Thread(clientThread).start();
        }
    }
}
class  ClientHandler implements Runnable{
    private Socket clientSocket;
    private List<ClientHandler> clients;
    private PrintWriter out;
    private BufferedReader in;

    public ClientHandler(Socket socket, List<ClientHandler> clients) throws IOException {
        this.clientSocket = socket;
        this.clients = clients;
        this.out = new PrintWriter(clientSocket.getOutputStream(),true);
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    @Override
    public void run() {
        try{
            String inputLine;
            //read mesage from client
            while((inputLine = in.readLine()) != null) {
                //broadcast  message to all clients
                for (ClientHandler aClient:clients){
                    aClient.out.println(inputLine);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred:" + e.getMessage());
            throw new RuntimeException(e);
        }finally {
            try{
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}