package tek.getarrays.employeemanagement.services;

import tek.getarrays.employeemanagement.dto.UserDto;
import tek.getarrays.employeemanagement.entity.User;
import tek.getarrays.employeemanagement.security.response.AuthResponse;

public interface UserService {


    User registerUser(UserDto userDto);

    AuthResponse login(UserDto userDto);
}
