package Networking;

import ReusableClasses.Networking.ClientSocket;
import ReusableClasses.Images.SharableImage;
import ReusableClasses.Networking.SimpleServerSocket;
import org.apache.commons.lang3.SerializationUtils;

import java.io.*;
import java.util.Base64;

public class BasicSharing {
    public static void sendImage(SharableImage sharableImage) throws IOException {
        try {
            SimpleServerSocket simpleServerSocket = new SimpleServerSocket(1337);
            String imageAsBytes = Base64.getEncoder().encodeToString(SerializationUtils.serialize(sharableImage));
            simpleServerSocket.sendMessage(imageAsBytes);
        } catch (IOException e) {
            throw new IOException("Server could not connect to client");
        }

    }

    public static SharableImage receiveImage(String ip) throws IOException {
        RequestedImageSharing.preventRaceCondition();
        ClientSocket clientSocket = new ClientSocket(ip,1337);
        String receivedString = clientSocket.recieveMessage();

        byte[] decodedBase64 = Base64.getDecoder().decode(receivedString);
        SharableImage gotImage = (SharableImage) SerializationUtils.deserialize(decodedBase64);

        return gotImage;
    }

}
