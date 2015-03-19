package gunParser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

enum CategoryPageParser
{

    instance;

    public void parseCategoryPage(String url, int page)
    {
        List<Item> result = new ArrayList<Item>();
        String targetUrl = String.format("%s?p=%d", url, page);
        WebDriver pageDriver = PatientLoader.instance.loadUrlWithWebDriver(targetUrl);

        List<WebElement> products = WebHelper.findElementsByCssSelector(pageDriver, "ul.products-grid a.product-image");
        for (WebElement product : products)
        {
            String productUrl = product.getAttribute("href");
            Item item = ItemParser.instance.parseItem(productUrl);
            if (item != null)
            {
                result.add(item);
            }
        }
    }
}
