import Networking.BasicSharing;
import ReusableClasses.SharableImage;

import java.io.IOException;

public class Main {
    public static void main(String[] args){
        new Thread(()->{
            BasicSharing.sendImage(new SharableImage(null, "Test", "test"));

        }).start();
            new Thread(()->{
                try {
                    SharableImage recievedImage = BasicSharing.recieveImage("127.0.0.1");
                    System.out.println(recievedImage.getTitle());
                }catch(Exception e){

                }
            }).start();

    }
}
