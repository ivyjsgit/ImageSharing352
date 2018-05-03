package Networking;

import Sockets.ClientSocket;
import Sockets.SimpleServerSocket;

import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

import Images.SharableImage;

public class RequestedImageSharing {
	static final int portNumber = 1999;
	
    public static Optional<SharableImage> sendImageRequest(String ip, String imageName) throws IOException {
        preventRaceCondition();
        ClientSocket socket = new ClientSocket(ip, portNumber);
        socket.sendMessage(imageName);

        boolean asBoolean = Boolean.valueOf(socket.recieveMessage());
        if (asBoolean) {
            return Optional.of(getSelectedImage(ip));
        } else {
            return Optional.empty();
        }
    }

    public static void receiveImageRequest(ArrayList<SharableImage> files) throws IOException {
        SimpleServerSocket serverSocket = new SimpleServerSocket(portNumber);
        while (!serverSocket.ready()) {
        	
        }
        String requestedImage = serverSocket.recieveMessage();
        if (doesContainImage(requestedImage, files)) {
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
            try {
                BasicSharing.sendImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    protected static SharableImage getSelectedImage(String ip) throws IOException {
        return BasicSharing.receiveImage(ip);
    }
}
