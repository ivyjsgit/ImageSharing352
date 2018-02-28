package ReusableClasses;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static Networking.RequestedImageSharing.getBufferedReader;

public class SimpleServerSocket extends ClientSocket{

    public SimpleServerSocket(int port) throws IOException {
        super(new ServerSocket(port).accept());
        System.out.println(socket);
        this.outputWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        this.inputReader = getBufferedReader(socket.getInputStream());
    }
    public boolean ready() throws IOException {
        return inputReader.ready();
    }
}


