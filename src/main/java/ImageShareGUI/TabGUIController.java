package ImageShareGUI;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import Networking.RequestAllImages;
import ReusableClasses.Images.SharableImage;
import ReusableClasses.Users.User;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class TabGUIController {

	@FXML
	TabPane tabHolder;

	@FXML
	Label ipViewer;

	@FXML
	TextField otherIP;

	@FXML
	Button upload;

	@FXML
	Button request;

	@FXML
	TilePane tiles;

	FileChooser fileChooser = new FileChooser();
	User shareUser = new User("", "");
	File chosenDirectory;
	
	ArrayList<SharableImage> receivedImages = new ArrayList<SharableImage>();

	public void initialize() {
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("*.jpg", "*.jpg"),
				new FileChooser.ExtensionFilter("*.jpg", "*.jpeg"), new FileChooser.ExtensionFilter("*.png", "*.png"));
		Platform.runLater(() -> {
			try {
				String ip = InetAddress.getLocalHost().getHostAddress();
				shareUser.setIP(ip);
				ipViewer.setText("Your IP is: " + ip);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}

		});
	}

	/*
	 * https://stackoverflow.com/questions/27182323/working-on-creating-image-
	 * gallery-in-javafx-not-able-to-display-image-properly
	 *
	 * Used as reference for some TilePane code
	  *
	 */

	private void addTab(String name) {
		Tab tab = new Tab(name);
		VBox scrollBox = new VBox();
		scrollBox.setFillWidth(false);
		ScrollPane imageScroll = new ScrollPane();
		TilePane imageTiles = new TilePane();
		imageTiles.setPadding(new Insets(15, 15, 15, 15));
		imageTiles.setHgap(15);
		imageTiles.setVgap(15);

		for (SharableImage x : receivedImages) {
			Image image = SwingFXUtils.toFXImage(x.getImage().get(), null);
			ImageView tileImage = new ImageView(image);
			tileImage.setOnMouseClicked((MouseEvent e) -> {
				Image clickedImage = tileImage.getImage();
				saveImage(clickedImage);

			});
			tileImage.setFitHeight(300);
			tileImage.setFitWidth(300);

			imageTiles.getChildren().add(tileImage);
		}

		imageScroll.setContent(imageTiles);
		scrollBox.getChildren().add(imageScroll);
		tab.setContent(imageScroll);

		tabHolder.getTabs().add(tab);
	}

	public void saveImage(Image downImage) {
		FileChooser fileSaver = new FileChooser();
		fileSaver.setTitle("Save Image");
		File savedImage = fileSaver.showSaveDialog(null);
		fileSaver.setInitialDirectory(chosenDirectory);
		
		// https://stackoverflow.com/questions/10471396/appending-the-file-type-to-a-file-in-java-using-jfilechooser
		String filePath = savedImage.getAbsolutePath();
		if (filePath != null && !filePath.endsWith(".png")) {
			savedImage = new File(filePath + ".png");
		}

		BufferedImage buffImage = SwingFXUtils.fromFXImage(downImage, null);
		try {
			ImageIO.write(buffImage, "png", savedImage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void uploadImage() {
		fileChooser.setTitle("Select Image");
		File chosenFile = fileChooser.showOpenDialog(new Stage());
		if (chosenFile != null) {
			new Thread(() -> {

				SharableImage chosenImage = new SharableImage(chosenFile, chosenFile.getName(), "test");
				shareUser.addSharbleImage(chosenImage);

				Image image = SwingFXUtils.toFXImage(chosenImage.getImage().get(), null);
				Platform.runLater(() -> {
					ImageView upImage = new ImageView(image);
					upImage.setFitHeight(300);
					upImage.setFitWidth(300);
					tiles.getChildren().add(upImage);

				});

				try {
					RequestAllImages.receiveAllImages(shareUser.getFiles());
				} catch (IOException e) {
					e.printStackTrace();
				}
				;
			}, "uploadingThread").start();
		}
	}

	public void requestImage() throws IOException {
		Platform.runLater(() -> {
			try {
				receivedImages.clear();
				receivedImages.addAll(RequestAllImages.requestAllImages(otherIP.getText()));
				addTab(otherIP.getText());
			} catch (IOException e) {
				e.printStackTrace();
			}

		});
	}
}
