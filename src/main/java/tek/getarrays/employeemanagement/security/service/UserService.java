package tek.getarrays.employeemanagement.security.service;

import tek.getarrays.employeemanagement.security.dto.UserDto;
import tek.getarrays.employeemanagement.security.entity.User;
import tek.getarrays.employeemanagement.security.response.AuthResponse;

public interface UserService {


    User registerUser(UserDto userDto);

    AuthResponse login(UserDto userDto);
}
