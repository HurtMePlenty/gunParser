package gunParser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

enum CategoryPageParser {

    instance;

    public void parseCategoryPage(String url, int page, int lastPage) {
        String targetUrl = String.format("%s?p=%d", url, page);
        if (ProgressInfo.instance.isAlreadyLoaded(targetUrl)) {
            System.out.println(String.format("Page with url = %s was already loaded", targetUrl));
            return;
        }
        System.out.println(String.format("Loading page %d/%d with url = %s", page, lastPage, targetUrl));
        WebDriver pageDriver = PatientLoader.instance.loadUrlWithWebDriver(targetUrl);

        final List<WebElement> products = WebHelper.findElementsByCssSelector(pageDriver, "ul.products-grid a.product-image");
        System.out.println(String.format("Page loaded. Located %d items", products.size()));
        final List<String> productUrls = new ArrayList<String>();
        for (WebElement product : products) {
            productUrls.add(product.getAttribute("href"));
        }

        if (Config.instance.isUseSeveralThreads()) {

            final PatientLoader[] loaders = new PatientLoader[4];
            loaders[0] = PatientLoader.instance;
            loaders[1] = PatientLoader.instance2;
            loaders[2] = PatientLoader.instance3;
            loaders[3] = PatientLoader.instance4;

            ExecutorService es = Executors.newCachedThreadPool();
            for (int i = 0; i < 4; i++) {
                final int finalI = i;
                es.execute(new Runnable() {
                    @Override
                    public void run() {
                        PatientLoader loader = loaders[finalI];
                        for (String productUrl : productUrls) {
                            if (productUrls.indexOf(productUrl) % 4 == finalI) {
                                if (ProgressInfo.instance.isAlreadyLoaded(productUrl)) {
                                    System.out.println(String.format("Product with url = %s was already loaded", productUrl));
                                    continue;
                                }
                                try {
                                    Item item = ItemParser.instance.parseItem(productUrl, loader);
                                    if (item != null) {
                                        ProgressInfo.instance.writeItem(item, productUrl);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });
            }

            es.shutdown();
            try {
                es.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } else {

            for (String productUrl : productUrls) {
                if (ProgressInfo.instance.isAlreadyLoaded(productUrl)) {
                    System.out.println(String.format("Product with url = %s was already loaded", productUrl));
                    continue;
                }
                try {
                    Item item = ItemParser.instance.parseItem(productUrl, PatientLoader.instance);
                    if (item != null) {
                        ProgressInfo.instance.writeItem(item, productUrl);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println(String.format("Page %d loaded.", page));
    }
}
