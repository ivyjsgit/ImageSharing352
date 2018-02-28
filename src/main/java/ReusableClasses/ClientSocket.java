package ReusableClasses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import static Networking.RequestedImageSharing.getBufferedReader;

public class ClientSocket {


    Socket socket;
    BufferedReader inputReader;
    PrintWriter outputWriter;

    public ClientSocket(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        System.out.println("Connected client");
        this.outputWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        this.inputReader = getBufferedReader(socket.getInputStream());
    }
    public void sendMessageLine(String message){
        outputWriter.write(message+"\n");
        outputWriter.flush();
    }
    public void sendMessage(String message){
        outputWriter.write(message);
        outputWriter.flush();
    }
    public String recieveMessage() throws IOException {
        return inputReader.readLine();
    }
    public Socket getSocket(){
        return this.socket;
    }
    @Override
    public String toString() {
        return "ClientSocket{" +
                "socket=" + socket +
                ", inputReader=" + inputReader +
                ", outputWriter=" + outputWriter +
                '}';
    }
    public ClientSocket(Socket socket) throws IOException {
        System.out.println("Connected client");
        this.socket=socket;
        this.outputWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        this.inputReader = getBufferedReader(socket.getInputStream());
    }
    public ClientSocket(){

    }

}
