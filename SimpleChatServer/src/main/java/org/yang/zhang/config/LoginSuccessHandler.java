package org.yang.zhang.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 05 11 17:57
 */

public class LoginSuccessHandler implements AuthenticationSuccessHandler,InitializingBean {

    protected Log logger = LogFactory.getLog(getClass());

    private String defaultTargetUrl;

    private boolean forwardToDestination = false;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();


    /* (non-Javadoc)
     * @see org.springframework.security.web.authentication.AuthenticationSuccessHandler#onAuthenticationSuccess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
     */
    @Override
    @Transactional(readOnly=false,propagation= Propagation.REQUIRED,rollbackFor={Exception.class})
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        if(this.forwardToDestination){
            logger.info("Login success,Forwarding to "+this.defaultTargetUrl);

            request.getRequestDispatcher(this.defaultTargetUrl).forward(request, response);
        }else{
            logger.info("Login success,Redirecting to "+this.defaultTargetUrl);

            this.redirectStrategy.sendRedirect(request, response, this.defaultTargetUrl);
        }
    }

    public void setDefaultTargetUrl(String defaultTargetUrl) {
        this.defaultTargetUrl = defaultTargetUrl;
    }

    public void setForwardToDestination(boolean forwardToDestination) {
        this.forwardToDestination = forwardToDestination;
    }

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if(StringUtils.isEmpty(defaultTargetUrl))
            throw new Exception("You must configure defaultTargetUrl");

    }
}
