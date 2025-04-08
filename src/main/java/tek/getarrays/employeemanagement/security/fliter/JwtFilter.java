package tek.getarrays.employeemanagement.security.fliter;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import tek.getarrays.employeemanagement.security.config.JwtUtils;
import tek.getarrays.employeemanagement.utils.ErrorMessages;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static tek.getarrays.employeemanagement.utils.ErrorMessages.TOKEN_EXPIRE;
import static tek.getarrays.employeemanagement.utils.ErrorMessages.TOKEN_INVALID;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

         private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
         private final UserDetailsService userDetailsService;
         private final JwtUtils jwtUtils;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);//on recupere l'en-tete Authorization contenant le token jwt

         //1.verification basique du header
        //verifie si l'entete est vide ou ne commence pas par bearer
        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);

            return;
        }

             //2-extraction du token
            //extrait le token en supprimant le "Bearer "
            String jwt = authHeader.substring(7);

            String userName;

            try {

                //Recupere le username depuis le jwt
                userName = jwtUtils.extractUserName(jwt);

            } catch (ExpiredJwtException e){

                logger.error("Token expire: {}", e.getMessage());

                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, TOKEN_EXPIRE);

                return;

            } catch (JwtException e) {

                logger.error("Token Invalid: {}", e.getMessage());

                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, TOKEN_INVALID);

                return;
            }


         //3-Authentification
        //verifie si le token contient un username et qu'il n'est pas deja connecter
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null){
            authenticateUser(request,userName,jwt);
        }

          filterChain.doFilter(request, response);
    }

    //Authentifie l'utilisateur en chargeant ses détails et en stockant l'authentification dans le contexte de sécurité.
    private void authenticateUser(HttpServletRequest request, String userName, String jwt){

        //charge les details user depuis la BD
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

        if (jwtUtils.validateToken(jwt, userDetails)){

            logger.info("Authentification reussie pour : {}", userName);

            setAuthenticationInContext(request, userDetails);

        }else {

            logger.warn("Token invalide pour : {}", userName);

        }
    }


    private void setAuthenticationInContext(HttpServletRequest request, UserDetails userDetails){

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        //stocke l'authentification pour les requetes suivantes
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }



}
