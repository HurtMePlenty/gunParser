package gunParser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

enum CategoryPageParser
{

    instance;

    public List<Item> parseCategoryPage(String url, int page)
    {
        List<Item> result = new ArrayList<Item>();
        String targetUrl = String.format("%s?p=%d", url, page);
        System.out.println(String.format("Loading page %d with url = %s", page, targetUrl));
        WebDriver pageDriver = PatientLoader.instance.loadUrlWithWebDriver(targetUrl);

        List<WebElement> products = WebHelper.findElementsByCssSelector(pageDriver, "ul.products-grid a.product-image");
        System.out.println(String.format("Page loaded. Located %d items", products.size()));
        List<String> productUrls = new ArrayList<String>();
        for (WebElement product : products)
        {
            productUrls.add(product.getAttribute("href"));
        }

        for (String productUrl : productUrls)
        {
            try
            {
                Item item = ItemParser.instance.parseItem(productUrl);
                if (item != null)
                {
                    result.add(item);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        System.out.println(String.format("Page %d loaded. Total items = %d", page, result.size()));
        return result;
    }
}
