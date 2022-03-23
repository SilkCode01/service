package ro.unibuc.hello;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class GetData {

    private static String apiKey = "9a3453f0-954f-4b35-89c0-629c868b146a";

    public static void main(String[] args) {


//        String uri = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/category";
        String uri = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("start","1"));
        parameters.add(new BasicNameValuePair("limit","10"));
        parameters.add(new BasicNameValuePair("convert","USD"));
        parameters.add(new BasicNameValuePair("sort","price"));
        parameters.add(new BasicNameValuePair("sort_dir","desc"));
        parameters.add(new BasicNameValuePair("cryptocurrency_type","coins"));
        parameters.add(new BasicNameValuePair("price_max","3000"));



        try {
            String result = makeAPICall(uri, parameters);

            JSONParser parser = new JSONParser();
            JSONObject parsed_result = (JSONObject) parser.parse(result);
            JSONArray data = (JSONArray) parsed_result.get("data");
            System.out.println(data);
            System.out.println(parsed_result.keySet());

            for (int i = 0; i < data.size(); i++){
                JSONObject symbol = (JSONObject) data.get(i);
                JSONObject quote = (JSONObject) symbol.get("quote");
                JSONObject usd = (JSONObject) quote.get("USD");
                System.out.print(symbol.get("name"));
                System.out.print(": ");
                System.out.print(usd.get("price"));
                System.out.print(" USD");
                System.out.println(" ");
            }


        } catch (IOException e) {
            System.out.println("Error: cannont access content - " + e.toString());
        } catch (URISyntaxException e) {
            System.out.println("Error: Invalid URL " + e.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static String makeAPICall(String uri, List<NameValuePair> parameters)
            throws URISyntaxException, IOException {
        String response_content = "";

        URIBuilder query = new URIBuilder(uri);
        query.addParameters(parameters);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(query.build());

        request.setHeader(HttpHeaders.ACCEPT, "application/json");
        request.addHeader("X-CMC_PRO_API_KEY", apiKey);

        CloseableHttpResponse response = client.execute(request);

        try {
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            response_content = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }

        return response_content;
    }

}

