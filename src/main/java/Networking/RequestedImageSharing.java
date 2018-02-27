package Networking;

import ReusableClasses.SharableImage;

import javax.swing.text.html.Option;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Optional;

public class RequestedImageSharing {
    public static Optional<SharableImage> sendImageRequest(String ip, String imageName) throws IOException {
        preventRaceCondition();
        Socket socket = new Socket(ip, 1999);
        System.out.println("Connected client");

        PrintWriter outputStreamWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);

        BufferedReader inputStream = getBufferedReader(socket.getInputStream());
        outputStreamWriter.write(imageName+"\n");
        outputStreamWriter.flush();
        boolean asBoolean =Boolean.valueOf(inputStream.readLine());
        if(asBoolean){
            System.out.println("Have image!");
            return Optional.of(getSelectedImage(ip));
        }else{
            System.out.println("No image :(");
            return Optional.empty();

        }
    }

    public static void receiveImageRequest(ArrayList<SharableImage> files) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1999);
        Socket connectedSocket = serverSocket.accept();
        BufferedReader inputStream = getBufferedReader(connectedSocket.getInputStream());

        System.out.println("Connected Server");
        PrintWriter outputWriter = new PrintWriter(new BufferedOutputStream(connectedSocket.getOutputStream()),true);

        System.out.println("Getting stuff!");
        while (!inputStream.ready()) {}
        System.out.println("Stuff gotten");
        String requestedImage = inputStream.readLine();
        System.out.println("Requested Image " + requestedImage);
        if(doesContainImage(requestedImage,files)){
            System.out.println("Have image!");
            outputWriter.write("true");
            outputWriter.flush();
            outputWriter.close();

        }else{
            System.out.println("No image");
            outputWriter.write("false");
            outputWriter.flush();
            outputWriter.close();
        }
    }

    protected static boolean doesContainImage(String imageName, ArrayList<SharableImage> files){
        for(int i=0;i<files.size();i++){
            if(files.get(i).getTitle().equals(imageName)){
                shareSelectedImage(files.get(i));
                return true;
            }
        }
        return false;
    }

    protected static BufferedReader getBufferedReader(InputStream input){
        return new BufferedReader(new InputStreamReader(input));
    }

    public static void preventRaceCondition(){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected static void shareSelectedImage(SharableImage image){
        new Thread(()->{
            BasicSharing.sendImage(image);
        }).start();
    }
    protected static SharableImage getSelectedImage(String ip) throws IOException {
        return BasicSharing.receiveImage(ip);
    }
}
