package Networking;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Assert;
import org.junit.Test;

import ReusableClasses.Images.SharableImage;

public class RequestAllImagesTest {
	
	@Test
	public void requestAllImagesTest() throws InterruptedException {
        AtomicReference<String> stringAtomicReference = new AtomicReference<>("");
        ArrayList<SharableImage> images = new ArrayList<SharableImage>();
        new Thread(() -> {
            try {
                ArrayList<SharableImage> received = RequestAllImages.requestAllImages("127.0.0.1");
                Assert.assertTrue(images.equals(received));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (AssertionError e) { //JUnit cannot give us an error for another thread. We must have some external indicator that can be placed inside a lambda.
                stringAtomicReference.set("Error!");
            }

        }).start();

        new Thread(() -> {
            try {
                RequestAllImages.receiveAllImages(images);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(1000);
        Assert.assertFalse(stringAtomicReference.toString().equals("Error!"));
    }
}
