package gunParser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

enum CategoryParser {
    instance;

    public void parseCategory(String url) {
        WebDriver categoryPage = PatientLoader.instance.loadUrlWithWebDriver(url);
        WebElement lastPageElem = WebHelper.findElementByCssSelector(categoryPage, "a.last");
        int lastPage = 1;
        if (lastPageElem != null) {
            String lastPageText = lastPageElem.getAttribute("innerHTML");
            lastPage = Integer.parseInt(lastPageText);
        }
        System.out.println(String.format("This category contains %d pages", lastPage));
        for (int i = 1; i <= lastPage; i++) {
            try {
                CategoryPageParser.instance.parseCategoryPage(url, i, lastPage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println(String.format("Category with url = %s parsed", url));
    }
}
