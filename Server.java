import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    ServerSocket serverSocket;
    public Server(int port){
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void startServer(){
        try{

            while(!this.serverSocket.isClosed()){

                Socket socket = this.serverSocket.accept();
                System.out.println("Someone Connected");
                ClientHandler clientHandler = new ClientHandler(socket);
                Thread thread = new Thread(clientHandler);
                thread.start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Port");
        String por = scanner.nextLine();
        int port = Integer.parseInt(por);
        Server server = new Server(port);
        server.startServer();
    }
}

