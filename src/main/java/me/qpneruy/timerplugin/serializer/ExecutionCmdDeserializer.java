package me.qpneruy.timerplugin.serializer;

import com.google.gson.*;
import me.qpneruy.timerplugin.Task.ExecutionCmd;

import java.lang.reflect.Type;

public class ExecutionCmdDeserializer implements JsonDeserializer<ExecutionCmd> {
    @Override
    public ExecutionCmd deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        ExecutionCmd cmd = new ExecutionCmd();
        cmd.setCommand(jsonObject.get("command").getAsString());
        cmd.setStartTime(jsonObject.get("startTime").getAsString());
        if (jsonObject.has("endTime")) {
            cmd.setEndTime(jsonObject.get("endTime").getAsString());
        }
        if (jsonObject.has("eachHours")) {
            cmd.setEachHour(jsonObject.get("eachHours").getAsBoolean());
        }
        if (jsonObject.has("eachMinutes")) {
            cmd.setEachMinutes(jsonObject.get("eachMinutes").getAsBoolean());
        }
        return cmd;
    }
}
