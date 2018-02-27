import Networking.RequestAllImages;
import Networking.RequestedImageSharing;
import ReusableClasses.SharableImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.ArrayList;


public class Main {
	public static void main(String[] args){
		new Thread(()->{
			SharableImage testFile= new SharableImage(new File("/Users/ivy/Desktop/Screen Shot 2018-02-20 at 7.37.03 PM.png"), "ImageTestYeahYeah", "test");
			SharableImage image2 = new SharableImage(new File("/Users/ivy/Downloads/shrek is life.jpg"),"yes","yes");
			ArrayList<SharableImage> al = new ArrayList<>();
			al.add(testFile);
			al.add(image2);
            try {
                RequestAllImages.receiveAllImages(al);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
				RequestedImageSharing.receiveImageRequest(al);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();
		new Thread(()->{
			try {
            ArrayList<SharableImage> images = RequestAllImages.requestAllImages("127.0.0.1");
                System.out.println(images);
			}catch(Exception e){

			}
		}).start();
		while (true) {

		}
	}
}
