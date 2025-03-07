package tek.getarrays.employeemanagement.security.service.implement;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tek.getarrays.employeemanagement.Exception.ApiRequestException;
import tek.getarrays.employeemanagement.security.configuration.JwtUtils;
import tek.getarrays.employeemanagement.security.dto.UserDto;
import tek.getarrays.employeemanagement.security.entity.User;
import tek.getarrays.employeemanagement.security.repository.UserRepository;
import tek.getarrays.employeemanagement.security.response.AuthResponse;
import tek.getarrays.employeemanagement.security.service.UserService;





@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    private final JwtUtils jwtUtils;

    @Override
    public User registerUser(UserDto userDto){
        User user =  new User();
        user.setUserName(userDto.getUserName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(userDto.getRole());
        return userRepo.save(user);
    }

    @Override
    public AuthResponse login(UserDto userDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUserName(), userDto.getPassword()));
         if (authentication.isAuthenticated()){
             String token = jwtUtils.generateToken(userDto.getUserName());
             return new AuthResponse(token,"Authentification reussie");
         }
       throw new ApiRequestException("vous n'etes pas autorisez");
    }
}
