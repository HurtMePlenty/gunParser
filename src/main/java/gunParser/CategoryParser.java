package gunParser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

enum CategoryParser {
    instance;

    public void parseCategory(String url) {
        try {
            Document categoryDoc = PatientLoader.instance.loadUrl(url);
            Elements lastPageIndex = categoryDoc.select("a.last");
            if (lastPageIndex.size() > 0) {
                Element lastPageElem = lastPageIndex.first();
                String lastPageText = lastPageElem.text();
                int lastPage = Integer.parseInt(lastPageText);

                for(int i = 1; i <= lastPage; i++){
                    CategoryPageParser.instance.parseCategoryPage(url, i);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
