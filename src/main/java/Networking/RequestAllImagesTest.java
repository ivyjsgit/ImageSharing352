package Networking;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import ReusableClasses.Images.SharableImage;

public class RequestAllImagesTest {

	@Test
	public void test() {
		ArrayList<SharableImage> images = new ArrayList<SharableImage>();
		new Thread(()->{
            try {
            	RequestAllImages.receiveAllImages(images);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();
        new Thread(()->{
            try {
                ArrayList<SharableImage> received = RequestAllImages.requestAllImages("127.0.0.1");
                Assert.assertTrue(received.equals(images));
            } catch(Exception e) {
            	e.printStackTrace();
            }
        }).start();
	}
}
