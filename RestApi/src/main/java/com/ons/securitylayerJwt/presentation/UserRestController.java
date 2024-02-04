package com.ons.securitylayerJwt.presentation;


import com.ons.securitylayerJwt.businessLogic.GithubServiceLogin;
import com.ons.securitylayerJwt.businessLogic.IUserService;
import com.ons.securitylayerJwt.dto.LoginDto;
import com.ons.securitylayerJwt.dto.RegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
import java.io.IOException;
import java.net.URI;
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserRestController {


    private final IUserService iUserService ;
    private final GithubServiceLogin githubServiceLogin;

    //RessourceEndPoint:http://localhost:8087/api/user/register
    @PostMapping("/register")
    public ResponseEntity<?> register (@RequestBody RegisterDto registerDto)
    { return  iUserService.register(registerDto);}

    //RessourceEndPoint:http://localhost:8087/api/user/authenticate
    @PostMapping("/authenticate")
    public HashMap<String,String> authenticate(@RequestBody LoginDto loginDto)
    { return  iUserService.authenticate(loginDto);}
    @PostMapping("/authenticate-google")
    public HashMap<String,String> authenticate(@RequestBody RegisterDto registerDto)
    { return  iUserService.authenticateWithGoogle(registerDto);}
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestParam String refresh_token) {
        //System.out.println(k);
        // This code is handled by JwtRefreshTokenFilter
        return null; // Remove this endpoint as it's handled by the filter
    }
    @GetMapping("/callback")
    public HashMap<String,String> callback(@RequestParam String code) throws IOException, URISyntaxException {
        return iUserService.authenticateWithGithub(code);
    }
    @GetMapping("/check_token")
    public ResponseEntity<HashMap<String,String>> HiWorld(){
        HashMap<String,String> a = new HashMap<>();
        a.put("dsq","dsq");
        return new ResponseEntity<>(a, HttpStatus.OK);
    }
}
