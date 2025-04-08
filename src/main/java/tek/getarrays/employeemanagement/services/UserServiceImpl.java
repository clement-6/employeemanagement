package tek.getarrays.employeemanagement.services;


import lombok.RequiredArgsConstructor;


import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tek.getarrays.employeemanagement.Enum.UserStatus;
import tek.getarrays.employeemanagement.Exception.BadRequestException;
import tek.getarrays.employeemanagement.Exception.UserNotFoundException;
import tek.getarrays.employeemanagement.entity.Role;
import tek.getarrays.employeemanagement.repository.RoleRepo;
import tek.getarrays.employeemanagement.repository.UserRepository;
import tek.getarrays.employeemanagement.dto.UserDto;
import tek.getarrays.employeemanagement.entity.User;

import java.util.HashSet;
import java.util.Set;

import static tek.getarrays.employeemanagement.Enum.UserStatus.ACTIVE;
import static tek.getarrays.employeemanagement.utils.ErrorMessages.PASSWORDS_DONT_MATCH;
import static tek.getarrays.employeemanagement.utils.ErrorMessages.ROLE_NOT_FOUND;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    private final RoleRepo roleRepo;
    private final ModelMapper modelMapper;

    private User findUser(long id){return userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));}

    private void validatePassword(UserDto userDto){
        if (!userDto.getPassWordConfirm().equals(userDto.getPassword())){
            throw new BadRequestException(PASSWORDS_DONT_MATCH);
        }
    }

    @Override
    public User registerUser(UserDto userDto){
        User user =  modelMapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Set<Role> roles = new HashSet<>();
        for (String roleName : userDto.getRoles()){
            Role role = roleRepo.findByRoleName(roleName).orElseThrow(() -> new UserNotFoundException(ROLE_NOT_FOUND +roleName));
            roles.add(role);
        }
        user.setRoles(roles);
        user.setUserStatus(ACTIVE);
        validatePassword(userDto);
        return userRepo.save(user);
    }

    private void validateOldPassword(User user , String oldPassword){
        if (!oldPassword.equals(user.getPassword())){
            throw new BadRequestException(PASSWORDS_DONT_MATCH);
        }
    }

    @Override
    public void changePassword(long id, String oldPassword, String newPassword) {
            User user = findUser(id);
            validateOldPassword(user,oldPassword);
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepo.save(user);
    }



}
