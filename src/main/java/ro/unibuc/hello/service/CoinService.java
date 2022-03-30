package ro.unibuc.hello.service;
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
import org.springframework.stereotype.Service;

@Service
public class CoinService {

    private final String apiKey = "9a3453f0-954f-4b35-89c0-629c868b146a";
    public String makeAPICall(String uri, List<NameValuePair> parameters)
            throws URISyntaxException, IOException {
        String response_content = "";

        URIBuilder query = new URIBuilder(uri);
        query.addParameters(parameters);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(query.build());

        request.setHeader(HttpHeaders.ACCEPT, "application/json");
        request.addHeader("X-CMC_PRO_API_KEY", apiKey);

        try (CloseableHttpResponse response = client.execute(request)) {
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            response_content = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
        }
        return response_content;
    }

    public JSONArray filter(JSONObject json) {
        JSONArray data = (JSONArray) json.get("data");
        JSONObject[] dataList = new JSONObject[data.size()];
        JSONObject[] quote = new JSONObject[data.size()];
        JSONObject[] subData = new JSONObject[data.size()];
        JSONArray filteredData = new JSONArray();
        System.out.println(json);
        for(int i = 0; i < data.size(); i++) {
            dataList[i] = (JSONObject) data.get(i);
            quote[i] = (JSONObject) dataList[i].get("quote");
            quote[i] = (JSONObject) quote[i].get("USD");
            subData[i] = new JSONObject();
            subData[i].put("name", (String)dataList[i].get("name"));
            subData[i].put("price", (Double)quote[i].get("price"));
            filteredData.add(i, subData[i]);
        }

        return filteredData;
    }

    public JSONArray getPricesandNames () {
        String uri = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("start", "1"));
        parameters.add(new BasicNameValuePair("limit", "10"));
        parameters.add(new BasicNameValuePair("sort", "price"));
        parameters.add(new BasicNameValuePair("price_max", "5000"));


        JSONObject parsed_result = null;
        try {
            String result = makeAPICall(uri, parameters);

            JSONParser parser = new JSONParser();
            parsed_result = (JSONObject) parser.parse(result);
            return filter(parsed_result);

        } catch (IOException e) {
            System.out.println("Error: cannont access content - " + e.toString());
        } catch (URISyntaxException e) {
            System.out.println("Error: Invalid URL " + e.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject errObject = new JSONObject();
        JSONArray errArray = new JSONArray();
        errObject.put("eroare", "nu merge !");
        errArray.add(errObject);
        return errArray;
    }


}
