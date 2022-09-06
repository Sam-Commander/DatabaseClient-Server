import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    //declare variables
    Socket aSocket;
    int clientID;
    Database DB;

    //Constructor
    public ClientHandler (Socket socket, int clientId, Database db)
    {
        aSocket = socket;
        clientID = clientId;
        DB = db;
    }

    public void run() {
        try{
            System.out.println("ClientHandler started...");

            // Create I/O streams to read/write data, PrintWriter and BufferedReader
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
            PrintWriter outToClient = new PrintWriter(aSocket.getOutputStream(), true);
            String clientMessage;

            // Receive messages from the client and send replies, until the user types "stop"
            while(!(clientMessage = inFromClient.readLine()).equals("stop")) {
                System.out.println("Client sent the artist name " + clientMessage);

                // Request the number of titles from the db
                Database dbCaller = new Database();
                int titlesNum;
                titlesNum = dbCaller.getTitles(clientMessage);

                // Send reply to Client:
                outToClient.println("Number of titles: " + titlesNum + " records found");
            }

            System.out.println("Client " + clientID + " has disconnected");
            outToClient.println("Connection closed, Goodbye!");

            // Close I/O streams and socket
            inFromClient.close();
            outToClient.close();
            aSocket.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
