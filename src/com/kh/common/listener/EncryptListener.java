package com.kh.common.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.kh.common.encrypt.AESEncrypt;

/**
 * Application Lifecycle Listener implementation class EncryptListener
 *
 */
@WebListener
public class EncryptListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public EncryptListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
//    	System.out.println(arg0.getServletContext().getRealPath("/WEB-INF"));
    	String path=arg0.getServletContext().getRealPath("/WEB-INF");//context객체
    	//서버가 실행되었을 때 암호화를 하겠다
    	new AESEncrypt(path);
    }
	
}
