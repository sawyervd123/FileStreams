import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;

public class Product implements Serializable {
    private final String name;
    private final String description;
    private final String ID;
    private final double cost;

    public Product(String name, String description, String ID, double cost) {
        this.name = formatString(name, 35);
        this.description = formatString(description, 75);
        this.ID = formatString(ID, 6);
        this.cost = cost;
    }

    public static String formatString(String str, int length) {
        if (str.length() > length) return str.substring(0, length);
        return String.format("%-" + length + "s", str);
    }

    public String getName() { return name.trim(); }
    public String getDescription() { return description.trim(); }
    public String getID() { return ID.trim(); }
    public double getCost() { return cost; }

    public void writeToFile(RandomAccessFile file) throws IOException, IOException {
        file.writeChars(name);         // 35 chars
        file.writeChars(description);  // 75 chars
        file.writeChars(ID);           // 6 chars
        file.writeDouble(cost);        // 8 bytes
    }

    public static int getRecordSize() {
        return (2 * (35 + 75 + 6)) + 8; // 2 bytes per char + 8 for double
    }
}
