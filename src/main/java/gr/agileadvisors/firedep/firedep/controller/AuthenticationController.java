package gr.agileadvisors.firedep.firedep.controller;

import gr.agileadvisors.firedep.firedep.dto.JwtDto;
import gr.agileadvisors.firedep.firedep.dto.UserRegisterDto;
import gr.agileadvisors.firedep.firedep.dto.UserLoginDto;
import gr.agileadvisors.firedep.firedep.service.JwtUtilService;
import gr.agileadvisors.firedep.firedep.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/firedep")
public class AuthenticationController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtilService jwtUtil;

    @Operation(summary = "Create new user", description = "Create new user by submitting a unique username, password and specific role from these: ROLE_ADMIN , ROLE_MODERATOR , ROLE_USER")
    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody UserRegisterDto userRegisterDto){
        return userService.createUser(userRegisterDto);
    }

    @CrossOrigin(origins = {"http://192.168.28.40:3000/","http://localhost:3000","https://kepse.psnet.gr/"})
    @Operation(summary = "Login", description = "Login using credentials")
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserLoginDto userLoginDto) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginDto.getUsername(), userLoginDto.getPassword()));
        }catch (DisabledException e)
        {
            throw new Exception("USER_DISABLED",e);
        }
        catch(BadCredentialsException e){
            throw new Exception("INVALID_CREDENTIALS",e);
        }

        UserDetails userDetails = userService.loadUserByUsername(userLoginDto.getUsername());
        String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtDto(token));
    }

}
