package ImageShareGUI;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import Networking.BasicSharing;
import Networking.RequestedImageSharing;
import ReusableClasses.SharableImage;
import ReusableClasses.User;
import Storage.ImageStorage;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class ImageShareController {

	@FXML
	TextField userIP;

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

	}

	public void submitIP() {
		testUser.setIP(userIP.getText());
	}

	public void uploadImage() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select Image");
		File chosenFile = fileChooser.showOpenDialog(null);
		SharableImage chosenImage = new SharableImage(chosenFile, chosenFile.getName(), "test");
		testUser.addSharbleImage(chosenImage);
	}

	public void submitOtherIP() {
		otherUser.setIP(otherIP.getText());
	}


	public void requestImage() throws IOException {
		SharableImage receivedImage =  RequestedImageSharing.sendImageRequest(otherUser.getIP(),otherUser.getFiles().get(0).getTitle()).get();
		RequestedImageSharing.receiveImageRequest(otherUser.getFiles());
		Image image = SwingFXUtils.toFXImage(receivedImage.getImage().get(), null);
		imageDown.setImage(image);
	}
}













