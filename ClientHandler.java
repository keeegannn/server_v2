import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{
    public static final ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private final Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private String client_username;

    public ClientHandler(Socket socket) throws IOException {
        this.socket= socket;
    }


    @Override
    public void run() {
        try {
            String possible_username;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            while(true) {
                possible_username = this.bufferedReader.readLine();
                boolean valid = true;
                for (ClientHandler clientHandler : clientHandlers) {
                    if (clientHandler.client_username != null) {
                        if (possible_username.equals(clientHandler.client_username)) {
                            valid = false;
                            break;
                        }
                    }
                }
                if(valid) {
                    this.bufferedWriter.write("valid");
                    this.bufferedWriter.newLine();
                    this.bufferedWriter.flush();
                    this.client_username = possible_username;
                    clientHandlers.add(this);
                    System.out.println(this.client_username + " has joined the chat");
                    broadcastMessage("SERVER: " + this.client_username + "has joined the chat");
                    break;
                }
                else {
                    this.bufferedWriter.write("invalid");
                    this.bufferedWriter.newLine();
                    this.bufferedWriter.flush();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        String message;
        while(!this.socket.isClosed()) {
            try {
                message = this.bufferedReader.readLine();
                if (message.equals("!quit")){
                    clientHandlers.remove(this);
                    this.bufferedWriter.close();
                    this.bufferedReader.close();
                    this.socket.close();
                    broadcastMessage("SERVER: " + this.client_username + " has left the chat");
                }
                else{
                    broadcastMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void broadcastMessage(String message) {
        synchronized (clientHandlers) {
            for (ClientHandler clientHandler : clientHandlers) {
                try {
                    if (!clientHandler.client_username.equals(client_username)) {
                        clientHandler.bufferedWriter.write(message);
                        clientHandler.bufferedWriter.newLine();
                        clientHandler.bufferedWriter.flush();
                    }
                } catch (IOException e) {
                    closeEverything(clientHandler.socket, clientHandler.bufferedReader, clientHandler.bufferedWriter);
                }
            }
        }
    }
    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(1);
    }
}
