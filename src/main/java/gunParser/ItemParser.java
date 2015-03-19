package gunParser;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

enum ItemParser
{

    instance;

    public Item parseItem(String url)
    {
        WebDriver itemPage = PatientLoader.instance.loadUrlWithWebDriver(url);

        Item item = new Item();

        WebElement productNameElem = WebHelper.findElementByCssSelector(itemPage, "div.product-name");
        if (productNameElem != null)
        {
            item.productName = productNameElem.getText();
        }

        List<WebElement> skuElem = WebHelper.findElementsByCssSelector(itemPage, "div.sku");
        if (skuElem.size() > 1)
        {
            item.SKU = skuElem.get(1).getText();
        }

        List<WebElement> upcElem = WebHelper.findElementsByCssSelector(itemPage, "div.sku");
        if (upcElem.size() > 2)
        {
            item.UPC = upcElem.get(2).getText();
        }

        List<WebElement> brandNameElem = WebHelper.findElementsByCssSelector(itemPage, "div.sku");
        if (brandNameElem.size() > 3)
        {
            item.brandName = brandNameElem.get(3).getText();
        }

        List<WebElement> categoryElem = WebHelper.findElementsByCssSelector(itemPage, "div.breadcrumbs a");
        if (categoryElem.size() > 0)
        {
            item.category = categoryElem.get(categoryElem.size() - 1).getText();
        }

        WebElement imageElem = WebHelper.findElementByCssSelector(itemPage, "a.cloud-zoom");
        if (imageElem != null)
        {
            item.imageUrl = imageElem.getAttribute("href");
        }

        WebElement priceElem = WebHelper.findElementByCssSelector(itemPage, "div.product-type-data span.price");
        if (priceElem != null)
        {
            item.price = priceElem.getText().replace("$", "");
        }

        WebElement descrElem = WebHelper.findElementByCssSelector(itemPage, "div.box-additional.box-tabs div.panel");
        if (descrElem != null)
        {
            item.description = descrElem.getAttribute("innerHTML");
        }

        return item;
    }

}
