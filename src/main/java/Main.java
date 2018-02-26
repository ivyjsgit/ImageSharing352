import Networking.BasicSharing;
import ReusableClasses.SharableImage;

import java.io.IOException;

import org.junit.Assert;

public class Main {
    public static void main(String[] args){
    	 new Thread(()->{
             try {
                 SharableImage receivedImage = BasicSharing.recieveImage("10.253.201.40");
                 System.out.println(receivedImage.getAuthor());

             }catch(Exception e){

             }
         }).start();
    	 while (true) {
    		 
    	 }
    }
}
