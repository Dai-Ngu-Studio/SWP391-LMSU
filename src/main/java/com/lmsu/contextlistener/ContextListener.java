package com.lmsu.contextlistener;

import java.io.File;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.lmsu.utils.ScheduleHelper;
import org.apache.log4j.PropertyConfigurator;

@WebListener("application context listener")
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // initialize log4j
        ServletContext context = sce.getServletContext();
        String log4jConfigFile = context.getInitParameter("log4j-config-location");
        String fullPath = context.getRealPath("") + File.separator + log4jConfigFile;
        PropertyConfigurator.configure(fullPath);

        //System Handler
        ScheduleHelper.dailyTask();
        ScheduleHelper.weeklyTask();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //do nothing
    }
}
