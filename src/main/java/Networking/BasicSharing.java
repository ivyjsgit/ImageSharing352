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
            String imageAsBytes = Base64.getEncoder().encodeToString(SerializationUtils.serialize(sharableImage));
            System.out.println(imageAsBytes);
            Socket connectedSocket = serverSocket.accept();
            PrintWriter stringWriter = new PrintWriter(new BufferedOutputStream(connectedSocket.getOutputStream()));


            stringWriter.write(imageAsBytes);
            stringWriter.flush();
            stringWriter.close();
            serverSocket.close();
            connectedSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static SharableImage receiveImage(String ip) throws IOException {
        RequestedImageSharing.preventRaceCondition();

        Socket socket = new Socket(ip, 1337);
        BufferedReader inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String receivedString = inputStream.readLine();
        byte[] decodedBase64 = Base64.getDecoder().decode(receivedString);

        System.out.println("Got image " + decodedBase64);
        SharableImage gotImage = (SharableImage) SerializationUtils.deserialize(decodedBase64);
        return gotImage;
    }

}
