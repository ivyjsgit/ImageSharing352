package Users;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

public class UserTest {

    public static final String IP = "127.0.0.1";

    @Test
	public void testConstructor() {
		User me = new User("Sam", IP);
		assertTrue(me.getIP().equals(IP));
		assertTrue(me.getName().equals("Sam"));
		assertTrue(me.getFiles().size() == 0);
	}
	
	@Test
	public void testSet() {
		User me = new User("Sam", "127.0.0.1");
		me.setIP("1");
		me.setName("Collin");
		assertTrue(me.getIP().equals("1"));
		assertTrue(me.getName().equals("Collin"));
	}
	
	@Test
	public void testAddAndRemove() {
		User me = new User("Sam", IP);
        Path relPath = Paths.get("src/main/Resources/Curve1.jpg");
		me.addFile(relPath.toAbsolutePath().toFile(), "curve", "Sam");
		assertTrue(me.getFiles().size() == 1);
		me.deleteFile("Fiction");
		assertTrue(me.getFiles().size() == 1);
		me.deleteFile("curve");
		assertTrue(me.getFiles().size() == 0);
	}
}
