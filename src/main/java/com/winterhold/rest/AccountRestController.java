package com.winterhold.rest;
import com.winterhold.dto.account.RequestTokenDTO;
import com.winterhold.dto.account.ResponseTokenDTO;
import com.winterhold.service.AccountService;
import com.winterhold.utility.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AccountRestController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/authenticate")
    public ResponseTokenDTO post(@RequestBody RequestTokenDTO dto){
        var token = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
        authenticationManager.authenticate(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getUsername());

        String generatedToken = jwtToken.generateToken(dto.getSubject(),
                userDetails.getUsername(),
                dto.getSecretKey(),
                dto.getAudience());

        ResponseTokenDTO response = new ResponseTokenDTO(generatedToken, dto.getUsername());
        return response;
    }
}
