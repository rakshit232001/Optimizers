package utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.apache.logging.log4j.core.config.builder.impl.DefaultConfigurationBuilder;

/**
 * Dynamic Log4j2 configuration with timestamped log file.
 */
public class LogConfig {

    public static void initLogs() {
        try {
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            String logDir = "logs";
            String logFileName = logDir + "/log_" + timeStamp + ".log";

            // Ensure "logs" directory exists
            Files.createDirectories(Paths.get(logDir));

            // Build Log4j2 configuration programmatically
            ConfigurationBuilder<BuiltConfiguration> builder = new DefaultConfigurationBuilder<>();

            builder.setStatusLevel(org.apache.logging.log4j.Level.ERROR);
            builder.setConfigurationName("DynamicLogConfig");

            // Console Appender
            AppenderComponentBuilder console = builder.newAppender("Console", "CONSOLE")
                    .addAttribute("target", org.apache.logging.log4j.core.appender.ConsoleAppender.Target.SYSTEM_OUT);
            console.add(builder.newLayout("PatternLayout")
                    .addAttribute("pattern", "%d{yyyy-MM-dd HH:mm:ss} [%p] %c - %m%n"));
            builder.add(console);

            // File Appender
            AppenderComponentBuilder file = builder.newAppender("File", "FILE")
                    .addAttribute("fileName", logFileName)
                    .addAttribute("append", false);
            file.add(builder.newLayout("PatternLayout")
                    .addAttribute("pattern", "%d{yyyy-MM-dd HH:mm:ss} [%p] %c - %m%n"));
            builder.add(file);

            // Root logger
            builder.add(builder.newRootLogger(org.apache.logging.log4j.Level.DEBUG)
                    .add(builder.newAppenderRef("Console"))
                    .add(builder.newAppenderRef("File")));

            // Apply configuration
            LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
            ctx.start(builder.build());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
