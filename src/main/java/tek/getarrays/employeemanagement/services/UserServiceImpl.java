package tek.getarrays.employeemanagement.services;


import lombok.RequiredArgsConstructor;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tek.getarrays.employeemanagement.Enum.UserStatus;
import tek.getarrays.employeemanagement.Exception.BadRequestException;

import tek.getarrays.employeemanagement.Exception.UserNotFoundException;
import tek.getarrays.employeemanagement.repository.UserRepository;
import tek.getarrays.employeemanagement.security.config.JwtUtils;
import tek.getarrays.employeemanagement.dto.UserDto;
import tek.getarrays.employeemanagement.entity.User;
import tek.getarrays.employeemanagement.utils.ErrorMessages;

import static tek.getarrays.employeemanagement.Enum.UserStatus.ACTIVE;
import static tek.getarrays.employeemanagement.utils.ErrorMessages.PASSWORDS_DONT_MATCH;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    private User findUser(long id){return userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));}

    private void validatePassword(UserDto userDto){
        if (!userDto.getPassWordConfirm().equals(userDto.getPassword())){
            throw new BadRequestException(PASSWORDS_DONT_MATCH);
        }
    }

    private void getUser(User user, UserDto userDto){
        user.setUserName(userDto.getUserName());
        user.setEmail(userDto.getEmail());
        user.setUserStatus(ACTIVE);
        user.setRole(userDto.getRole());
    }
    @Override
    public User registerUser(UserDto userDto){
        User user =  new User();
        getUser(user,userDto);
        if (!StringUtils.hasText(userDto.getPassword())){
            throw new BadRequestException("password is required");
        }
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        validatePassword(userDto);
        return userRepo.save(user);
    }

    @Override
    public void changePassword(long id, String oldPassword, String newPassword) {
        User user = findUser(id);
            if (!oldPassword.equals(user.getPassword())){
                throw new BadRequestException("the old password is not correct");
            }
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepo.save(user);
    }


    private UserDto mapToDto(User user){
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUserName(user.getUserName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setCreateDate(user.getCreateDate());
        dto.setUpdateDate(user.getUpdateDate());
        return dto;
    }
}
