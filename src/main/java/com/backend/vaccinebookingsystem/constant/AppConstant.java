package com.backend.vaccinebookingsystem.constant;

public class AppConstant {

    public AppConstant() {
    }

    public static final String DEFAULT_SYSTEM = "SYSTEM";

    public enum ResponseCode {

        SUCCESS("SUCCESS", "Success"),
        DATA_NOT_FOUND("DATA_NOT_FOUND", "Data Not Found"),
        INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "Internal Server Error"),
        UNKNOWN_ERROR("UNKNOWN_ERROR", "Unknown Error"),
        BAD_CREDENTIALS("BAD_CREDENTIALS", "Bad Credentials");

        private final String code;

        private final String message;

        ResponseCode(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }

    public enum ProfileRole {
        USER,
        HEALTH_ADMIN,
        ADMIN;
    }
}
