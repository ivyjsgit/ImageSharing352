package Storage;

import java.io.*;
import java.util.Scanner;

public class DefaultDirectory {
    public static void saveDirectory(String absolutePath, File directory) {
        try {
            Scanner fileIn = new Scanner(absolutePath);
            PrintStream fileOut = new PrintStream(directory);

            while (fileIn.hasNextLine()) {
                fileOut.print(fileIn.nextLine());
            }

            fileIn.close();
            fileOut.close();
        }
        catch (FileNotFoundException e) {
            e.getMessage();
        }
    }
    public static void readDefaultDirectory(File directory, String defaultDir) {
        try {
            InputStream is = new FileInputStream(directory);
            BufferedReader buf = new BufferedReader(new InputStreamReader(is));

            String line = buf.readLine();
            StringBuilder sb = new StringBuilder();

            while(line != null){
                sb.append(line).append("\n");
                line = buf.readLine();
            }

            defaultDir = sb.toString();
            buf.close();
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
