package Sockets;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class NetworkingClassesTest {
    @Test
    public void sendAndReceiveMessage() throws InterruptedException {
    	AtomicReference<String> stringAtomicReference = new AtomicReference<>("");

    	new Thread(() -> {
    		try {
    			SimpleServerSocket serverSocket = new SimpleServerSocket(3092);
    			String message = serverSocket.recieveMessage();
    			Assert.assertTrue(message.equals("Hello"));
    		} catch (IOException e) {
    			e.printStackTrace();
    		} catch (AssertionError e) { //JUnit cannot give us an error for another thread. We must have some external indicator that can be placed inside a lambda.
    			stringAtomicReference.set("Error!");
    		}

    	}).start();

    	new Thread(() -> {
    		try {
    			Thread.sleep(500);
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
    		clientThread("Hello");
    	}).start();

    	Thread.sleep(1000);
    	Assert.assertFalse(stringAtomicReference.toString().equals("Error!"));
    }

    private static void clientThread(String message) {
    	new Thread(() -> {
    		ClientSocket clientSocket = null;
    		try {
    			clientSocket = new ClientSocket("127.0.0.1", 3092);
    			clientSocket.sendMessage(message);

    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	}).start();
    }
}
