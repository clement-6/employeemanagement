package tek.getarrays.employeemanagement.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import tek.getarrays.employeemanagement.Exception.UnAuthorizedException;
import tek.getarrays.employeemanagement.dto.UserDto;
import tek.getarrays.employeemanagement.security.config.JwtUtils;
import tek.getarrays.employeemanagement.security.response.AuthResponse;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;


    public AuthResponse login(UserDto userDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUserName(), userDto.getPassword()));
        if (authentication.isAuthenticated()){
            String token = jwtUtils.generateToken(userDto.getUserName());
            return new AuthResponse(token,"Authentification reussie");
        }
        throw  new UnAuthorizedException("vous n'etes pas autoriser");
    }










}
