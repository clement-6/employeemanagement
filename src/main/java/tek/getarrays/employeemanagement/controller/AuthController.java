package tek.getarrays.employeemanagement.controller;


import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tek.getarrays.employeemanagement.dto.AuthDto;
import tek.getarrays.employeemanagement.dto.UserDto;
import tek.getarrays.employeemanagement.security.response.AuthResponse;
import tek.getarrays.employeemanagement.services.AuthService;



@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;


    @ApiOperation(value = "Authenticate a user")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthDto authDto) throws Exception {
        return new ResponseEntity<>(service.login(authDto),HttpStatus.CREATED);
    }





}
