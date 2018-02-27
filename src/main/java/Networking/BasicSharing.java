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
<<<<<<< Updated upstream
=======
    public static void sendImageRequest(String ip, String imageName) throws IOException {
        Socket socket = new Socket(ip, 1338);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());
        outputStreamWriter.write(imageName);

    }
    public static void receiveImageRequest(ArrayList<SharableImage> files) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1338);
        Socket connectedSocket = serverSocket.accept();
        PrintWriter outputWriter = new PrintWriter(new BufferedOutputStream(connectedSocket.getOutputStream()));

        BufferedReader inputStream = new BufferedReader(new InputStreamReader(connectedSocket.getInputStream()));
        String requestedImage = inputStream.readLine();

        if(doesContainImage(requestedImage,files)){
            outputWriter.write("true");
            outputWriter.flush();
            outputWriter.close();
        }else{
            outputWriter.write("false");
            outputWriter.flush();
            outputWriter.close();
        }
    }
    public static boolean doesContainImage(String imageName, ArrayList<SharableImage> files){
        for(int i=0;i<files.size();i++){
            if(files.get(i).getTitle().equals(imageName)){
                return true;
            }
        }
        return  false;
    }
>>>>>>> Stashed changes

}
