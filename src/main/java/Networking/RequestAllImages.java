package Networking;

import ReusableClasses.ClientSocket;
import ReusableClasses.SharableImage;
import ReusableClasses.SimpleServerSocket;
import org.apache.commons.lang3.SerializationUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Base64;

import static Networking.RequestedImageSharing.preventRaceCondition;

public class RequestAllImages {
    public static ArrayList<SharableImage> requestAllImages(String ip) throws IOException {
        preventRaceCondition();
        ClientSocket socket = new ClientSocket(ip,1339);
        socket.sendMessageLine("GET");
        String arrayListAsString = socket.recieveMessage();

        byte[] decodedBase64 = Base64.getDecoder().decode(arrayListAsString);
        ArrayList<SharableImage> asArrayList = (ArrayList<SharableImage>) SerializationUtils.deserialize(decodedBase64);
        System.out.println("Stuff " + asArrayList);
        return asArrayList;
    }

    public static void receiveAllImages(ArrayList<SharableImage> images) throws IOException {
        SimpleServerSocket simpleServerSocket = new SimpleServerSocket(1339);
        String recievedCommand = simpleServerSocket.recieveMessage();
        if (recievedCommand.equals("GET")) {
            String arrayAsBytes = Base64.getEncoder().encodeToString(SerializationUtils.serialize(images));
            simpleServerSocket.sendMessageLine(arrayAsBytes);
        }
    }
}
