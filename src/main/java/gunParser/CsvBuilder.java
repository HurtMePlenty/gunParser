package gunParser;


import com.google.common.io.Files;
import org.apache.commons.io.Charsets;

import java.io.File;
import java.util.List;

enum CsvBuilder {
    instance;

    private final String resultFile = "output.csv";
    private final String separator = ";";

    public void writeItems(List<Item> itemsList) {
        StringBuilder builder = new StringBuilder();
        File file = new File(resultFile);
        try {
            if (!file.exists()) {
                file.createNewFile();

                builder.append("Product Name");
                builder.append(separator);
                builder.append("Product Code/SKU");
                builder.append(separator);
                builder.append("Brand Name");
                builder.append(separator);
                builder.append("Price");
                builder.append(separator);
                builder.append("Category");
                builder.append(separator);
                builder.append("Image");
                builder.append(separator);
                builder.append("UPC");
                builder.append(separator);
                builder.append("Product Description");
                builder.append("\n");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        for (Item item : itemsList) {
            builder.append(String.format("\"%s\"", item.productName.replace("\"", "")));
            builder.append(separator);
            builder.append(item.SKU);
            builder.append(separator);
            builder.append(item.brandName);
            builder.append(separator);
            builder.append(item.price);
            builder.append(separator);
            builder.append(item.category);
            builder.append(separator);
            builder.append(item.imageUrl);
            builder.append(separator);
            builder.append(item.UPC);
            builder.append(separator);
            if(item.description != null) {
                builder.append(String.format("\"%s\"", item.description.replace("\n", "")));
            } else {
                builder.append("");
            }
            builder.append("\n");
        }
        try {
            Files.append(builder.toString().replace("&amp;", "&").replace("&nbsp;", " "), file, Charsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
