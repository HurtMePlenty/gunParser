package gunParser;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;
import java.util.Random;
import java.util.Set;

public enum PatientLoader
{

    instance;

    //private Map<String, String> authCookies;

    private Set<Cookie> authCookies;


    public WebDriver loadUrlWithWebDriver(String url)
    {
        String proxy = "88.150.136.178:3128";
        Proxy p = new Proxy();
        p.setHttpProxy(proxy).setFtpProxy(proxy).setSslProxy(proxy);
        DesiredCapabilities cap = DesiredCapabilities.phantomjs();
        cap.setCapability(CapabilityType.PROXY, p);
        cap.setCapability("phantomjs.binary.path", "phantomjs.exe");
        WebDriver driver = new PhantomJSDriver(cap);
        if (authCookies != null)
        {
            for (Cookie cookie : authCookies)
            {
                driver.manage().addCookie(cookie);
            }
        }
        driver.get(url);
        return driver;
    }

    public void setAuthCookies(Set<Cookie> cookies)
    {
        this.authCookies = cookies;
    }


    /*
    public Document loadUrl(String url)
    {
        try
        {
            Random rand = new Random();
            int randomNum = rand.nextInt(5) + 5;
            long randomDelay = 700 + randomNum * 150;
            Thread.sleep(randomDelay);
            Connection connection = createClientLikeConnection(url);

            Connection.Response response = connection.execute();
            //authCookies = response.cookies();
            Document result = response.parse();

            return result;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    } */
}
