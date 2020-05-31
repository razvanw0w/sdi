package ro.sdi.lab24.web.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;

public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            BufferedReader reader = request.getReader();
            StringBuffer stringBuffer = new StringBuffer();
            String line = null;

            while ((line = reader.readLine()) != null) {
                stringBuffer.append(line);
            }
            String parsedRequest = stringBuffer.toString();
            System.out.println(parsedRequest);
            if (parsedRequest != null) {
                ObjectMapper mapper = new ObjectMapper();
                AuthReq authReq = mapper.readValue(parsedRequest, AuthReq.class);
                System.out.println(authReq.toString());
                return new UsernamePasswordAuthenticationToken(authReq.getUsername(), authReq.getPassword());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new InternalAuthenticationServiceException("Failed to parse authentication request body");
        }
        return null;
    }

    @Data
    public static class AuthReq {
        String username;
        String password;
    }
}
