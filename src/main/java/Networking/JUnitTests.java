package Networking;

import ReusableClasses.SharableImage;
import org.junit.Assert;
import org.junit.Test;

public class JUnitTests {
    
    @Test
    public void TestNetworking(){
        new Thread(()->{
            BasicSharing.sendImage(new SharableImage(null, "Test", "test"));

        }).start();
        new Thread(()->{
            try {
                SharableImage recievedImage = BasicSharing.recieveImage("127.0.0.1");
                Assert.assertTrue(recievedImage.getTitle().equals("Test"));
                Assert.assertTrue(recievedImage.getAuthor().equals("test"));

            }catch(Exception e){

            }
        }).start();
    }
}
