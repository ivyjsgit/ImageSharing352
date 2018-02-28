package Networking;

import ReusableClasses.ClientSocket;
import ReusableClasses.SharableImage;
import ReusableClasses.SimpleServerSocket;
import org.apache.commons.lang3.SerializationUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Base64;

public class BasicSharing {
    public static void sendImage(SharableImage sharableImage) {
        try {
            SimpleServerSocket simpleServerSocket = new SimpleServerSocket(1337);
            String imageAsBytes = Base64.getEncoder().encodeToString(SerializationUtils.serialize(sharableImage));
            simpleServerSocket.sendMessage(imageAsBytes);
        } catch (IOException e) {
            e.printStackTrace();
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

    public static void sendImageRequest(String ip, String imageName) throws IOException {
        ClientSocket clientSocket = new ClientSocket(ip,1338);
        clientSocket.sendMessageLine(imageName);

    }

    public static void receiveImageRequest(ArrayList<SharableImage> files) throws IOException {
        SimpleServerSocket serverSocket = new SimpleServerSocket(1338);
        String requestedImage = serverSocket.recieveMessage();

        if (doesContainImage(requestedImage, files)) {
          serverSocket.sendMessage("true");
        } else {
            serverSocket.sendMessage("false");
        }
    }

    public static boolean doesContainImage(String imageName, ArrayList<SharableImage> files) {
        for (int i = 0; i < files.size(); i++) {
            if (files.get(i).getTitle().equals(imageName)) {
                return true;
            }
        }
        return false;
    }
}
