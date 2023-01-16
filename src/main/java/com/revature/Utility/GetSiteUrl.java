package com.revature.Utility;

import javax.servlet.http.HttpServletRequest;

public class GetSiteUrl {
    public static String getSiteURL(HttpServletRequest request){
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(),"");
    }
}
