package ru.pinkgoosik.visuality.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.pinkgoosik.visuality.VisualityMod;

import java.io.*;

@SuppressWarnings("unused")
public class ConfigReader {

    public static Config read(){
        try{
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            BufferedReader reader = new BufferedReader(new FileReader("config/visuality.json"));
            //migrateToV1();
            return gson.fromJson(reader, Config.class);
        } catch (FileNotFoundException e) {
            createDefault();
            return new Config();
        }
    }

    private static void createDefault(){
        try {
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            File dir = new File("config");
            if (!dir.exists()){
                boolean bl = dir.mkdirs();
            }
            FileWriter writer = new FileWriter("config/visuality.json");
            writer.write(gson.toJson(new Config()));
            writer.close();
        } catch (IOException e) {
            VisualityMod.LOGGER.info("Failed to create config due to an exception: " + e);
        }
    }
}
