package Networking;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Assert;
import org.junit.Test;

import ReusableClasses.Images.SharableImage;

public class RequestedImageSharingTest {

	@Test
	public void requestImageSharingTest() throws InterruptedException {
        AtomicReference<String> stringAtomicReference = new AtomicReference<>("");
        ArrayList<SharableImage> images = new ArrayList<SharableImage>();
        final File file = Paths.get("src/main/Resources/Curve1.jpg").toAbsolutePath().toFile();
        images.add(new SharableImage(file, "Curve", "Sam"));
        
        new Thread(() -> {
            try {
                Optional<SharableImage> received = RequestedImageSharing.sendImageRequest("127.0.0.1", "Curve");
                Assert.assertTrue(received.isPresent());
                Assert.assertTrue(images.get(0).equals(received));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (AssertionError e) { //JUnit cannot give us an error for another thread. We must have some external indicator that can be placed inside a lambda.
                stringAtomicReference.set("Error!");
            }

        }).start();

        new Thread(() -> {
            try {
                RequestedImageSharing.receiveImageRequest(images);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(1000);
        Assert.assertFalse(stringAtomicReference.toString().equals("Error!"));
    }

}
