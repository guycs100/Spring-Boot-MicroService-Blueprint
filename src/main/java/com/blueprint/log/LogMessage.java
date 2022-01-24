package com.blueprint.log;

import lombok.Data;

@Data
public class LogMessage {
    private String requestQueryString;
    private String appName;
    private String message;
}
