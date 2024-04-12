import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.json.JSONObject;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.Scanner;

public class Scraper {
    private static Scanner keyboard = new Scanner(System.in);

    public static void main (String[] args){
        String targetcoin1;
        String targetcoin2;
        double price1 = 0.0;
        double price2 = 0.0;
        double ratioprice = 0.0;
        System.out.println("What coin are you looking for:");
        targetcoin1 = keyboard.next().toLowerCase();
        price1 = PriceFetcher(targetcoin1);
        System.out.println("What coin do you want to compaire to:");
        targetcoin2=  keyboard.next().toLowerCase();
        price2 = PriceCompairer(targetcoin2);
        ratioprice=price1/price2;
        targetcoin1=targetcoin1.toUpperCase();
        targetcoin2=targetcoin2.toUpperCase();
        System.out.println("One "+targetcoin1+" is currently at "+ratioprice+" "+targetcoin2);
    }

    public static double PriceFetcher(String targetcoin1) {
        double price = 0.0;
        String url = "https://www.coingecko.com/en/coins/"+targetcoin1;

        targetcoin1=targetcoin1.toUpperCase();
        try {
            Document doc = Jsoup.connect(url).get();

            Elements scriptElements = doc.select("script[type=application/ld+json]");

            if (scriptElements.size() >= 2) {
                Element secondScriptElement = scriptElements.get(1);
                String jsonText = secondScriptElement.html();
                JSONObject json = new JSONObject(jsonText);
                price = json.getJSONObject("currentExchangeRate").getDouble("price");
                System.out.println(targetcoin1+" price to USD$: " + price);
            } else {
                System.out.println("The script tag containing the "+ targetcoin1 +" price information could not be found.");
            }


        } catch (IOException e) {
            System.err.println("An error occurred while trying to connect to the website: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An error occurred while processing the "+targetcoin1+" price information: " + e.getMessage());
        }
        return price;
    }
    public static double PriceCompairer(String targetcoin2) {
        double price = 0.0;
        String url = "https://www.coingecko.com/en/coins/"+targetcoin2;

        targetcoin2=targetcoin2.toUpperCase();
        try {
            Document doc = Jsoup.connect(url).get();

            Elements scriptElements = doc.select("script[type=application/ld+json]");

            if (scriptElements.size() >= 2) {
                Element secondScriptElement = scriptElements.get(1);
                String jsonText = secondScriptElement.html();
                JSONObject json = new JSONObject(jsonText);
                price = json.getJSONObject("currentExchangeRate").getDouble("price");
            } else {
                System.out.println("The script tag containing the "+ targetcoin2 +" price information could not be found.");
            }


        } catch (IOException e) {
            System.err.println("An error occurred while trying to connect to the website: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An error occurred while processing the "+targetcoin2+" price information: " + e.getMessage());
        }
        return price;
    }
}
