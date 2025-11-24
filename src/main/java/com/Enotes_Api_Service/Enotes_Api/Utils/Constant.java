package com.Enotes_Api_Service.Enotes_Api.Utils;

public class Constant {
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    public static final String PHONE_REGEX = "^\\+?[1-9]\\d{1,14}$\n";
    public static final String ROLE_ADMIN = "hasRole('ADMIN')";
    public static final String ROLE_ADMIN_USER = "hasAnyRole('USER', 'ADMIN')";
    public static final String ROLE_USER = "hasRole('USER')";
    public static final String DEFAULT_PAGENO = "0";
    public static final String DEFAULT_PAGESIZE = "10";
}
