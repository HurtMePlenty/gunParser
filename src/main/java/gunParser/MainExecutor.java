package gunParser;

import com.google.common.io.Files;
import org.apache.commons.io.Charsets;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

enum MainExecutor {
    instance;

    private List<String> loadedUrls = new ArrayList<String>();
    File progressFile;

    public void run() {

        progressFile = new File("progress.txt");
        if (!progressFile.exists()) {
            try {
                progressFile.createNewFile();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                loadedUrls = Files.readLines(progressFile, Charsets.UTF_8);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            File configFile = new File("config.txt");

            List<String> configLines = Files.readLines(configFile, Charsets.UTF_8);
            for (String configLine : configLines) {
                String[] parts = configLine.split("=");
                if (parts[0].trim().equals("useSeveralThreads")) {
                    Config.instance.useSeveralThreads(Boolean.valueOf(parts[1].trim()));
                }

                if (parts[0].trim().equals("shouldMakeDelays")) {
                    Config.instance.shouldMakeDelays(Boolean.valueOf(parts[1].trim()));
                }

                if (parts[0].trim().equals("proxy")) {
                    Config.instance.proxy(parts[1]);
                }

            }
        } catch (Exception e) {
            System.out.println("----   WARNING!!! WAS UNABLE TO LOAD CONFIG FILE. USING DEFAULT CONFIG  ----");
            e.printStackTrace();
        }

        if (Config.instance.isUseSeveralThreads()) {
            authorizeLoader(PatientLoader.instance);
            authorizeLoader(PatientLoader.instance2);
            authorizeLoader(PatientLoader.instance3);
            authorizeLoader(PatientLoader.instance4);

        } else {
            authorizeLoader(PatientLoader.instance);
        }

        collectData();
    }

    private void authorizeLoader(PatientLoader loader) {
        try {
            //System.setProperty("http.proxyHost", "88.150.136.178");
            //System.setProperty("http.proxyPort", "3128");
            String login = "jeff@survivalhour.com";
            String pass = "Tar1234";

            WebDriver driver = loader.loadUrlWithWebDriver("http://gunaccessorysupply.com");
            WebElement element = driver.findElement(By.cssSelector("a[title=\"Log In\"]"));
            element.click();
            WebElement loginElem = driver.findElement(By.cssSelector("input[name=\"login[username]\"]"));
            loginElem.sendKeys(login);
            WebElement passElem = driver.findElement(By.cssSelector("input[name=\"login[password]\"]"));
            passElem.sendKeys(pass);
            WebElement submitElem = driver.findElement(By.cssSelector("button[id=\"send2\"]"));
            submitElem.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void collectData() {
        WebDriver mainDriver = PatientLoader.instance.loadUrlWithWebDriver("http://gunaccessorysupply.com");
        List<WebElement> categories = WebHelper.findElementsByCssSelector(mainDriver, "div.nav-container div.level0-wrapper ul.level0 a");
        System.out.println(String.format("Loaded category list - %d categories", categories.size()));
        List<String> categoryUrls = new ArrayList<String>();
        for (WebElement category : categories) {
            categoryUrls.add(category.getAttribute("href"));
        }

        for (String categoryUrl : categoryUrls) {
            if (loadedUrls.contains(categoryUrl)) {
                System.out.println(String.format("Category with url %s is skipped - it was already loaded", categoryUrl));
            }

            System.out.println(String.format("Start parsing category with url: %s", categoryUrl));
            List<Item> categoryItems = CategoryParser.instance.parseCategory(categoryUrl);
            CsvBuilder.instance.writeItems(categoryItems);
            addProgress(categoryUrl);
        }
    }

    private void addProgress(String categoryUrl) {
        try {
            if (loadedUrls.isEmpty()) {
                Files.append(categoryUrl, progressFile, Charsets.UTF_8);
            } else {
                Files.append("\n" + categoryUrl, progressFile, Charsets.UTF_8);
            }
            loadedUrls.add(categoryUrl);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
