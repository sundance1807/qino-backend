//package com.qino.security;
//
//import com.qino.model.Role;
//import com.qino.model.entity.UserEntity;
//import com.qino.repository.UserRepository;
//import com.qino.util.MessageSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class CustomUserDetailService implements UserDetailsService {
//    private final UserRepository userRepository;
//
//    @Autowired
//    public CustomUserDetailService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        UserEntity userEntity = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException(MessageSource.USERNAME_NOT_FOUND.getText(String.valueOf(username))));
//
//        return new User(userEntity.getUsername(), userEntity.getPassword(), mapRolesToAuthorities(userEntity.getRoles()));
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
//        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
//    }
//
//}
