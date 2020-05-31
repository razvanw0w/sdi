package ro.sdi.lab24.web.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import ro.sdi.lab24.core.model.UserRole;
import ro.sdi.lab24.web.security.CatalogUserDetailsService;
import ro.sdi.lab24.web.security.CustomLogoutSuccessHandler;
import ro.sdi.lab24.web.security.MySavedRequestAwareAuthenticationSuccessHandler;
import ro.sdi.lab24.web.security.RestAuthenticationEntryPoint;

/**
 * Created by radu.
 * <p>
 * http://www.baeldung.com/securing-a-restful-web-service-with-spring-security
 * <p>
 * curl -i -X POST -d username=student -d password=student -c /home/radu/cookies.txt http://localhost:8080/login
 * <p>
 * curl -i --header "Accept:application/json" -X GET -b /home/radu/cookies.txt http://localhost:8080/api/students
 */

@Configuration
@EnableWebSecurity
@ComponentScan("ro.sdi.lab24.web.security")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private MySavedRequestAwareAuthenticationSuccessHandler
            mySavedRequestAwareAuthenticationSuccessHandler;

    @Autowired
    private CatalogUserDetailsService catalogUserDetailsService;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {

        auth.authenticationProvider(authProvider());
//        auth.inMemoryAuthentication()
//                .withUser("teacher").password("pass").roles("TEACHER")
//                .and()
//                .withUser("student").password("pass").roles("STUDENT");
    }


    @Override
    protected UserDetailsService userDetailsService() {
        return catalogUserDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .authorizeRequests()
                .antMatchers("/api/clients*").hasRole(UserRole.EMPLOYEE.toString())
                .antMatchers("/api/clients/**").hasRole(UserRole.EMPLOYEE.toString())
                .antMatchers("/api/rentals").hasRole(UserRole.EMPLOYEE.toString())
                .antMatchers("/api/rentals/**").hasRole(UserRole.EMPLOYEE.toString())
                .antMatchers(HttpMethod.DELETE, "/api/movies").hasRole(UserRole.EMPLOYEE.toString())
                .antMatchers(HttpMethod.POST, "/api/movies").hasRole(UserRole.EMPLOYEE.toString())
                .antMatchers(HttpMethod.PUT, "/api/movies").hasRole(UserRole.EMPLOYEE.toString())
                .antMatchers(HttpMethod.GET, "/api/movies").permitAll()
                .antMatchers(HttpMethod.GET, "/api/movies/filter-genre/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/movies/filter-name/**").permitAll()
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .successHandler(mySavedRequestAwareAuthenticationSuccessHandler)
                .failureHandler(new SimpleUrlAuthenticationFailureHandler())
                .and()
                .logout()
                .logoutUrl("/logout").permitAll()
                .logoutSuccessHandler(new CustomLogoutSuccessHandler())
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .permitAll().and().cors();
    }

    @Bean
    public MySavedRequestAwareAuthenticationSuccessHandler mySuccessHandler() {
        return mySavedRequestAwareAuthenticationSuccessHandler;
    }

    @Bean
    public SimpleUrlAuthenticationFailureHandler myFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler();
    }
}

