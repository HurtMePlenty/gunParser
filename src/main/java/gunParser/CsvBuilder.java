package gunParser;


import com.google.common.io.Files;
import org.apache.commons.io.Charsets;

import java.io.File;

enum CsvBuilder {
    instance;

    private final String resultFile = "output.csv";
    private final String separator = ";";

    public void writeItem(Item item) {
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

        appendSafe(builder, item.productName);
        builder.append(separator);
        appendSafe(builder, item.SKU);
        builder.append(separator);
        appendSafe(builder, item.brandName);
        builder.append(separator);
        appendSafe(builder, item.price);
        builder.append(separator);
        appendSafe(builder, item.category);
        builder.append(separator);
        appendSafe(builder, item.imageName);
        builder.append(separator);
        appendSafe(builder, item.UPC);
        builder.append(separator);
        if (item.description != null) {
            appendSafe(builder, item.description);
        } else {
            builder.append("");
        }
        builder.append("\n");

        try

        {
            Files.append(builder.toString().replace("&amp;", "&").replace("&nbsp;", " "), file, Charsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void appendSafe(StringBuilder builder, String data) {
        if (data != null) {
            builder.append(data.replace("&amp;", "&").replace("&nbsp;", " ").replace(separator,"").replace("\n", ""));
        }
    }

}
