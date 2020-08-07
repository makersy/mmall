package com.mmall.util;

import com.github.pagehelper.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by makersy on 2019
 */

@Slf4j
public class CookieUtil {

    //tomcat8.5之后domain开头不要加 .
    private final static String COOKIE_DOMAIN = "makersy.top";
    private final static String COOKIE_NAME = "mmall_login_token";

    /**
     * 读取登录cookie
     * @param request
     * @return
     */
    public static String readLoginToken(HttpServletRequest request) {
        Cookie[] cks = request.getCookies();
        if (cks != null) {
            for (Cookie ck : cks) {
                log.info("read cookieName:{}, cookieValue:{}",ck.getName(), ck.getValue());
                if (StringUtils.equals(ck.getName(), COOKIE_NAME)) {
                    log.info("return cookieName:{}, cookieValue:{}", ck.getName(), ck.getValue());
                    return ck.getValue();
                }
            }
        }
        return null;
    }

    /*
    a:A
     */
    /**
     * 向用户浏览器写入cookie
     * @param response
     * @param token
     */
    public static void writeLoginToken(HttpServletResponse response, String token) {
        Cookie ck = new Cookie(COOKIE_NAME, token);
        ck.setDomain(COOKIE_DOMAIN);
        ck.setPath("/");//代表设置在根目录
        ck.setHttpOnly(true);//防止脚本攻击
        //cookie存活周期，单位是s
        //若是不设置这个，cookie就不会写入硬盘，而是写在内存，只在当前页面有效
        ck.setMaxAge(60 * 60 * 24 * 365);//设置有效期1年。若是-1，则是永久
        log.info("write cookieName:{}, cookieValue:{}", ck.getName(), ck.getValue());
        response.addCookie(ck);
    }

    /**
     * 删除浏览器的cookie
     * @param request
     * @param response
     */
    public static void delLoginToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cks = request.getCookies();
        if (cks != null) {
            for (Cookie ck : cks) {
                if (StringUtils.equals(ck.getName(), COOKIE_NAME)) {
                    ck.setDomain(COOKIE_DOMAIN);
                    ck.setPath("/");
                    ck.setMaxAge(0);//0--代表删除此cookie
                    log.info("del cookieName:{}, cookieValue:{}", ck.getName(), ck.getValue());
                    //若if命中，返回一个有效期为0的cookie给浏览器，浏览器就会把这个cookie删除掉
                    response.addCookie(ck);
                    return;
                }
            }
        }
    }
}
