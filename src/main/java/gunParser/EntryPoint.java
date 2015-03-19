package gunParser;


import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Map;


public class EntryPoint {

    public static void main(String[] args) {

        String url = "http://gunaccessorysupply.com/optics/binoculars.html";

        try {
            System.setProperty("http.proxyHost", "88.150.136.178");
            System.setProperty("http.proxyPort", "3128");
            String login = "jeff@survivalhour.com";
            String pass = "Tar1234";
            //String login = "fgf@fsdf.ru";
            //String pass = "fgfgsdfd";

            String mainUrl = "http://gunaccessorysupply.com";

            Connection.Response mainResponse = PatientLoader.instance.createClientLikeConnection(mainUrl).execute();
            Document mainDoc = mainResponse.parse();

            Element loginElem = mainDoc.select("a[title=Log In]").first();
            String loginUrl = loginElem.attr("href");
            Map<String, String> cookies = mainResponse.cookies();
            Connection.Response loginPageResponse = PatientLoader.instance.createClientLikeConnection(loginUrl).cookies(cookies).execute();
            Document loginPageDoc = loginPageResponse.parse();
            Element loginFormElem = loginPageDoc.select("form[id=login-form]").first();
            String loginPostUrl = loginFormElem.attr("action");
            String formKey = loginPageDoc.select("input[name=form_key]").first().val();

            Connection.Response loginPostResponse = PatientLoader.instance.createClientLikeConnection(loginPostUrl)
                    .cookies(loginPageResponse.cookies()).method(Connection.Method.POST)
                    .data("login[username]", login).data("login[password]", pass).data("form_key", formKey)
                    .referrer(loginPostUrl)
                    .followRedirects(false).execute();

            PatientLoader.instance.setAuthCookies(loginPostResponse.cookies());

        } catch (Exception e) {
            e.printStackTrace();
        }


            CategoryParser.instance.parseCategory(url);

    }
}
