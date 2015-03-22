package gunParser;

import com.google.common.collect.Sets;
import com.google.common.io.Files;
import org.apache.commons.io.Charsets;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

enum ProgressInfo {
    instance;

    Set<String> loadedItems = new HashSet<String>();
    File progressFile;
    private final String imageDirPath = "images";

    ProgressInfo() {

        progressFile = new File("progress.txt");
        if (!progressFile.exists()) {
            try {
                progressFile.createNewFile();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                loadedItems = Sets.newHashSet(Files.readLines(progressFile, Charsets.UTF_8));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        File imageDir = new File(imageDirPath);
        if (!imageDir.exists()) {
            imageDir.mkdir();
        }
    }

    public boolean isAlreadyLoaded(String url) {
        return loadedItems.contains(url);
    }

    public synchronized void writeItem(Item item, String url) {
        CsvBuilder.instance.writeItem(item);
        if (!StringUtils.isEmpty(item.imageName) && item.image != null) {
            writeImage(item.imageName, item.image);
        }
        addProgress(url);
    }

    public synchronized void addProgress(String url) {
        try {
            if (loadedItems.size() > 0) {
                Files.append("\n" + url, progressFile, Charsets.UTF_8);
            } else {
                Files.append(url, progressFile, Charsets.UTF_8);
            }
            loadedItems.add(url);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void writeImage(String imageName, BufferedImage image) {
        try {
            String imagePath = Paths.get(imageDirPath, imageName).toString();
            File imageFile = new File(imagePath);
            ImageIO.write(image, "JPEG", imageFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
