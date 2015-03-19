package gunParser;


import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

enum ItemParser {

    instance;

    public Item parseItem(String url) {
        Document itemDoc = PatientLoader.instance.loadUrl(url);

        Item item = new Item();
        Elements productNameElems = itemDoc.select("div.product-name");
        if (!productNameElems.isEmpty()) {
            Element productNameElem = productNameElems.first();
            item.productName = productNameElem.text();
        }

        Elements skuElems = itemDoc.select("div.sku");
        if (!skuElems.isEmpty()) {
            Element skuElem = skuElems.get(1);
            item.SKU = skuElem.ownText();
        }

        Elements upcElems = itemDoc.select("div.sku");
        if (!upcElems.isEmpty()) {
            Element upcElem = upcElems.get(2);
            item.UPC = upcElem.ownText();
        }

        Elements brandNamesElems = itemDoc.select("div.sku");
        if (!brandNamesElems.isEmpty()) {
            Element brandNamesElem = brandNamesElems.get(3);
            item.brandName = brandNamesElem.ownText();
        }

        Elements categoryElems = itemDoc.select("div.breadcrumbs a");
        if (!categoryElems.isEmpty()) {
            Element categoryElem = categoryElems.last();
            item.category = categoryElem.ownText();
        }

        Elements imageUrlElems = itemDoc.select("a.cloud-zoom");
        if (!imageUrlElems.isEmpty()) {
            Element imageUrlElem = imageUrlElems.get(0);
            item.imageUrl = imageUrlElem.attr("href");
        }

        Elements priceElems = itemDoc.select("div.product-type-data span.price");
        if (!priceElems.isEmpty()) {
            Element priceElem = priceElems.get(0);
            item.price = priceElem.ownText().replace("$", "");
        }

        Elements descriptionElems = itemDoc.select("div.box-additional.box-tabs div.panel");
        if (!descriptionElems.isEmpty()) {
            Element descriptionElem = descriptionElems.get(0);
            item.description = descriptionElem.html();
        }
        return item;
    }

}
