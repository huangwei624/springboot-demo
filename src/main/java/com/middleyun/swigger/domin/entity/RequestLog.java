package com.middleyun.swigger.domin.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @title 请求日志
 * @description
 * @author huangwei
 * @createDate 2020/12/29
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestLog {

    private String moduleName;

    private String methodDescription;

    private String host;

    private String requestUrl;

    private String requestParams;

    private String responseResult;

    private String browersName;

    private String requestMethod;

    private String operatingSystem;

    private String responseStatus;

    @Override
    public String toString() {
        return "RequestLog{" +
                "  \n moduleName='" + moduleName + '\'' +
                ", \n methodDescription='" + methodDescription + '\'' +
                ", \n host='" + host + '\'' +
                ", \n requestUrl='" + requestUrl + '\'' +
                ", \n requestParams='" + requestParams + '\'' +
                ", \n responseResult='" + responseResult + '\'' +
                ", \n browersName='" + browersName + '\'' +
                ", \n requestMethod='" + requestMethod + '\'' +
                ", \n operatingSystem='" + operatingSystem + '\'' +
                ", \n responseStatus='" + responseStatus + '\'' + "\n" +
                '}';
    }
}
