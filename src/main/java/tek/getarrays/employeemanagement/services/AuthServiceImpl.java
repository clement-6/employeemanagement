package tek.getarrays.employeemanagement.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import tek.getarrays.employeemanagement.Exception.UnAuthorizedException;
import tek.getarrays.employeemanagement.dto.AuthDto;

import tek.getarrays.employeemanagement.entity.User;
import tek.getarrays.employeemanagement.security.config.JwtUtils;
import tek.getarrays.employeemanagement.security.response.AuthResponse;
import tek.getarrays.employeemanagement.security.config.CustomUserDetails;

import static tek.getarrays.employeemanagement.utils.ErrorMessages.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    private final CustomUserDetails customUserDetails;


    @Override
    public AuthResponse login(AuthDto authDto) throws Exception {
        authenticate(authDto.getPseudo(), authDto.getPassWord());
        UserDetails userDetails = customUserDetails.loadUserByUsername(authDto.getPseudo());
        String token = jwtUtils.generateToken(userDetails);
        return new AuthResponse(token);
    }

    @Override
    public User UserConnect() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated()){
            throw new UnAuthorizedException(USER_CONNECT);
        }

        return (User) authentication.getPrincipal();

    }


    private void authenticate(String pseudo, String passWord) throws Exception{
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(pseudo, passWord));
        }catch (DisabledException e){
                throw new UnAuthorizedException(DISABLE_USER);
        }catch (BadCredentialsException e){
                throw new UnAuthorizedException(FAILED_CONNEXION);
        }
    }


}
