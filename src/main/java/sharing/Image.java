package sharing;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Optional;

import javax.imageio.ImageIO;

public class Image {
	private String sender;
	private String name;
	private byte[] image;
	
	public Image(String sender, String name, String fileName) {
		this.sender = sender;
		this.name = name;
		this.image = toByteArray(fileName);
	}
	
	public byte[] toByteArray(String fileName) {
		byte[] array;
		try{
			BufferedImage image = ImageIO.read(new File(fileName));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(image, "png", baos);
			array = baos.toByteArray();
			return array;
		} catch(Exception e) {
			System.out.println("This image could not be read.");
			array = new byte[0];
		}
		return array;
	}
	
	public void saveImage(String fileName) {
		try {
		    OutputStream out = new BufferedOutputStream(new FileOutputStream(fileName));
		    out.write(image);
		    out.close();
		} catch(Exception e) {
		    System.out.println("Image could not be saved.");
		}
	}
}
