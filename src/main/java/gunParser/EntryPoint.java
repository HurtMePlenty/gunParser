package gunParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class EntryPoint {

    public static void main(String[] args) {
        System.setProperty("http.proxyHost", "88.150.136.178");
        System.setProperty("http.proxyPort", "3128");
        String url = "http://gunaccessorysupply.com/optics/binoculars.html";

        try {
            Document doc = Jsoup.connect(url).get();
            Elements manufacturers = doc.select("a[href*=?manufacturer]");
            int a = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
