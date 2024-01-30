package com.syllabus.api.aspectLogger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.syllabus.api.aspectLogger.persistLog.Logger;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;


@Aspect
@Component
public class RequestLoggingAspect {

    private final ObjectMapper objectMapper;
    private final Logger logger = Logger.getInstance();

    @Autowired
    public RequestLoggingAspect(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restControllerMethods() {}

    @Before("restControllerMethods()")
    public void logBeforeControllerMethod(JoinPoint joinPoint) {
        String url = extractUrl();
        StringBuilder logs=new StringBuilder();
        String message="Incoming request to " + url +" "+joinPoint.getSignature();
        logs.append(message+"\n");

//        System.out.println(message);
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            message=logRequestData(args[0]);
            logs.append(message+"\n");
        }

        logger.writeLog(logs.toString());
    }

    private String extractUrl() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            if (request != null) {
                return request.getRequestURI();
            }
        }
        return "";
    }

    private String logRequestData(Object requestData) {
        try {
            String requestBody = objectMapper.writeValueAsString(requestData);
            String mesaage="Incoming request body: " + requestBody;
//            System.out.println(mesaage);
            return mesaage;
        } catch (IOException e) {
            System.err.println("Error logging request body: " + e.getMessage());
        }
        return "Error in Log";
    }
}

