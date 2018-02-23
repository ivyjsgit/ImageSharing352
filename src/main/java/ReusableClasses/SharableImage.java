package ReusableClasses;

import java.io.File;
import java.io.Serializable;

public class SharableImage implements Serializable{
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
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

    public SharableImage(File file, String title, String author) {
        this.file = file;
        this.title = title;
        this.author = author;
    }

    private File file;
    private String title;
    private String author;
}
