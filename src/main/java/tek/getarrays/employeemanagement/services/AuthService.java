package tek.getarrays.employeemanagement.services;

import tek.getarrays.employeemanagement.dto.AuthDto;
import tek.getarrays.employeemanagement.dto.UserDto;
import tek.getarrays.employeemanagement.entity.User;
import tek.getarrays.employeemanagement.security.response.AuthResponse;

public interface AuthService {

    AuthResponse login(AuthDto authDto) throws Exception;

    User UserConnect();
}
