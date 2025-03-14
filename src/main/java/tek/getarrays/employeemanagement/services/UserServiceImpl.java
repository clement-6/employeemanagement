package tek.getarrays.employeemanagement.services;


import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tek.getarrays.employeemanagement.Exception.BadRequestException;
import tek.getarrays.employeemanagement.Exception.UnAuthorizedException;
import tek.getarrays.employeemanagement.repository.UserRepository;
import tek.getarrays.employeemanagement.security.config.JwtUtils;
import tek.getarrays.employeemanagement.dto.UserDto;
import tek.getarrays.employeemanagement.entity.User;
import tek.getarrays.employeemanagement.security.response.AuthResponse;


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
        if (userDto.getUserName().isEmpty() || userDto.getEmail().isEmpty() || userDto.getPassword().isEmpty()){
            throw new BadRequestException("userName/Email/Password is required");
        }
        user.setUserName(userDto.getUserName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        if (!userDto.getPassWordConfirm().equals(userDto.getPassword())){
            throw new BadRequestException("vos mots de passe ne coresponde pas");
        }
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
       throw  new UnAuthorizedException("vous n'etes pas sutoriser");
    }
}
