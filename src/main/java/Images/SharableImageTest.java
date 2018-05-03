package Images;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class SharableImageTest {

	public static final File FILE = Paths.get("src/main/Resources/Curve1.jpg").toAbsolutePath().toFile();


	@Test
	public void testConstructor() {
		SharableImage image = new SharableImage(FILE, "pic", "Sam");
		assertTrue(image.getAuthor().equals("Sam"));
		assertTrue(image.getTitle().equals("pic"));
	}
	
	@Test
	public void testToString() {
		SharableImage image = new SharableImage(FILE, "pic", "Sam");
		assertTrue(image.toString().equals("SharableImage{title='pic', author='Sam'}"));
	}
	
	@Test
	public void testSet() {
		SharableImage image = new SharableImage(FILE, "pic", "Sam");
		image.setAuthor("Colby");
		image.setTitle("notPic");
		assertTrue(image.getAuthor().equals("Colby"));
		assertTrue(image.getTitle().equals("notPic"));
	}
	
	@Test
	public void testGetImage() {
		File file = FILE;
		SharableImage image = new SharableImage(file, "pic", "Sam");
		byte[] picture = image.getByteArray();
		byte[] copy = null;
		try {
			copy = IOUtils.toByteArray(new FileInputStream(file));
		} catch (IOException e) {
            e.printStackTrace();
        }
		assertTrue(Arrays.equals(picture, copy));
		assertTrue(image.equals(image));
	}
}
