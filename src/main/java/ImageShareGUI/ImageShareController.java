package ImageShareGUI;

import Networking.BasicSharing;
import ReusableClasses.Images.SharableImage;
import ReusableClasses.Users.User;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ImageShareController {

    @FXML
    Label ipViewer;

    @FXML
    TextField otherIP;

    @FXML
    Button upload;

    @FXML
    Button request;

    @FXML
    ImageView imageDown;
    FileChooser fileChooser = new FileChooser();
    User testUser = new User("test", "");
    User otherUser = new User("test1", "");

    public void initialize() {
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPEG", "*.jpg"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    String ip = InetAddress.getLocalHost().getHostAddress();
                    System.out.println(ip);
                    ipViewer.setText("Your IP is: " + ip);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    public void submitIP() {
    }

    public void uploadImage() {
        fileChooser.setTitle("Select Image");
        File chosenFile = fileChooser.showOpenDialog(new Stage());

        new Thread(new Runnable() {
            @Override
            public void run() {
                SharableImage chosenImage = new SharableImage(chosenFile, chosenFile.getName(), "test");
                System.out.println("Crashing?");
                testUser.addSharbleImage(chosenImage);

                Image image = SwingFXUtils.toFXImage(chosenImage.getImage().get(), null);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        imageDown.setImage(image);

                    }
                }).start();
                BasicSharing.sendImage(chosenImage);
            }
        }).start();

    }

    public void submitOtherIP() {
        otherUser.setIP(otherIP.getText());
    }


    public void requestImage() throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharableImage receivedImage = null;
                System.out.println("Getting image!");
                try {
                    System.out.println(otherIP.getText());
                    receivedImage = BasicSharing.receiveImage(otherIP.getText());
                    System.out.println(receivedImage);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                Image image = SwingFXUtils.toFXImage(receivedImage.getImage().get(), null);
                imageDown.setImage(image);
            }
        }).start();

    }
}













