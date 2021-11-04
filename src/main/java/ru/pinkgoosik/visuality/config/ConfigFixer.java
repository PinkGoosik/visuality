package ru.pinkgoosik.visuality.config;

import com.google.gson.*;
import ru.pinkgoosik.goosikconfig.api.Config;

import java.io.*;

public class ConfigFixer {

    public static void fix(Config config, int currentVersion){
        try{
            BufferedReader reader = new BufferedReader(new FileReader("config/visuality.json"));
            JsonParser parser = new JsonParser();
            JsonObject object = parser.parse(reader).getAsJsonObject();
            JsonElement oldVersion = object.get("version");
            if(oldVersion != null){
                try{
                    int i = oldVersion.getAsInt();
                    if(i != currentVersion){
                        config.getReader().generate(config);
                    }
                }catch (ClassCastException e){
                    config.getReader().generate(config);
                }
            }
        } catch (FileNotFoundException ignored) {}
    }
}
