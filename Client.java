import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Socket {
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private String username;
    public Client(String host, int port) throws IOException {
        super(host,port);
        boolean retry = false;
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.getOutputStream()));
        this.bufferedReader = new BufferedReader(new InputStreamReader(this.getInputStream()));

        Scanner scanner = new Scanner(System.in);
        while(!this.isClosed()) {
            if(!retry){
                System.out.println("Enter Username: ");
            }
            else {
                System.out.println("Username is Taken Please Enter Valid Username: ");
            }
            String username_attempt = scanner.nextLine();
            this.bufferedWriter.write(username_attempt);
            this.bufferedWriter.newLine();
            this.bufferedWriter.flush();
            String val = bufferedReader.readLine();
            if (val.equals("valid")){
                this.username = username_attempt;
                listenForMessage();
                sendMessage();
                break;
            }
            else {
                retry=true;
            }
        }
    }
    public void listenForMessage() {
        new Thread(() -> {
            String messageFromGroupChat;
            while (this.isConnected()) {
                try {
                    messageFromGroupChat = bufferedReader.readLine();
                    if (messageFromGroupChat == null) {
                        System.out.println("Disconnected from server.");
                        break;
                    }
                    System.out.println(messageFromGroupChat);
                } catch (IOException e) {
                    break;
                }
            }
        }).start();
    }
    public void sendMessage() {
        try {
            Scanner scanner = new Scanner(System.in);

            // Keep reading user input and send it to the server
            while (this.isConnected()) {
                String message = scanner.nextLine();
                if (message.equals("!quit")){
                    bufferedWriter.write(message);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                    this.bufferedWriter.close();
                    this.bufferedReader.close();
                    this.close();
                    System.exit(1);
                }
                bufferedWriter.write(username + ": " + message);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }

            scanner.close();
        } catch (IOException e) {
            //closeEverything(socket, bufferedReader, bufferedWriter);
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        try {
            Client client = new Client(args[0], Integer.parseInt(args[1]));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
