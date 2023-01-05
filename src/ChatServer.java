import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {
        ArrayList<Client> clients = new ArrayList<>();
        ServerSocket srvSocket;
        public ChatServer() {
           try {
                srvSocket = new ServerSocket(1234);
            }
            catch (IOException ex) {
                System.out.println("Server socket did not created: Port is busy");
            }
        }

        public void sendMessage(String message) {

            for (Client client : clients) {
                client.receiveMessage(message);
            }
        }
        public void run() {
            while (true) {
                System.out.println("Waiting for clients...");
                try {
                    Socket socket = srvSocket.accept();
                    System.out.println("Client connected!");
                    clients.add(new Client(socket, this));
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    public static void main(String[] args) throws IOException {
            ChatServer server = new ChatServer();
            server.run();
    }
}
