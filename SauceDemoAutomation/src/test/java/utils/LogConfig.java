package utils;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

public class LogConfig {
	public static void initLogs() {
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date());
        String logFileName = "logs/log_" + timeStamp + ".log";

        Properties props = new Properties();
        props.setProperty("log4j.rootLogger", "DEBUG, file, console");

        // Console appender
        props.setProperty("log4j.appender.console", "org.apache.log4j.ConsoleAppender");
        props.setProperty("log4j.appender.console.layout", "org.apache.log4j.PatternLayout");
        props.setProperty("log4j.appender.console.layout.ConversionPattern", "%d{yyyy-MM-dd HH:mm:ss} [%p] %c - %m%n");

        // File appender with unique log file
        props.setProperty("log4j.appender.file", "org.apache.log4j.FileAppender");
        props.setProperty("log4j.appender.file.File", logFileName);
        props.setProperty("log4j.appender.file.Append", "false");
        props.setProperty("log4j.appender.file.layout", "org.apache.log4j.PatternLayout");
        props.setProperty("log4j.appender.file.layout.ConversionPattern", "%d{yyyy-MM-dd HH:mm:ss} [%p] %c - %m%n");

        PropertyConfigurator.configure(props);
    }
}

