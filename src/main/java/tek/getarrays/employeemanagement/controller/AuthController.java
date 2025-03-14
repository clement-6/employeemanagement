package tek.getarrays.employeemanagement.controller;


import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tek.getarrays.employeemanagement.dto.UserDto;
import tek.getarrays.employeemanagement.entity.User;
import tek.getarrays.employeemanagement.security.response.AuthResponse;
import tek.getarrays.employeemanagement.services.UserService;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @ApiOperation(value = "Register a user")
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.registerUser(userDto), HttpStatus.CREATED);
    }
    @ApiOperation(value = "Authenticate a user")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.login(userDto),HttpStatus.CREATED);
    }



}
