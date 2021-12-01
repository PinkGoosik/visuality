package ru.pinkgoosik.visuality.config;

import com.google.gson.*;
import ru.pinkgoosik.goosikconfig.api.Config;
import ru.pinkgoosik.goosikconfig.impl.ConfigGenerator;

import java.io.*;

public class ConfigFixer {

    public static void fix(Config config, int currentVersion){
        try{
            BufferedReader reader = new BufferedReader(new FileReader("config/visuality.json"));
            JsonObject object = JsonParser.parseReader(reader).getAsJsonObject();
            JsonElement oldVersion = object.get("version");
            if(oldVersion != null){
                try{
                    int i = oldVersion.getAsInt();
                    if(i != currentVersion){
                        ConfigGenerator.generate(config);
                    }
                }catch (ClassCastException e){
                    ConfigGenerator.generate(config);
                }
            }
        } catch (FileNotFoundException ignored) {}
    }
}
