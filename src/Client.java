import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {
    Socket socket;
    Scanner input;
    PrintStream output;
    ChatServer chatServer;
    public Client(Socket socket, ChatServer chatServer) {
        this.socket = socket;
        this.chatServer = chatServer;
        Thread clientThread = new Thread(this);
        clientThread.start();
    }
    public void receiveMessage(String message) {
        output.println(message);
    }
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            input = new Scanner(inputStream);
            output = new PrintStream(outputStream);
            output.println("Welcome to chat!");
            String inputStr = input.nextLine();
            while (!inputStr.equals("bye")) {
                chatServer.sendMessage(inputStr);
                inputStr = input.nextLine();
            }
            socket.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
