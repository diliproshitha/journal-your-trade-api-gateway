package com.journalyourtrade.apigateway.service;

import com.journalyourtrade.apigateway.model.configurations.JYTUserDetails;
import com.journalyourtrade.apigateway.model.entity.JYTUser;
import com.journalyourtrade.apigateway.model.entity.Role;
import com.journalyourtrade.apigateway.model.entity.Roles;
import com.journalyourtrade.apigateway.model.web.JYTUserDto;
import com.journalyourtrade.apigateway.repository.JYTUserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
public class JYTUserDetailsService implements UserDetailsService {

    JYTUserRepository userRepository;
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public JYTUserDetailsService(JYTUserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<JYTUser> user = userRepository.findByEmail(userName);

        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));

        return user.map(JYTUserDetails::new).get();
    }

    public JYTUser createWebUser(JYTUserDto userDto) {

        List<Role> roles = new ArrayList<>();
        roles.add(new Role(Roles.ROLE_USER_STR));

        JYTUser jytUser = JYTUser.builder()
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .roles(roles)
                .build();
        return userRepository.save(jytUser);
    }
}
