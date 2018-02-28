package Networking;

import ReusableClasses.ClientSocket;
import ReusableClasses.SharableImage;
import ReusableClasses.SimpleServerSocket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Optional;

public class RequestedImageSharing {
    public static Optional<SharableImage> sendImageRequest(String ip, String imageName) throws IOException {
        preventRaceCondition();
        ClientSocket socket = new ClientSocket(ip,1999);
        socket.sendMessage(imageName);

        boolean asBoolean = Boolean.valueOf(socket.recieveMessage());
        if (asBoolean) {
            System.out.println("Have image!");
            return Optional.of(getSelectedImage(ip));
        } else {
            System.out.println("No image :(");
            return Optional.empty();

        }
    }

    public static void receiveImageRequest(ArrayList<SharableImage> files) throws IOException {
        SimpleServerSocket serverSocket = new SimpleServerSocket(1999);

        System.out.println("Getting stuff!");
        while (!serverSocket.ready()) {
        }
        System.out.println("Stuff gotten");
        String requestedImage = serverSocket.recieveMessage();
        if (doesContainImage(requestedImage, files)) {
            System.out.println("Have image!");
            serverSocket.sendMessage("true");
        } else {
           serverSocket.sendMessage("false");
        }
    }

    protected static boolean doesContainImage(String imageName, ArrayList<SharableImage> files) {
        for (int i = 0; i < files.size(); i++) {
            if (files.get(i).getTitle().equals(imageName)) {
                shareSelectedImage(files.get(i));
                return true;
            }
        }
        return false;
    }

    public static BufferedReader getBufferedReader(InputStream input) {
        return new BufferedReader(new InputStreamReader(input));
    }

    public static void preventRaceCondition() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected static void shareSelectedImage(SharableImage image) {
        new Thread(() -> {
            BasicSharing.sendImage(image);
        }).start();
    }

    protected static SharableImage getSelectedImage(String ip) throws IOException {
        return BasicSharing.receiveImage(ip);
    }
}
