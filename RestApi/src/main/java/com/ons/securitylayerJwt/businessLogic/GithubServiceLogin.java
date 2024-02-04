package com.ons.securitylayerJwt.businessLogic;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ons.securitylayerJwt.persistence.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class GithubServiceLogin {

    public HashMap<String, String> LoginGithub(String code) throws URISyntaxException, IOException {
        String clientId = "550790b5794a0235277e";
        String clientSecret = "808f522308312741a1c24bf18769dd9ffdaf090c";
        String redirectUri = "http://localhost:4200/callback";

        // Construct the request URL
        URI uri = new URIBuilder("https://github.com/login/oauth/access_token")
                .addParameter("client_id", clientId)
                .addParameter("client_secret", clientSecret)
                .addParameter("code", code)
                .addParameter("redirect_uri", redirectUri)
                .build();

        // Create an HTTP POST request
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost postRequest = new HttpPost(uri);
        postRequest.setEntity(new StringEntity("", ContentType.APPLICATION_FORM_URLENCODED));

        // Send the request and get the response
        HttpResponse response = client.execute(postRequest);

        // Get the response body as a string
        HttpEntity entity = response.getEntity();
        String responseBody = EntityUtils.toString(entity);

        // Extract the access token from the response body
        String accessToken = extractAccessToken(responseBody);
        // Use the access token to fetch user details
        String userDetails = getUserDetails(accessToken);
        System.out.println(userDetails);
        // Extract the login field value from the user details
        String login = extractLogin(userDetails);
        String name = extractName(userDetails);
        System.out.println(login);
        // Return the login value to the API caller
        HashMap<String,String> data = new HashMap<>();
        data.put("login",login);
        data.put("name",name);
        return data;
    }
    private String extractAccessToken(String responseBody) {
        String prefix = "access_token=";
        String suffix = "&scope";
        int startIndex = responseBody.indexOf(prefix);
        int endIndex = responseBody.indexOf(suffix);
        if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
            return responseBody.substring(startIndex + prefix.length(), endIndex);
        }
        return null; // Return null if the access token cannot be extracted
    }

    private String getUserDetails(String accessToken) throws URISyntaxException, IOException {
        URI uri = new URIBuilder("https://api.github.com/user")
                .build();

        // Create an HTTP GET request
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet getRequest = new HttpGet(uri);
        getRequest.setHeader("Authorization", "Bearer " + accessToken);

        // Send the request and get the response
        HttpResponse response = client.execute(getRequest);

        // Get the response body as a string
        HttpEntity entity = response.getEntity();
        String responseBody = EntityUtils.toString(entity);

        // Return the response body to the caller
        return responseBody;
    }
    private String extractName(String userDetails) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(userDetails, JsonObject.class);
        if (jsonObject.has("name")) {
            return jsonObject.get("name").getAsString();
        }
        return null; // Return null if the login field is not present
    }
    private String extractLogin(String userDetails) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(userDetails, JsonObject.class);
        if (jsonObject.has("login")) {
            return jsonObject.get("login").getAsString();
        }
        return null; // Return null if the login field is not present
    }
}
