package gunParser;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

enum MainExecutor
{
    instance;

    public void run()
    {
        //authorize();
        collectData();
    }

    private void authorize()
    {
        try
        {
            //System.setProperty("http.proxyHost", "88.150.136.178");
            //System.setProperty("http.proxyPort", "3128");
            String login = "jeff@survivalhour.com";
            String pass = "Tar1234";

            WebDriver driver = PatientLoader.instance.loadUrlWithWebDriver("http://gunaccessorysupply.com");
            WebElement element = driver.findElement(By.cssSelector("a[title=\"Log In\"]"));
            element.click();
            WebElement loginElem = driver.findElement(By.cssSelector("input[name=\"login[username]\"]"));
            loginElem.sendKeys(login);
            WebElement passElem = driver.findElement(By.cssSelector("input[name=\"login[password]\"]"));
            passElem.sendKeys(pass);
            WebElement submitElem = driver.findElement(By.cssSelector("button[id=\"send2\"]"));
            submitElem.click();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void collectData()
    {
        WebDriver mainDriver = PatientLoader.instance.loadUrlWithWebDriver("http://gunaccessorysupply.com");
        List<WebElement> categories = WebHelper.findElementsByCssSelector(mainDriver, "div.nav-container div.level0-wrapper ul.level0 a");
        List<Item> result = new ArrayList<Item>();
        System.out.println(String.format("Loaded category list - %d categories", categories.size()));
        List<String> categoryUrls = new ArrayList<String>();
        for (WebElement category : categories)
        {
            categoryUrls.add(category.getAttribute("href"));
        }

        for (String categoryUrl : categoryUrls)
        {
            System.out.println(String.format("Start parsing category with url: %s", categoryUrl));
            List<Item> categoryItems = CategoryParser.instance.parseCategory(categoryUrl);
            result.addAll(categoryItems);
        }

    }

}
