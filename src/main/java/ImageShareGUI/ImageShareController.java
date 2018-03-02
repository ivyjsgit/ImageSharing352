package ImageShareGUI;

import Networking.BasicSharing;
import Networking.RequestAllImages;
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
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

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

	@FXML
	TilePane tiles;

	FileChooser fileChooser = new FileChooser();
	User shareUser = new User("", "");

	public void initialize() {
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("*.jpg", "*.jpg"),
				new FileChooser.ExtensionFilter("*.jpg", "*.jpeg"), new FileChooser.ExtensionFilter("*.png", "*.png"));
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					String ip = InetAddress.getLocalHost().getHostAddress();
					System.out.println(ip);
					shareUser.setIP(ip);
					ipViewer.setText("Your IP is: " + ip);
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}

			}
		});

	}

	public void uploadImage() {
		fileChooser.setTitle("Select Image");
		File chosenFile = fileChooser.showOpenDialog(new Stage());

		new Thread(() -> {
			SharableImage chosenImage = new SharableImage(chosenFile, chosenFile.getName(), "test");
			shareUser.addSharbleImage(chosenImage);
			System.out.println("Crashing?");

			Image image = SwingFXUtils.toFXImage(chosenImage.getImage().get(), null);
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					ImageView upImage = new ImageView(image);
					tiles.getChildren().add(upImage);

				}

			});
			try {
				RequestAllImages.receiveAllImages(shareUser.getFiles());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			;
		}).start();

	}

	public void requestImage() throws IOException {
		new Thread(new Runnable() {
			@Override
			public void run() {
				ArrayList<SharableImage> receivedImages = new ArrayList<SharableImage>();
				System.out.println("Getting image!");
				try {
					System.out.println(otherIP.getText());
					receivedImages.addAll(RequestAllImages.requestAllImages(otherIP.getText()));

				} catch (IOException e) {
					e.printStackTrace();
				}
				for (SharableImage x : receivedImages) {
					ImageView tileImage;
					Image image = SwingFXUtils.toFXImage(x.getImage().get(), null);
					tileImage = new ImageView(image);
					tiles.getChildren().addAll(tileImage);
				}
			}
		}).start();

	}
}
