import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
//import ClientHandler

// public class Server {
//     public static void main(String[] args) {
//         try {
//             ServerSocket serverSocket = new ServerSocket(8080);
//             System.out.println("Server has started: " + serverSocket.getInetAddress() + ":" + serverSocket.getLocalPort());
//             while (true) {
//                 Socket socket = serverSocket.accept();
//                 System.out.println("Server has accepted: " + socket.getInetAddress() + ":" + socket.getPort());
//                 new ClientHandler(socket).start();
//             }
//         } catch (IOException e) {
//             System.out.println("Server has stopped");
//         }
//     }
// }

class ClientHandler extends Thread {
    Socket socket;
    DataInputStream in = null;
    DataOutputStream out = null;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            String line = "";
            while (!line.equals("exit")) {
                try {
                    line = in.readUTF();
                    System.out.println(line);
                    out.writeUTF(line);
                    out.flush();
                } catch (IOException e) {
                    System.out.println("Client has stopped");
                    break;
                }
            }
            socket.close();
            out.close();
            in.close();
        } catch (IOException e) {
            System.out.println("Client has stopped");
        }
    }
}

public class Server {
    Socket socket = null;
    ServerSocket server = null;
    DataInputStream in = null;

    public Server(int port) {
        try {
            CreateServer(port);
            while(true) {
                acceptSocket();
                new ClientHandler(socket).start();
            }
            
        } catch (IOException e) {
            System.out.println("Server has stopped");
        }
    }

    public void CreateServer(int port) throws IOException {
        server = new ServerSocket(port);
        System.out.println("Server has started: " + server.getInetAddress() + ":" + server.getLocalPort());
    }
    
    public void acceptSocket() throws IOException {
        socket = server.accept();
        System.out.println("Server has connected: " + socket.getInetAddress() + ":" + socket.getPort());
    }

    public static void main(String[] args) {
        Server server = new Server(8080);
    }
}