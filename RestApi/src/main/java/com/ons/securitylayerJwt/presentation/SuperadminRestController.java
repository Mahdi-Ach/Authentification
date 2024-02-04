package com.ons.securitylayerJwt.presentation;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/superadmin")
@RequiredArgsConstructor
public class SuperadminRestController {


    //RessourceEndPoint:http://localhost:8087/api/superadmin/hi
    @GetMapping("/hi")
    public ResponseEntity<HashMap<String,String>> sayHi ()
    {
        HashMap<String,String> hi = new HashMap<>();
        return new ResponseEntity<>(hi, HttpStatus.OK) ;}


}
