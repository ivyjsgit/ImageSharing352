package Networking;

import Sockets.ClientSocket;
import Sockets.SimpleServerSocket;

import org.apache.commons.lang3.SerializationUtils;

import Images.SharableImage;

import java.io.*;
import java.util.Base64;

public class BasicSharing {
	static final int portNumber = 1337;

    public static void sendImage(SharableImage sharableImage) throws IOException {
        try {
            SimpleServerSocket simpleServerSocket = new SimpleServerSocket(portNumber);
            encodeAndSend(sharableImage,simpleServerSocket);
        } catch (IOException e) {
            throw new IOException("Server could not connect to client");
        }

    }

    public static SharableImage receiveImage(String ip) throws IOException {
        RequestedImageSharing.preventRaceCondition();
        ClientSocket clientSocket = new ClientSocket(ip, portNumber);
        String receivedString = clientSocket.recieveMessage();

        byte[] decodedBase64 = Base64.getDecoder().decode(receivedString);
        SharableImage gotImage = (SharableImage) SerializationUtils.deserialize(decodedBase64);

        return gotImage;
    }
    
    protected static void encodeAndSend(Serializable data, SimpleServerSocket simpleServerSocket){
        String imageAsBytes = Base64.getEncoder().encodeToString(SerializationUtils.serialize(data));
        simpleServerSocket.sendMessage(imageAsBytes);
    }
}
