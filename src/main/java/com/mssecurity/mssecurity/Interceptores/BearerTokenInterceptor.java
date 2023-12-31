package com.mssecurity.mssecurity.Interceptores;


import com.mssecurity.mssecurity.Services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class BearerTokenInterceptor implements HandlerInterceptor {
    private static final String BEARER_PREFIX = "Bearer ";
    private JwtService myJWTService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler)

            throws Exception {
        String authorizationHeader = request.getHeader("Authorization");
        String token = "";
        if (authorizationHeader != null &&
                authorizationHeader.startsWith(BEARER_PREFIX)) {

            token = authorizationHeader.substring(BEARER_PREFIX.length());
            // Verifica el token aquí, por ejemplo, con un servicio de autenticación
            // Si el token es válido, puedes permitir que la solicitud continúe
            // Si no es válido, puedes rechazar la solicitud o realizar otra acción
            // apropiada.
            // Por simplicidad, aquí solo se muestra cómo imprimir el token.
            System.out.println("Bearer Token: " + token);
        }
            // Devuelve true para permitir que la solicitud continúe o false para bloquearla
        return myJWTService.validateToken(token);
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
// Lógica a ejecutar después de que se haya manejado la solicitud por el controlador
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse
            response, Object handler,

                                Exception ex) throws Exception {
// Lógica a ejecutar después de completar la solicitud, incluso después de la renderización de la vista
    }
}