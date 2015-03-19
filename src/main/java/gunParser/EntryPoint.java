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


public class EntryPoint {

    public static void main(String[] args) {

        String url = "http://gunaccessorysupply.com/optics/binoculars.html";

        try {
            System.setProperty("http.proxyHost", "88.150.136.178");
            System.setProperty("http.proxyPort", "3128");
            String login = "jeff@survivalhour.com";
            String pass = "Tar1234";
            //String login = "fgf@fsdf.ru";
            //String pass = "fgfgsdfd";

            String proxy = "88.150.136.178:3128";
            Proxy p = new Proxy();
            p.setHttpProxy(proxy).setFtpProxy(proxy).setSslProxy(proxy);
            DesiredCapabilities cap = DesiredCapabilities.phantomjs();
            cap.setCapability(CapabilityType.PROXY, p);
            cap.setCapability("phantomjs.binary.path", "phantomjs.exe");
            PhantomJSDriver driver = new PhantomJSDriver(cap);

            driver.get("http://gunaccessorysupply.com");
            WebElement element = driver.findElement(By.cssSelector("a[title=\"Log In\"]"));
            element.click();
            WebElement loginElem = driver.findElement(By.cssSelector("input[name=\"login[username]\"]"));
            loginElem.sendKeys(login);
            WebElement passElem = driver.findElement(By.cssSelector("input[name=\"login[password]\"]"));
            passElem.sendKeys(pass);
            WebElement submitElem = driver.findElement(By.cssSelector("button[id=\"send2\"]"));
            submitElem.click();
            Set<Cookie> cookieSet = driver.manage().getCookies();

            /*String mainUrl = "http://gunaccessorysupply.com";

            Connection.Response mainResponse = PatientLoader.instance.createClientLikeConnection(mainUrl).execute();
            Document mainDoc = mainResponse.parse();

            Element loginElem = mainDoc.select("a[title=Log In]").first();
            String loginUrl = loginElem.attr("href");
            Map<String, String> cookies = mainResponse.cookies();
            Thread.sleep(1300);
            Connection.Response loginPageResponse = PatientLoader.instance.createClientLikeConnection(loginUrl).cookies(cookies).execute();
            Document loginPageDoc = loginPageResponse.parse();
            Element loginFormElem = loginPageDoc.select("form[id=login-form]").first();
            String loginPostUrl = loginFormElem.attr("action");
            String formKey = loginPageDoc.select("input[name=form_key]").first().val();

            Thread.sleep(1100);
            PatientLoader.instance.setAuthCookies(loginPageResponse.cookies());
            Connection.Response loginPostResponse = PatientLoader.instance.createClientLikeConnection(loginPostUrl)
                    .cookies(loginPageResponse.cookies()).method(Connection.Method.POST)
                    .data("form_key1", formKey).data("login[username]", login).data("login[password]", pass).data("send","")
                    .followRedirects(false).referrer(loginPostUrl).execute();

            PatientLoader.instance.setAuthCookies(loginPostResponse.cookies());
                                        */

        } catch (Exception e) {
            e.printStackTrace();
        }


           // CategoryParser.instance.parseCategory(url);

    }
}
