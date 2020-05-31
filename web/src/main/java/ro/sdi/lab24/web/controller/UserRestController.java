package ro.sdi.lab24.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.sdi.lab24.web.dto.CredentialsDTO;


@RestController
public class UserRestController {
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    CredentialsDTO getTypeOfCurrentUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User principal = (User) authentication.getPrincipal();
            principal.getAuthorities().forEach(System.out::println);
            return new CredentialsDTO(principal.getAuthorities().toArray()[0].toString());
        } catch (Exception e) {
            return new CredentialsDTO("NONE");
        }
    }
}
