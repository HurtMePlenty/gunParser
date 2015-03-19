package gunParser;


import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;
import java.util.Set;


public class EntryPoint
{

    public static void main(String[] args)
    {

        String url = "http://gunaccessorysupply.com/optics/binoculars.html";

        try
        {
            //System.setProperty("http.proxyHost", "88.150.136.178");
            //System.setProperty("http.proxyPort", "3128");
            String login = "jeff@survivalhour.com";
            String pass = "Tar1234";
            //String login = "fgf@fsdf.ru";
            //String pass = "fgfgsdfd";



            WebDriver driver = PatientLoader.instance.loadUrlWithWebDriver("http://gunaccessorysupply.com");
            WebElement element = driver.findElement(By.cssSelector("a[title=\"Log In\"]"));
            element.click();
            WebElement loginElem = driver.findElement(By.cssSelector("input[name=\"login[username]\"]"));
            loginElem.sendKeys(login);
            WebElement passElem = driver.findElement(By.cssSelector("input[name=\"login[password]\"]"));
            passElem.sendKeys(pass);
            WebElement submitElem = driver.findElement(By.cssSelector("button[id=\"send2\"]"));
            submitElem.click();
            Set<Cookie> cookieSet = driver.manage().getCookies();
            PatientLoader.instance.setAuthCookies(cookieSet);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        CategoryParser.instance.parseCategory(url);
    }
}
