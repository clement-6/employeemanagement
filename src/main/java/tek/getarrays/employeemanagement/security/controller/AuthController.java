package tek.getarrays.employeemanagement.security.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tek.getarrays.employeemanagement.security.dto.UserDto;
import tek.getarrays.employeemanagement.security.entity.User;
import tek.getarrays.employeemanagement.security.response.AuthResponse;
import tek.getarrays.employeemanagement.security.service.UserService;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.registerUser(userDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.login(userDto),HttpStatus.CREATED);
    }



}
