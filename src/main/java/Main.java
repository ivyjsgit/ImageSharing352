import Networking.RequestedImageSharing;
import ReusableClasses.SharableImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.ArrayList;


public class Main {
	public static void main(String[] args){
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

			}catch(Exception e){

			}
		}).start();
		while (true) {

		}
	}
}
