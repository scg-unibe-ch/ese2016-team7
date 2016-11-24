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

        // suppress bootstrap and css requests for cleaner logging
        if (!request.getRequestURL().toString().contains("/css/") && !request.getRequestURL().toString().contains("/bootstrap.")) {
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
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        // suppress bootstrap and css requests for cleaner logging
        if (!request.getRequestURL().toString().contains("/css/") && !request.getRequestURL().toString().contains("/bootstrap.")) {
            Principal user = request.getUserPrincipal();
            String requestId = (String) request.getAttribute("RequestId");
            String userName = user != null ? user.getName() : "Anonymous";
            String logEntry = String.format("Completed request: %s, Status code: %d, User: %s, Request UUID: %s", request.getRequestURI(), response.getStatus(), userName, requestId);

            logger.info(logEntry);
        }
    }

    /**
     * The following three methods create log messages for when a controller received/handled successfully/failed handling
     * a request.
     */
    public static void receivedRequest(String controller, String requestMapping){
        logger.info("Controller " + controller + " received request " + requestMapping + " successfully");
    }

    public static void handledRequestSuccessfully(String controller, String requestMapping){
        logger.info("Controller " + controller + " handled request " + requestMapping + " successfully");
    }

    public static void handlingRequestFailed(String controller, String requestMapping, String reason){
        logger.warn("Failure: Controller " + controller + " couldn't handle request " + requestMapping + ": "
        + reason);
    }

    /**
     * Creates a warning log, that an exception was thrown when handling the specified request at the specified location
     * and prints the stackTrace of the exception. Used in Controller and Service.
     */
    public static void exceptionLog(String request, String location, String Exception, Exception e, String message){
        logger.warn("Request: " + request + "; Location: " + location + "; " + Exception + " thrown " + message, e);
    }

}
