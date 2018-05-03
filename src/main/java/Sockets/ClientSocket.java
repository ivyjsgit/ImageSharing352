package Sockets;

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
        this.socket.setSoTimeout(10000);
        this.outputWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        this.inputReader = getBufferedReader(socket.getInputStream());
    }

    public void sendMessage(String message){
        outputWriter.write(message+"\n");
        outputWriter.flush();
    }
    
    public String recieveMessage() throws IOException {
        return inputReader.readLine();
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
        this.socket=socket;
        this.socket.setSoTimeout(10000);
        this.outputWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        this.inputReader = getBufferedReader(socket.getInputStream());
    }
}
