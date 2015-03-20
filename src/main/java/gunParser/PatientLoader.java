package gunParser;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;


public enum PatientLoader {

    instance;

    private WebDriver driver;

    private void initializeDriver() {
        String proxy = "88.150.136.178:3128";
        Proxy p = new Proxy();
        p.setHttpProxy(proxy).setFtpProxy(proxy).setSslProxy(proxy);
        DesiredCapabilities cap = DesiredCapabilities.phantomjs();
        cap.setCapability(CapabilityType.PROXY, p);
        cap.setCapability("phantomjs.binary.path", "phantomjs");
        driver = new PhantomJSDriver(cap);
    }

    public WebDriver loadUrlWithWebDriver(String url) {
        if (driver == null) {
            initializeDriver();
        }
        driver.get(url);
        return driver;
    }

}
