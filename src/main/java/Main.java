import Networking.BasicSharing;
import Networking.RequestedImageSharing;
import ReusableClasses.SharableImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.Optional;


public class Main {
    public static void main(String[] args){
<<<<<<< Updated upstream
        new Thread(()->{
            SharableImage testFile= new SharableImage(new File("/Users/ivy/Desktop/Screen Shot 2018-02-20 at 7.37.03 PM.png"), "ImageTestYeahYeah", "test");
            ArrayList<SharableImage> al = new ArrayList<>();
            al.add(testFile);
            try {
                RequestedImageSharing.receiveImageRequest(al);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()->{
            try {
                System.out.println("Trying");
               SharableImage receivedImage =  RequestedImageSharing.sendImageRequest("10.253.201.40","ImageTestYeahYeah").get();
                    File savedFile = new File("/Users/ivy/Desktop/dank.png");
                ImageIO.write(receivedImage.getImage().get(),"png",savedFile);
=======
    	 new Thread(()->{
             try {
                 SharableImage receivedImage = BasicSharing.receiveImage("10.253.201.40");
                 System.out.println();
                 System.out.println(receivedImage.getImage().get());
>>>>>>> Stashed changes

            }catch(Exception e){

            }
        }).start();
    	 while (true) {
    		 
    	 }
    }
}
