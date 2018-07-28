package utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by Dubai on 10/9/2017.
 */
public class DefaultLoggerUtils {

    private Log log = null;

    public DefaultLoggerUtils(){

        if (log == null)
        {

            log = LogFactory.getLog(DefaultLoggerUtils.class);
        }

    }
    public void setLog(String message,int status,Throwable t ){


        //message d'information
        if (status == 0 && t == null){

            this.log.info(message);
        }
        if (status == 0 && t != null){
            this.log.info(message,t);
        }
        //message debug
        if (status == 1 && t == null){

            this.log.debug(message);
        }
        if (status == 1 && t != null){
            this.log.debug(message,t);
        }

        //message warning
        if (status == 2 && t == null){

            this.log.warn(message);
        }
        if (status == 2 && t != null){
            this.log.warn(message,t);
        }
        //message debug
        if (status == 3 && t == null){

            this.log.error(message);
        }
        if (status == 3 && t != null){
            this.log.error(message,t);
        }
    }
}
