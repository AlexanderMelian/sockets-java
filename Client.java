import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    Socket socket = null;
    DataOutputStream out = null;
    DataInputStream in = null;
    public Client(String host, int port) {
        try {
            socket = new Socket(host, port);
            System.out.println("Client has connected: " + socket.getInetAddress() + ":" + socket.getPort());
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(System.in);
        } catch (IOException e) {
            System.out.println("Client has stopped");
        }
        String line = "";
        while(!line.equals("exit")) {
            try {
                line = in.readLine();
                out.writeUTF(line);
                out.flush();
            } catch (IOException e) {
                System.out.println("Client has stopped");
            }
        }
        try {
            socket.close();
            out.close();
            in.close();
        } catch (IOException e) {
            System.out.println("Client has stopped");
        }
    }

    public static void main(String[] args) {
        Client client = new Client("localhost", 8080);
 
    }
}
