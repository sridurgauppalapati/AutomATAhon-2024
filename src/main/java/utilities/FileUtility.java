package utilities;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileUtility {

    public static void writeToFile(String filePath, List<String> barcodes) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        for (String barcode : barcodes) {
            writer.write(barcode);
            writer.newLine();
        }
        writer.close();
    }
}
