package me.qpneruy.timerplugin.Task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JsonValidator {
    private static final Logger logger = Logger.getLogger(archiver.class.getName());
    public static boolean isValidJson(String jsonFilePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.readTree(new File(jsonFilePath));
            return true;
        } catch (JsonProcessingException e) {
            logger.log (Level.SEVERE, + e.getLocation().getLineNr() + ", column " + e.getLocation().getColumnNr());
            return false;
        } catch (IOException e) {
            logger.log (Level.SEVERE,"IO Error: " + e.getMessage());
            return false;
        }
    }
}
