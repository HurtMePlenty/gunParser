package gunParser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

enum CategoryPageParser {

    instance;

    public void parseCategoryPage(String url, int page) {
        String targetUrl = String.format("%s?p=%d", url, page);
        Document pageDocument = PatientLoader.instance.loadUrl(targetUrl);
        Elements productsGrid = pageDocument.select("ul.products-grid");

        List<Item> result = new ArrayList<Item>();

        if (productsGrid.size() > 0) {
            Element productGrid = productsGrid.first();
            Elements products = productGrid.select("a.product-image");
            if (products.size() > 0) {
                for (Element product : products) {
                    String productUrl = product.attr("href");
                    Item item = ItemParser.instance.parseItem(productUrl);
                    if(item != null) {
                        result.add(item);
                    }
                }
            }
        }

    }
}
