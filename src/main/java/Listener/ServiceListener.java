package Listener;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Listener.ListenerObject.TakonParam;

public class ServiceListener implements ServletContextListener{
	 private final Logger logger = LoggerFactory.getLogger(ServiceListener.class);

	    public void contextDestroyed(ServletContextEvent arg0) {}

	    /*
	     * (non-Javadoc)
	     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	     */
	    public void contextInitialized(ServletContextEvent context) {
	    	
	    	try {
	    		TakonParam tp=new TakonParam(context);
	    		tp.setup();
			} catch (Exception e) {
				logger.error("failed to start portal and exit",e);
				System.exit(-1);
			}

	    }
}
