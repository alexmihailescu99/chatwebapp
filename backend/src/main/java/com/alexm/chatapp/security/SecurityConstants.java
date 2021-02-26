package com.alexm.chatapp.security;

public class SecurityConstants {
    public static final String SECRET_KEY = "VERY_SECRET_KEY";
    public static final int EXPIRATION_TIME = 3600000; // 1 hour (15 min recommended, but will do for now)
    public static final String TOKEN_PREFIX ="Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
