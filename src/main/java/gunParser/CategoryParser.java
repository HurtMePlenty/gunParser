package gunParser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

enum CategoryParser
{
    instance;

    public void parseCategory(String url)
    {
        try
        {
            WebDriver categoryPage = PatientLoader.instance.loadUrlWithWebDriver(url);
            WebElement lastPageElem = WebHelper.findElementByCssSelector(categoryPage, "a.last");
            if (lastPageElem != null)
            {
                String lastPageText = lastPageElem.getText();
                int lastPage = Integer.parseInt(lastPageText);
                for (int i = 1; i <= lastPage; i++)
                {
                    CategoryPageParser.instance.parseCategoryPage(url, i);
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
