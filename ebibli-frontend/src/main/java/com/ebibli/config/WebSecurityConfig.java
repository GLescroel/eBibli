package com.ebibli.config;

import com.ebibli.service.UtilisateurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSecurityConfig.class);

    private static final String LOGGIN_URL = "/login";

    @Autowired
    UtilisateurService utilisateurService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        //configure
    }

    /**
     * Configuration des droits d'accès aux ressources web
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        LOGGER.info("configure");

        http.csrf()
                .disable()
                .exceptionHandling()
                .and()
                .authenticationProvider(getProvider())
                .formLogin()
                .loginProcessingUrl(LOGGIN_URL)
                .successHandler(new AuthentificationLoginSuccessHandler())
                .failureHandler(new SimpleUrlAuthenticationFailureHandler())
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .logoutSuccessHandler(new AuthentificationLogoutSuccessHandler())
                .invalidateHttpSession(true)
                .and()
                .authorizeRequests()
                .antMatchers("/", "/**.jpg", "/**.png", "/**.css").permitAll()
                .antMatchers(LOGGIN_URL).permitAll()
                .antMatchers("/logout").permitAll()
                .antMatchers("/creationCompte").permitAll()
                .antMatchers("/compte").authenticated()
                .antMatchers("/Utilisateur/*/mesEmprunts**").authenticated()
                .antMatchers("/*").permitAll()
                .antMatchers("/biblio-services/**").permitAll()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage(LOGGIN_URL)
                .successForwardUrl(LOGGIN_URL)
                .failureForwardUrl(LOGGIN_URL)
                .permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
                .and().rememberMe();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        LOGGER.info("authprovider");

        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(utilisateurService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }

    private class AuthentificationLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
        @Override
        public void onAuthenticationSuccess(HttpServletRequest request,
                                            HttpServletResponse response, Authentication authentication) {
            response.setStatus(HttpServletResponse.SC_OK);
            LOGGER.info("LOGIN SUCCESSFULL");
        }
    }
    private class AuthentificationLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
        @Override
        public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                    Authentication authentication) throws IOException {
            response.setStatus(HttpServletResponse.SC_OK);
            LOGGER.info("log out success");
            response.sendRedirect(request.getContextPath() + "/");
        }
    }
    @Bean
    public AuthenticationProvider getProvider() {
        AppAuthProvider provider = new AppAuthProvider();
        provider.setUserDetailsService(utilisateurService);
        return provider;
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandler() {
            @Override
            public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
                LOGGER.info("ACCESS DENIED");
            }
        };
    }

}
