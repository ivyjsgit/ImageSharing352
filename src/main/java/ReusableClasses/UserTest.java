package ReusableClasses;

import static org.junit.Assert.*;
import java.io.File;
import org.junit.Test;

public class UserTest {

	@Test
	public void testConstructor() {
		User me = new User("Sam", "1.0.0.1");
		assertTrue(me.getIP().equals("1.0.0.1"));
		assertTrue(me.getName().equals("Sam"));
		assertTrue(me.getFiles().size() == 0);
	}
	
	@Test
	public void testSet() {
		User me = new User("Sam", "1.0.0.1");
		me.setIP("1");
		me.setName("Collin");
		assertTrue(me.getIP().equals("1"));
		assertTrue(me.getName().equals("Collin"));
	}
	
	@Test
	public void testAddAndRemove() {
		User me = new User("Sam", "1.0.0.1");
		me.addFile(new File("C:/Users/Samantha/Downloads/Curve1.jpg"), "curve", "Sam");
		assertTrue(me.getFiles().size() == 1);
		me.deleteFile("Fiction");
		assertTrue(me.getFiles().size() == 1);
		me.deleteFile("curve");
		assertTrue(me.getFiles().size() == 0);
	}
}
