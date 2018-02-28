package ImageShareGUI;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import Networking.BasicSharing;
import Networking.RequestedImageSharing;
import ReusableClasses.SharableImage;
import ReusableClasses.User;
import Storage.ImageStorage;
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

public class ImageShareController {

	@FXML
    Label ipViewer;

	@FXML
	TextField otherIP;

	@FXML
	Button subIP;

	@FXML
	Button upload;

	@FXML
	Button subOtherIP;

	@FXML
	Button request;

	@FXML
	ImageView imageDown;

	User testUser = new User("test", "");
	User otherUser = new User("test1", "");

	public void initialize() {
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
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select Image");
        File chosenFile = fileChooser.showOpenDialog(new Stage());

        new Thread(new Runnable() {
        @Override
        public void run() {
            SharableImage chosenImage = new SharableImage(chosenFile, chosenFile.getName(), "test");
            System.out.println("Crashing?");
            testUser.addSharbleImage(chosenImage);
            BasicSharing.sendImage(chosenImage);
            
            Image image = SwingFXUtils.toFXImage(chosenImage.getImage().get(), null);
            imageDown.setImage(image);
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













