package gunParser;

import com.google.common.collect.Lists;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class WebHelper
{
    public static WebElement findElementByCssSelector(WebDriver webDriver, String cssSelector)
    {
        WebElement webElement;
        try
        {
            webElement = webDriver.findElement(By.cssSelector(cssSelector));
            return webElement;
        }
        catch (NoSuchElementException e)
        {
            return null;
        }
    }

    public static List<WebElement> findElementsByCssSelector(WebDriver webDriver, String cssSelector)
    {
        List<WebElement> webElementList;
        try
        {
            webElementList = webDriver.findElements(By.cssSelector(cssSelector));
            return webElementList;
        }
        catch (NoSuchElementException e)
        {
            return Lists.newArrayList();
        }
    }
}
