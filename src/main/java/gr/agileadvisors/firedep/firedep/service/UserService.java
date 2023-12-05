package gr.agileadvisors.firedep.firedep.service;

import gr.agileadvisors.firedep.firedep.dto.UserRegisterDto;
import gr.agileadvisors.firedep.firedep.model.User;
import gr.agileadvisors.firedep.firedep.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Lazy
    @Autowired
    private PasswordEncoder bcryptEncoder;

    public ResponseEntity<String> createUser(UserRegisterDto userRegisterDto) {
        userRepository.save(mapUserDtoToUser(userRegisterDto));
        return new ResponseEntity<>(userRegisterDto.getUsername(), HttpStatus.CREATED);
    }

    private User mapUserDtoToUser(UserRegisterDto userRegisterDto){
        return User.builder().username(userRegisterDto.getUsername()).password(bcryptEncoder.encode(userRegisterDto.getPassword())).role(userRegisterDto.getRole()).build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<SimpleGrantedAuthority> roles = null;
        User user = userRepository.findByUsername(username);
        if (user != null) {
            roles = List.of(new SimpleGrantedAuthority(user.getRole()));
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), roles);
        }
        throw new UsernameNotFoundException("User not found with the name " + username);
    }
}
