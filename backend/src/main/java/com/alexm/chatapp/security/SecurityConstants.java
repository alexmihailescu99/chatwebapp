package com.alexm.chatapp.security;

public class SecurityConstants {
    public static final String SECRET_KEY = "VERY_SECRET_KEY";
    public static final int EXPIRATION_TIME = 900000; // 15 mins
    public static final String TOKEN_PREFIX ="Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
