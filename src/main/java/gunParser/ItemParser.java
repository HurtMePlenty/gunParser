package gunParser;


import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;

enum ItemParser
{

    instance;

    public Item parseItem(String url, PatientLoader loader)
    {
        System.out.println(String.format("Loading item with url = %s", url));
        WebDriver itemPage = loader.loadUrlWithWebDriver(url);

        Item item = new Item();

        WebElement productNameElem = WebHelper.findElementByCssSelector(itemPage, "div.product-name");
        if (productNameElem != null)
        {
            item.productName = productNameElem.getText();
        }

        List<WebElement> skuElem = WebHelper.findElementsByCssSelector(itemPage, "div.sku");
        if (skuElem.size() > 2)
        {
            item.SKU = skuElem.get(2).getAttribute("innerHTML").replaceAll("<.*>", "");
        }

        List<WebElement> upcElem = WebHelper.findElementsByCssSelector(itemPage, "div.sku");
        if (upcElem.size() > 3)
        {
            item.UPC = upcElem.get(3).getAttribute("innerHTML").replaceAll("<.*>", "");
        }

        List<WebElement> brandNameElem = WebHelper.findElementsByCssSelector(itemPage, "div.sku");
        if (brandNameElem.size() > 4)
        {
            item.brandName = brandNameElem.get(4).getAttribute("innerHTML").replaceAll("<.*>", "");
        }

        List<WebElement> categoryElem = WebHelper.findElementsByCssSelector(itemPage, "div.breadcrumbs a");
        if (categoryElem.size() > 0)
        {
            item.category = categoryElem.get(categoryElem.size() - 1).getAttribute("innerHTML").replaceAll("<.*>", "");
        }

        WebElement imageElem = WebHelper.findElementByCssSelector(itemPage, "a.cloud-zoom");
        if (imageElem != null)
        {
            String imageURL = imageElem.getAttribute("href");
            if(!StringUtils.isEmpty(imageURL)) {
                String[] parts = imageURL.split("/");
                if(parts.length > 0) {
                    item.imageName = parts[parts.length - 1];
                    try {
                        URL imgURL = new URL(imageURL);
                        item.image = ImageIO.read(imgURL);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        WebElement priceElem = WebHelper.findElementByCssSelector(itemPage, "div.product-type-data span.price");
        if (priceElem != null)
        {
            item.price = priceElem.getAttribute("innerHTML").replaceAll("<.*>", "").replace("$", "").replace(".", ",");
        }

        WebElement descrElem = WebHelper.findElementByCssSelector(itemPage, "div.box-additional.box-tabs div.panel");
        if (descrElem != null)
        {
            item.description = descrElem.getAttribute("innerHTML");
        }

        return item;
    }

}
