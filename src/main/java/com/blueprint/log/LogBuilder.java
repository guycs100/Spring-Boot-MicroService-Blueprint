package com.blueprint.log;

import com.blueprint.config.EnvironmentVars;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class LogBuilder {

    @Autowired
    private EnvironmentVars environmentVars;

    private LogMessage logMessage = null;

    private final static ThreadLocal<LogMessage> commonInfo = new ThreadLocal<>();

    private LogBuilder() {}

    public void initRequest(HttpServletRequest request) {
        LogMessage logMessage = new LogMessage();
        commonInfo.set(logMessage);

        logMessage.setRequestQueryString(request.getQueryString());
    }

    public LogBuilder get() {
        LogBuilder logBuilder = new LogBuilder();
        logBuilder.logMessage = new LogMessage();

        LogMessage commonInfo = LogBuilder.commonInfo.get();
        logBuilder.logMessage.setRequestQueryString(commonInfo.getRequestQueryString());
        logBuilder.logMessage.setAppName(environmentVars.getApplicationName());

        return logBuilder;
    }

    public LogBuilder setMessage(String message) {
        this.logMessage.setMessage(message);
        return this;
    }

    public String toString() {
        try {
            final ObjectMapper om = new ObjectMapper();
            return om.writeValueAsString(this.logMessage);
        }
        catch(JsonProcessingException jex) {
            jex.printStackTrace();
            return jex.getLocalizedMessage();
        }
    }
}
