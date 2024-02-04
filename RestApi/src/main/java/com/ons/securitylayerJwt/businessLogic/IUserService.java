package com.ons.securitylayerJwt.businessLogic;


import com.ons.securitylayerJwt.dto.LoginDto;
import com.ons.securitylayerJwt.dto.RegisterDto;
import com.ons.securitylayerJwt.models.Role;
import com.ons.securitylayerJwt.models.User;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;


public interface IUserService {
   //ResponseEntity<?> register (RegisterDto registerDto);
 //  ResponseEntity<BearerToken> authenticate(LoginDto loginDto);

   HashMap<String,String> authenticate(LoginDto loginDto);
   HashMap<String, String> authenticateWithGithub(String code) throws URISyntaxException, IOException;
   HashMap<String, String> authenticateWithGoogle(RegisterDto registerDto);
   ResponseEntity<?> register (RegisterDto registerDto);
   Role saveRole(Role role);

   User saverUser (User user) ;
}
