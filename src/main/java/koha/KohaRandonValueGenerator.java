package koha;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class KohaRandonValueGenerator {
    public static String generateCardNumber(String libraryName) {
        String initials = libraryName.replaceAll("\\B.|\\s", "").toUpperCase();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return initials + "_" + sdf.format(new Date());
    }

    public static String generateRandomName() {
        String[] names = {"John", "Jane", "Alice", "Bob", "Charlie"};
        return names[new Random().nextInt(names.length)];
    }

    public static String generateRandomEmail(String name) {
        return name.toLowerCase() + "@example.com";
    }
}

