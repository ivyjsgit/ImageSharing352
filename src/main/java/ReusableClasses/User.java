package ReusableClasses;

import java.io.File;
import java.util.ArrayList;

public class User {
	private ArrayList<SharableImage> files;
	private String name;
	private String ip;
	
	public User(String name, String ip) {
		files = new ArrayList<SharableImage>();
		this.name = name;
		this.ip = ip;
	}
	
	public void deleteFile(String fileName) {
		int i = 0;
		while (!files.get(i).getTitle().equals(fileName) && i != files.size()) {
			i++;
		}
		if (i != files.size()) {
			files.remove(i);
		}
	}
	
	public void addFile(File file, String name, String author) {
		files.add(new SharableImage(file, name, author));
	}
	
	public String getName() {
		return name;
	}
	
	public String getIP() {
		return ip;
	}
}
