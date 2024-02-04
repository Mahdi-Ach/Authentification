package com.ons.securitylayerJwt.businessLogic;

import com.ons.securitylayerJwt.dto.BearerToken;
import com.ons.securitylayerJwt.dto.LoginDto;
import com.ons.securitylayerJwt.dto.RegisterDto;
import com.ons.securitylayerJwt.models.Role;
import com.ons.securitylayerJwt.models.RoleName;
import com.ons.securitylayerJwt.models.User;
import com.ons.securitylayerJwt.persistence.IRoleRepository;
import com.ons.securitylayerJwt.persistence.IUserRepository;
import com.ons.securitylayerJwt.security.JwtUtilities;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final AuthenticationManager authenticationManager ;
    private final IUserRepository iUserRepository ;
    private final IRoleRepository iRoleRepository ;
    private final PasswordEncoder passwordEncoder ;
    private final JwtUtilities jwtUtilities ;
    private final GithubServiceLogin githubServiceLogin;

    @Override
    public Role saveRole(Role role) {
        return iRoleRepository.save(role);
    }

    @Override
    public User saverUser(User user) {
        return iUserRepository.save(user);
    }

    @Override
    public ResponseEntity<HashMap<String,String>> register(RegisterDto registerDto) {
        HashMap<String,String> response = new HashMap<>();
        if(iUserRepository.existsByEmail(registerDto.getEmail()))
        {
            response.put("reject","email is already taken !");
            return new ResponseEntity<>(response,HttpStatus.SEE_OTHER); }
        else
        { User user = new User();
            user.setEmail(registerDto.getEmail());
            user.setFirstName(registerDto.getFirstName());
            user.setLastName(registerDto.getLastName());
            user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
            //By Default , he/she is a simple user
            Role role = iRoleRepository.findByRoleName(RoleName.USER);
            user.setRoles(Collections.singletonList(role));
            iUserRepository.save(user);
            String token = jwtUtilities.generateToken(registerDto.getEmail(),Collections.singletonList(role.getRoleName()));
            //return new ResponseEntity<>(new BearerToken(token , "Bearer "),HttpStatus.OK);
            response.put("accept","Register Is Seccufuly");
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
    }

    @Override
    public HashMap<String, String> authenticate(LoginDto loginDto) {
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = iUserRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<String> rolesNames = new ArrayList<>();
        user.getRoles().forEach(r-> rolesNames.add(r.getRoleName()));
        String token = jwtUtilities.generateToken(user.getUsername(),rolesNames);
        String refresh_token = jwtUtilities.generateRefreshToken(user.getUsername(),rolesNames);
        user.setRefresh_token(refresh_token);
        HashMap<String,String> tokens = new HashMap<>();
        tokens.put("access-token",token);
        tokens.put("refresh-token",refresh_token);
        return tokens;
    }
    @Override
    public HashMap<String, String> authenticateWithGoogle(RegisterDto registerDto) {
        System.out.println("chui dans authentificcation");
        System.out.println(!iUserRepository.existsByEmail(registerDto.getEmail()));
        if(!iUserRepository.existsByEmail(registerDto.getEmail())){
            User user = new User();
            user.setEmail(registerDto.getEmail());
            user.setFirstName(registerDto.getFirstName());
            user.setLastName(registerDto.getLastName());
            user.setName(registerDto.getName());
            user.setPassword(passwordEncoder.encode("hi"));
            user.setProvide("Google");
            //By Default , he/she is a simple user
            Role role = iRoleRepository.findByRoleName(RoleName.USER);
            user.setRoles(Collections.singletonList(role));
            iUserRepository.save(user);
        }
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        registerDto.getEmail(),
                        "hi"
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = iUserRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<String> rolesNames = new ArrayList<>();
        user.getRoles().forEach(r-> rolesNames.add(r.getRoleName()));
        String token = jwtUtilities.generateToken(user.getUsername(),user.getName(),rolesNames);
        String refresh_token = jwtUtilities.generateRefreshToken(user.getUsername(),rolesNames);
        user.setRefresh_token(refresh_token);
        HashMap<String,String> tokens = new HashMap<>();
        tokens.put("access-token",token);
        tokens.put("refresh-token",refresh_token);
        return tokens;
    }
    @Override
    public HashMap<String, String> authenticateWithGithub(String code) throws URISyntaxException, IOException {
        HashMap<String,String> data = githubServiceLogin.LoginGithub(code);
        if(data.get("login") == null){
            return new HashMap<>();
        }
        if(!iUserRepository.existsByEmail(data.get("login"))){
            User user = new User();
            user.setEmail(data.get("login"));
            user.setProvide("Github");
            user.setPassword(passwordEncoder.encode("hi"));
            user.setName(data.get("name"));
            Role role = iRoleRepository.findByRoleName(RoleName.USER);
            user.setRoles(Collections.singletonList(role));
            iUserRepository.save(user);
        }
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        data.get("login"),
                        "hi"
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = iUserRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<String> rolesNames = new ArrayList<>();
        user.getRoles().forEach(r-> rolesNames.add(r.getRoleName()));
        String token = jwtUtilities.generateToken(user.getUsername(),user.getName(),rolesNames);
        String refresh_token = jwtUtilities.generateRefreshToken(user.getUsername(),rolesNames);
        user.setRefresh_token(refresh_token);
        HashMap<String,String> tokens = new HashMap<>();
        tokens.put("access-token",token);
        tokens.put("refresh-token",refresh_token);
        return tokens;
    }
}

