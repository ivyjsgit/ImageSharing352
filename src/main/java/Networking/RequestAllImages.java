package Networking;

import ReusableClasses.SharableImage;
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

        Socket socket = new Socket(ip, 1339);
        PrintWriter outputStreamWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        BufferedReader inputStream = RequestedImageSharing.getBufferedReader(socket.getInputStream());
        outputStreamWriter.write("GET\n");
        outputStreamWriter.flush();

        String arrayListAsString = inputStream.readLine();
        byte[] decodedBase64 = Base64.getDecoder().decode(arrayListAsString);
        ArrayList<SharableImage> asArrayList = (ArrayList<SharableImage>) SerializationUtils.deserialize(decodedBase64);
        System.out.println("Stuff " + asArrayList);
        return asArrayList;
    }

    public static void receiveAllImages(ArrayList<SharableImage> images) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1339);
        Socket socket = serverSocket.accept();
        PrintWriter outputStreamWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        BufferedReader inputStream = RequestedImageSharing.getBufferedReader(socket.getInputStream());
        String recievedCommand = inputStream.readLine();
        if (recievedCommand.equals("GET")) {
            String arrayAsBytes = Base64.getEncoder().encodeToString(SerializationUtils.serialize(images));
            outputStreamWriter.write(arrayAsBytes + "\n");
            outputStreamWriter.flush();
        }
    }
}
