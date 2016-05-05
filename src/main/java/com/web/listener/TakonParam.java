package com.web.listener;


import javax.servlet.ServletContextEvent;

import com.constants.SystemParamMapping;
import com.constants.Properties.SystemParam;


public class TakonParam {
	
	private ServletContextEvent servletContextEvent;
	
	public TakonParam(ServletContextEvent servletContextEvent){
		this.servletContextEvent = servletContextEvent;
	}


	/**
     * 设置环境变量
     */
    public void setup() {
    	try{
    		//获得是绝对路径
    		//D:\Java\eclipseWorkspace\mainWork\.metadata\.plugins\
    		//org.eclipse.wst.server.core\tmp0\wtpwebapps\MavenCodes\
            String rootPath =
                    servletContextEvent.getServletContext().getRealPath("/");
            SystemParam.setProperty(SystemParamMapping.WAR_HOME_PATH, rootPath);
        } catch(Exception e){
        	e.printStackTrace();
        }
        	finally {
        }
    }
}
