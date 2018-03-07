package Networking;

import ReusableClasses.Images.SharableImage;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class BasicSharingTest {
    @Test
    public void sendAndReceiveMessage() throws InterruptedException {
        AtomicReference<String> stringAtomicReference = new AtomicReference<>("");

        new Thread(() -> {
            try {
                SharableImage image = BasicSharing.receiveImage("127.0.0.1");
                Assert.assertTrue(image.getTitle().equals("Test"));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (AssertionError e) { //JUnit cannot give us an error for another thread. We must have some external indicator that can be placed inside a lambda.
                stringAtomicReference.set("Error!");
            }

        }).start();

        new Thread(() -> {
            try {
                SharableImage recievedImage = Networking.BasicSharing.receiveImage("127.0.0.1");
                Assert.assertTrue(recievedImage.getTitle().equals("Test"));
                Assert.assertTrue(recievedImage.getAuthor().equals("test"));

            } catch(Exception e) {
            	e.printStackTrace();
            }
            try {
                BasicSharing.sendImage(new SharableImage(null, "Test", "Test"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(1000);
        Assert.assertFalse(stringAtomicReference.toString().equals("Error!"));
    }

}
