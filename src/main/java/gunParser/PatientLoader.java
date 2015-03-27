package gunParser;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Random;


public enum PatientLoader {

    instance,
    instance2,
    instance3,
    instance4;

    private WebDriver driver;

    private void initializeDriver() {
        DesiredCapabilities cap = DesiredCapabilities.phantomjs();
        String proxyStr = Config.instance.getProxy();
        if (!StringUtils.isEmpty(proxyStr)) {
            System.setProperty("http.proxyHost", proxyStr.split(":")[0]);
            System.setProperty("http.proxyPort", proxyStr.split(":")[1]);
            String proxy = Config.instance.getProxy();
            Proxy p = new Proxy();
            p.setHttpProxy(proxy).setFtpProxy(proxy).setSslProxy(proxy);
            cap.setCapability(CapabilityType.PROXY, p);
        }
        cap.setCapability("phantomjs.binary.path", "phantomjs.exe");
        driver = new PhantomJSDriver(cap);
    }

    public WebDriver loadUrlWithWebDriver(String url) {
        if (driver == null) {
            initializeDriver();
        }

        if (Config.instance.isShouldMakeDelays()) {
            int randomNum = (int) (Math.random() * 5) + 5;
            int delay = 700 + randomNum * 150;
            try {
                Thread.sleep(delay);
            } catch (Exception e) {
                //suppress
            }
        }

        driver.get(url);
        return driver;
    }

}
