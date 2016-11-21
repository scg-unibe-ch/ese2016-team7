package ch.unibe.ese.team1.logger;

import java.security.Principal;
import java.util.Enumeration;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LogInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = Logger.getLogger("logger");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // Get the user from the request
        Principal user = request.getUserPrincipal();

        // username: "Anonymous" if user=null
        String userName = user != null ? user.getName() : "Anonymous";

        // Need a Universally Unique Identifer for the request
        UUID requestId = UUID.randomUUID();
        request.setAttribute("RequestId", requestId.toString());


        StringBuilder builder = new StringBuilder();

        Enumeration<String> parameterNames = request.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            for (String paramValue : paramValues) {
                builder.append(String.format("%s=%s", paramName, paramValue));
                builder.append("; ");
            }
        }

        String logEntry = String.format("Received request: %s, Path: %s, Params: { %s}, User: %s, RequestUUID: %s", request.getMethod(), request.getRequestURI(), builder.toString(), userName, requestId.toString());

        logger.info(logEntry);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        Principal user = request.getUserPrincipal();
        String requestId = (String) request.getAttribute("RequestId");
        String userName = user != null ? user.getName() : "Anonymous";
        String logEntry = String.format("Completed request: %s, Status code: %d, User: %s, Request UUID: %s", request.getRequestURI(), response.getStatus(), userName, requestId);

        logger.info(logEntry);
    }
}
