package com.SolanaDevMinecraft;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

public class SolanaForge implements ModInitializer {
    public static final String MODID = "solanaforge";
    private static final Logger LOGGER = LogManager.getLogger();

    private static DatabaseManager databaseManager;
    private static SolanaManager solanaManager;
    private static StoreManager storeManager;
    private static HomeManager homeManager;
    private static TeleportManager teleportManager;
    private static ChestLockManager chestLockManager;
    private static SolanaCommands solanaCommands;

    @Override
    public void onInitialize() {
        LOGGER.info("Solana Fabric 1.20.1 iniciado!");

        ConfigManager.loadConfig();
        
        // Configuração de Eventos
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            LOGGER.info("SolanaForge Iniciando no Fabric!");
        });

        // Registrar sleep event
        net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents.START_SLEEPING.register((entity, sleepingPos) -> {
            if (entity instanceof net.minecraft.server.level.ServerPlayer player) {
                if (HomeManager.instance != null) {
                    HomeManager.instance.setHome(player, "default");
                    player.sendSystemMessage(net.minecraft.network.chat.Component.translatable("solanaforge.message.home.bed_saved"));
                }
            }
        });

        // Registrar death event
        net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents.ALLOW_DEATH.register((entity, damageSource, damageMultiplier) -> {
            if (entity instanceof net.minecraft.server.level.ServerPlayer player) {
                if (teleportManager != null) {
                    teleportManager.saveLastLocation(player);
                }
            }
            return true;
        });

        databaseManager = new DatabaseManager(
                ConfigManager.DB_URL,
                ConfigManager.DB_USER,
                ConfigManager.DB_PASSWORD
        );
        databaseManager.setupTables();
        
        solanaManager = new SolanaManager(databaseManager);
        storeManager = new StoreManager(databaseManager);
        homeManager = new HomeManager(databaseManager);
        teleportManager = new TeleportManager();
        chestLockManager = new ChestLockManager(databaseManager);
        solanaCommands = new SolanaCommands(solanaManager, storeManager, homeManager, teleportManager, chestLockManager);
        
        PlayerJoinHandler.init(storeManager, solanaManager);
        ChestLockManager.init();
        RelicEffectsHandler.init();
        
        ItemInit.registerItems();

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            if (solanaCommands != null) {
                solanaCommands.register(dispatcher);
            }
        });
    }
}
