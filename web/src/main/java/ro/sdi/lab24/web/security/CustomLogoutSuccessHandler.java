package ro.sdi.lab24.web.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("trying to log out");
        if (authentication != null && authentication.getDetails() != null) {
            try {
                System.out.println("logged out");
                request.getSession().invalidate();
            } catch (Exception e) {
                e.printStackTrace();
                e = null;
            }
        }
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
