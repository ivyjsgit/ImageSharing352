import Networking.BasicSharing;
import ReusableClasses.SharableImage;



public class Main {
    public static void main(String[] args){
    	 new Thread(()->{
             try {
                 SharableImage receivedImage = BasicSharing.receiveImage("10.253.201.40");
                 System.out.println(receivedImage.getAuthor());

             }catch(Exception e){

             }
         }).start();
    	 while (true) {
    		 
    	 }
    }
}
