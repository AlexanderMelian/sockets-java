import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    Socket socket = null;
    ServerSocket server = null;
    DataInputStream in = null;
    
    public Server(int port) {
        try {
            server = new ServerSocket(port);
            System.out.println("Server has started: " + server.getInetAddress() + ":" + server.getLocalPort());
            while(true) {

                socket = server.accept();
                System.out.println("Server has connected: " + socket.getInetAddress() + ":" + socket.getPort());
                in = new DataInputStream(socket.getInputStream());
                
                String line = "";
                while(!line.equals("exit")) {
                    line = in.readUTF();
                    System.out.println("Server received: " + line);
                }
                socket.close();
                in.close();   
            }

        } catch (IOException e) {
            System.out.println("Server has stopped");
        }
    }
    public static void main(String[] args) {
        Server server = new Server(8080);
    }
}