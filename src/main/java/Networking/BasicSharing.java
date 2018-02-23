package Networking;

import ReusableClasses.SharableImage;
import org.apache.commons.lang3.SerializationUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Base64;

public class BasicSharing {
    public static void sendImage(SharableImage sharableImage){
        try {
            ServerSocket serverSocket = new ServerSocket(1337);
            Socket connectedSocket = serverSocket.accept();
            PrintWriter stringWriter = new PrintWriter(new BufferedOutputStream(connectedSocket.getOutputStream()));

            String imageAsBytes = Base64.getEncoder().encodeToString(SerializationUtils.serialize(sharableImage));
            stringWriter.write(imageAsBytes);
            stringWriter.flush();
            stringWriter.close();
            serverSocket.close();
            connectedSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static SharableImage recieveImage(String ip) throws IOException {
        System.out.println("Connecting");
        Socket socket = new Socket(ip, 1337);
        BufferedReader inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String recievedString = inputStream.readLine();
        byte[] decodedBase64 = Base64.getDecoder().decode(recievedString);
        SharableImage gotImage = (SharableImage) SerializationUtils.deserialize(decodedBase64);
        return  gotImage;
    }
}
