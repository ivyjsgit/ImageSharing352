package Networking;

import ReusableClasses.Networking.ClientSocket;
import ReusableClasses.Images.SharableImage;
import ReusableClasses.Networking.SimpleServerSocket;
import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

import static Networking.RequestedImageSharing.preventRaceCondition;

public class RequestAllImages {
    public static ArrayList<SharableImage> requestAllImages(String ip) throws IOException {
        preventRaceCondition();
        ClientSocket socket = new ClientSocket(ip,1339);
        socket.sendMessage("GET");
        String arrayListAsString = socket.recieveMessage();

        byte[] decodedBase64 = Base64.getDecoder().decode(arrayListAsString);
        ArrayList<SharableImage> asArrayList = (ArrayList<SharableImage>) SerializationUtils.deserialize(decodedBase64);
        System.out.println("Stuff " + asArrayList);
        return asArrayList;
    }

    public static void receiveAllImages(ArrayList<SharableImage> images) throws IOException {
        SimpleServerSocket simpleServerSocket = new SimpleServerSocket(1339);
        String receivedCommand = simpleServerSocket.recieveMessage();
        if (receivedCommand.equals("GET")) {
            BasicSharing.encodeAndSend(images,simpleServerSocket);
        }
    }
}
