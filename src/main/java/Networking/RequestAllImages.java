package Networking;

import Sockets.ClientSocket;
import Sockets.SimpleServerSocket;

import org.apache.commons.lang3.SerializationUtils;

import Images.SharableImage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

import static Networking.RequestedImageSharing.preventRaceCondition;

public class RequestAllImages {
	static final int portNumber = 1339;
	
    public static ArrayList<SharableImage> requestAllImages(String ip) throws IOException {
        preventRaceCondition();
        ClientSocket socket = new ClientSocket(ip, portNumber);
        socket.sendMessage("GET");
        String arrayListAsString = socket.recieveMessage();

        byte[] decodedBase64 = Base64.getDecoder().decode(arrayListAsString);
        ArrayList<SharableImage> asArrayList = (ArrayList<SharableImage>) SerializationUtils.deserialize(decodedBase64);
        System.out.println("Stuff " + asArrayList);
        return asArrayList;
    }

    public static void receiveAllImages(ArrayList<SharableImage> images) throws IOException {
        SimpleServerSocket simpleServerSocket = new SimpleServerSocket(portNumber);
        String receivedCommand = simpleServerSocket.recieveMessage();
        if (receivedCommand.equals("GET")) {
            BasicSharing.encodeAndSend(images,simpleServerSocket);
        }
    }
}
