package com.ruixun.shopcarts.util;

import javax.servlet.http.Cookie;

public class CookieUtils {
    public static Cookie getCookie(String name, Cookie[] cookies) {
        if(cookies!=null)
        {
            for(Cookie cookie:cookies)
            {
                if(cookie.getName().equals(name))
                {
                    return cookie;
                }
            }
        }

        return null;
    }
}
