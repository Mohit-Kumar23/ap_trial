package com.hp.appoindone;

import java.util.regex.Pattern;

public class Utils {
    public static final Pattern PASSWORD_PATTERN = Pattern.compile("^"
            +"(?=.*[0-9])"
            +"(?=.*[a-z])"
            +"(?=.*[A-Z])"
            +"(?=.*[a-zA-Z])"
            +"(?=.*[@#$%^&+=])"
            +"(?=\\S+$)"
            +".{8,}"
            +"$");
}


