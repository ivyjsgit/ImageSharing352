package Storage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class ImageStorage{
    public static void saveImage(ReusableClasses.SharableImage image, String pathname) throws IOException {
        File savedFile = new File(pathname);
        ImageIO.write(image.getImage().get(),"png",savedFile);
    }
}
