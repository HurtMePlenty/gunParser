package gunParser;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Map;
import java.util.Random;

public enum PatientLoader {

    instance;

    private Map<String, String> authCookies;

    public Document loadUrl(String url) {
        try {
            Random rand = new Random();
            int randomNum = rand.nextInt(5) + 5;
            long randomDelay = 700 + randomNum * 150;
            Thread.sleep(randomDelay);
            Connection connection = createClientLikeConnection(url);

            Connection.Response response = connection.execute();
            //authCookies = response.cookies();
            Document result = response.parse();

            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void setAuthCookies(Map<String, String> cookies) {
        this.authCookies = cookies;
    }

    public Connection createClientLikeConnection(String url){
        Connection connection = Jsoup.connect(url);
        if(authCookies != null) {
            connection.cookies(authCookies);
        }
        connection.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        connection.header("Accept-Encoding", "gzip, deflate, sdch");
        connection.header("Accept-Language", "en-US,en;q=0.8,ru;q=0.6");
        connection.header("Cache-Control", "no-cache");
        connection.header("Connection", "keep-alive");
        connection.header("Host", "gunaccessorysupply.com");
        connection.header("Pragma", "no-cache");
        connection.header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.89 Safari/537.36");
        return connection;
    }

}
