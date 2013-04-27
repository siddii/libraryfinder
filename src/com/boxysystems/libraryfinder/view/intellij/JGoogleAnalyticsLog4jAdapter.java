package com.boxysystems.libraryfinder.view.intellij;

import com.boxysystems.jgoogleanalytics.LoggingAdapter;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: SHAMEED
 * Date: Mar 29, 2008
 * Time: 1:53:18 PM
 */
public class JGoogleAnalyticsLog4jAdapter implements LoggingAdapter {

  private static Logger logger = Logger.getLogger(JGoogleAnalyticsLog4jAdapter.class);

  public void logError(String errorMessage) {
    logger.error(errorMessage);
  }

  public void logMessage(String message) {
    logger.info(message);
  }
}
