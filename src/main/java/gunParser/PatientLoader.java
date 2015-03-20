package gunParser;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Random;


public enum PatientLoader
{

    instance;

    private WebDriver driver;

    private void initializeDriver()
    {
        String proxy = "88.150.136.178:3128";
        Proxy p = new Proxy();
        p.setHttpProxy(proxy).setFtpProxy(proxy).setSslProxy(proxy);
        DesiredCapabilities cap = DesiredCapabilities.phantomjs();
        cap.setCapability(CapabilityType.PROXY, p);
        cap.setCapability("phantomjs.binary.path", "phantomjs.exe");
        driver = new PhantomJSDriver(cap);
    }

    public WebDriver loadUrlWithWebDriver(String url)
    {
        if (driver == null)
        {
            initializeDriver();
        }

        int randomNum = (int) (Math.random() * 5) + 5;
        int delay = 700 + randomNum * 150;
        try
        {
            Thread.sleep(delay);
        }
        catch (Exception e)
        {
            //suppress
        }
        driver.get(url);
        return driver;
    }

}
