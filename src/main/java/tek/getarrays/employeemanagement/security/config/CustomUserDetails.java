package tek.getarrays.employeemanagement.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tek.getarrays.employeemanagement.Exception.UnAuthorizedException;
import tek.getarrays.employeemanagement.Exception.UserNotFoundException;
import tek.getarrays.employeemanagement.entity.User;
import tek.getarrays.employeemanagement.repository.UserRepository;

import java.util.Set;
import java.util.stream.Collectors;

import static tek.getarrays.employeemanagement.utils.ErrorMessages.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetailsService {

    private final UserRepository userRepo;

//    @Override
//    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
//        User user = userRepo.findByUserNameOrEmail(identifier,identifier).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND + identifier));
//        Set<GrantedAuthority> authorities = user.getRoles().stream().map((role) -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName())).collect(Collectors.toSet());
//
//        return new org.springframework.security.core.userdetails.User(
//                user.getUserName(), // Toujours retourner le username officiel
//                user.getPassword(),
//                authorities);
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND + username));
        Set<GrantedAuthority> authorities = user.getRoles().stream().map((role) -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName())).collect(Collectors.toSet());
        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);
    }



}
