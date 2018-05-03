package ReusableClasses.Images;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;
import javax.imageio.ImageIO;
import org.apache.commons.io.IOUtils;

public class SharableImage implements Serializable {
    private String title;
    private String author;
    private byte[] imageAsBytes;

    public SharableImage(File file, String title, String author) {
    	this.title = title;
    	this.author = author;
    	if (file != null) {
    		try {
    			FileInputStream fileInputStream = new FileInputStream(file);
    			imageAsBytes = IOUtils.toByteArray(fileInputStream);
    		} catch (FileNotFoundException e) {
    			e.printStackTrace();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	}
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
    	this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public byte[] getByteArray() {
    	return imageAsBytes;
    }

    public Optional<BufferedImage> getImage(){
        try {
            return Optional.of(ImageIO.read(new ByteArrayInputStream(imageAsBytes)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public String toString() {
        return "SharableImage{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object other) {
    	if (other instanceof SharableImage || other instanceof Optional<?>) {
    		return (this.author.equals(((SharableImage) other).getAuthor())
    				&& this.title.equals(((SharableImage) other).getTitle())
    				&& Arrays.equals(imageAsBytes, ((SharableImage) other).getByteArray()));
    	}
    	return false;
    }
}
