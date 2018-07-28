package utils;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;

import java.io.File;

/**
 * Created by Dubai on 10/9/2017.
 */
public class Log4jLoggerUtils {

 private  Logger logger;



 public Logger getLogger() {
  return logger;
 }

 public void setLogger(Logger logger) {

  this.logger = logger;
 }

 public Log4jLoggerUtils(){


  LoggerContext context = (LoggerContext) LogManager.getContext(false);
  File file = new File(ClassLoader.getSystemResource("main/ressources/log4j2.xml").getPath());
  context.setConfigLocation(file.toURI());

  this.logger = context.getLogger(Log4jLoggerUtils.class.getName());

 }



    //private static final Logger

}
