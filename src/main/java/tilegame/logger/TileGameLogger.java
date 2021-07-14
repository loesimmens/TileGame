package tilegame.logger;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class TileGameLogger {

    private static final Logger logger;

    static {
        logger = Logger.getLogger("TileGameLog");
        FileHandler fh;

        LocalDateTime dateTime = LocalDateTime.now();
        String logDate = dateTime.toString();
        logDate = logDate.replace(":","");

        try {
            fh = new FileHandler("target/logging/log-" + logDate + ".log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    private TileGameLogger () {}

    public static Logger getLogger () {
        return logger;
    }
}
