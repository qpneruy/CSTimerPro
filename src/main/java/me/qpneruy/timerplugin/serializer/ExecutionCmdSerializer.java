package me.qpneruy.timerplugin.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import me.qpneruy.timerplugin.Task.ExecutionCmd;

import java.lang.reflect.Type;

public class ExecutionCmdSerializer implements JsonSerializer<ExecutionCmd> {
    @Override
    public JsonElement serialize(ExecutionCmd src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("command", src.getCommand());
        jsonObject.addProperty("startTime", src.getStartTime());
        if (!src.getStartTime().equals(src.getEndTime())) {
            jsonObject.addProperty("endTime", src.getEndTime());
        }
        if (src.isEachHour()) {
            jsonObject.addProperty("eachHours", src.isEachHour());
        }
        if (src.isEachMinute()) {
            jsonObject.addProperty("eachMinutes", src.isEachMinute());
        }
        if (src.isEnabled()) {
            jsonObject.addProperty("isEnable", src.isEnabled());
        }
        return jsonObject;
    }
}
