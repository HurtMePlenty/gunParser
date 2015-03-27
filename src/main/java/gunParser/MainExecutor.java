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


    public void run() {

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
            String login = "xxxx";
            String pass = "xxxx";

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
            if (ProgressInfo.instance.isAlreadyLoaded(categoryUrl)) {
                System.out.println(String.format("Category with url %s is skipped - it was already loaded", categoryUrl));
                continue;
            }

            System.out.println(String.format("Start parsing category with url: %s", categoryUrl));
            CategoryParser.instance.parseCategory(categoryUrl);
            ProgressInfo.instance.addProgress(categoryUrl);
        }
    }
}
