package Networking;
import ReusableClasses.Images.SharableImage;
import org.junit.Assert;
import org.junit.Test;

public class BasicSharingTest {

    @Test
    public void TestNetworking(){
        new Thread(()->{
            Networking.BasicSharing.sendImage(new SharableImage(null, "Test", "test"));

        }).start();
        new Thread(()->{
            try {
                SharableImage recievedImage = Networking.BasicSharing.receiveImage("127.0.0.1");
                Assert.assertTrue(recievedImage.getTitle().equals("Test"));
                Assert.assertTrue(recievedImage.getAuthor().equals("test"));

            }catch(Exception e){

            }
        }).start();
    }
}