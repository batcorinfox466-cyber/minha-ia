package com.SolanaDevMinecraft;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigManager {
    public static String DB_URL = "jdbc:mysql://localhost:3306/banco";
    public static String DB_USER = "root";
    public static String DB_PASSWORD = "password";
    public static boolean DB_USE_SSL = false;
    public static boolean DB_VERIFY_CERT = true;

    public static String DOCKER_HOST = "localhost/web_sol/panda_full";
    public static String API_WEB_KEY = "b493d48364afe44d";
    public static String SOLANA_COMMAND = "solana";
    public static String BASE_PATH = "/home/astral/astralcoin";
    public static String WALLET_BANK = "3uiJx2GHu5qV43BEJ8hHMiGYYHQG7ZTzTzMyhiNQ1Fp4";

    public static int CONVERSION_RATE = 1000;
    
    public static int PRICE_APPLE = 500;
    public static int PRICE_EMERALD = 250;
    public static int PRICE_NETHER_RELIC = 1000;
    public static int PRICE_THOR_AXE = 60000;
    public static int PRICE_BOOT_RELIC = 40000;
    public static int PRICE_WING_RELIC = 50000;

    public static void loadConfig() {
        File configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), "solanaforge.json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        if (configFile.exists()) {
            try (FileReader reader = new FileReader(configFile)) {
                JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
                if (json.has("Database")) {
                    JsonObject db = json.getAsJsonObject("Database");
                    if (db.has("url")) DB_URL = db.get("url").getAsString();
                    if (db.has("user")) DB_USER = db.get("user").getAsString();
                    if (db.has("password")) DB_PASSWORD = db.get("password").getAsString();
                    if (db.has("use_ssl")) DB_USE_SSL = db.get("use_ssl").getAsBoolean();
                    if (db.has("verify_server_certificate")) DB_VERIFY_CERT = db.get("verify_server_certificate").getAsBoolean();
                }
                if (json.has("Docker")) {
                    JsonObject docker = json.getAsJsonObject("Docker");
                    if (docker.has("host")) DOCKER_HOST = docker.get("host").getAsString();
                    if (docker.has("api_web_key")) API_WEB_KEY = docker.get("api_web_key").getAsString();
                    if (docker.has("solana_command")) SOLANA_COMMAND = docker.get("solana_command").getAsString();
                    if (docker.has("base_path")) BASE_PATH = docker.get("base_path").getAsString();
                    if (docker.has("wallet_bank_store_admin")) WALLET_BANK = docker.get("wallet_bank_store_admin").getAsString();
                }
                if (json.has("Store")) {
                    JsonObject store = json.getAsJsonObject("Store");
                    if (store.has("value_of_in_game_currency")) CONVERSION_RATE = store.get("value_of_in_game_currency").getAsInt();
                    if (store.has("price_apple")) PRICE_APPLE = store.get("price_apple").getAsInt();
                    if (store.has("price_emerald")) PRICE_EMERALD = store.get("price_emerald").getAsInt();
                    if (store.has("price_nether_relic")) PRICE_NETHER_RELIC = store.get("price_nether_relic").getAsInt();
                    if (store.has("price_thor_axe")) PRICE_THOR_AXE = store.get("price_thor_axe").getAsInt();
                    if (store.has("price_boot_relic")) PRICE_BOOT_RELIC = store.get("price_boot_relic").getAsInt();
                    if (store.has("price_wing_relic")) PRICE_WING_RELIC = store.get("price_wing_relic").getAsInt();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JsonObject root = new JsonObject();
            
            JsonObject db = new JsonObject();
            db.addProperty("url", DB_URL);
            db.addProperty("user", DB_USER);
            db.addProperty("password", DB_PASSWORD);
            db.addProperty("use_ssl", DB_USE_SSL);
            db.addProperty("verify_server_certificate", DB_VERIFY_CERT);
            root.add("Database", db);

            JsonObject docker = new JsonObject();
            docker.addProperty("host", DOCKER_HOST);
            docker.addProperty("api_web_key", API_WEB_KEY);
            docker.addProperty("solana_command", SOLANA_COMMAND);
            docker.addProperty("base_path", BASE_PATH);
            docker.addProperty("wallet_bank_store_admin", WALLET_BANK);
            root.add("Docker", docker);

            JsonObject store = new JsonObject();
            store.addProperty("value_of_in_game_currency", CONVERSION_RATE);
            store.addProperty("price_apple", PRICE_APPLE);
            store.addProperty("price_emerald", PRICE_EMERALD);
            store.addProperty("price_nether_relic", PRICE_NETHER_RELIC);
            store.addProperty("price_thor_axe", PRICE_THOR_AXE);
            store.addProperty("price_boot_relic", PRICE_BOOT_RELIC);
            store.addProperty("price_wing_relic", PRICE_WING_RELIC);
            root.add("Store", store);

            try (FileWriter writer = new FileWriter(configFile)) {
                gson.toJson(root, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
