package ro.sdi.lab24.web.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ro.sdi.lab24.core.model.User;
import ro.sdi.lab24.core.service.UserService;

import java.util.Arrays;
import java.util.List;


@Component
public class CatalogUserDetailsService implements UserDetailsService, ApplicationListener<AuthenticationSuccessEvent> {

    private static final Logger log = LoggerFactory.getLogger(CatalogUserDetailsService.class);

    @Autowired
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.trace("loadUserByUsername:: username = {}", username);

        User user = userService.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        List<GrantedAuthority> authorities = Arrays.asList(
                new SimpleGrantedAuthority("ROLE_" + user.getRole()));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(), true, true, true, true, authorities);
//        return new org.springframework.security.core.userdetails.User("user", "pass", true, true,
//                true, true, authorities);
    }


    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {

    }
}
