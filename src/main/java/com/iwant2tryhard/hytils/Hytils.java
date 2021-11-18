package com.iwant2tryhard.hytils;

import com.iwant2tryhard.hytils.commands.CheckStateCommand;
import com.iwant2tryhard.hytils.commands.CheckTntCommand;
import com.iwant2tryhard.hytils.commands.ClearTeammatesCommand;
import com.iwant2tryhard.hytils.commands.GamesMenuCommand;
import com.iwant2tryhard.hytils.commands.HytilsConfigCommand;
import com.iwant2tryhard.hytils.commands.PlayerCheckCommand;
import com.iwant2tryhard.hytils.commands.QueuePreviousCommand;
import com.iwant2tryhard.hytils.commands.QuickQueueCommand;
import com.iwant2tryhard.hytils.commands.ReDetectTeammatesCommand;
import com.iwant2tryhard.hytils.commands.ReQueueCommand;
import com.iwant2tryhard.hytils.commands.ViewTeammatesCommand;
import com.iwant2tryhard.hytils.core.Events;
import com.iwant2tryhard.hytils.core.MainConfig;
import com.iwant2tryhard.hytils.core.Utils;
import com.iwant2tryhard.hytils.core.discord.HytilsDiscordRPCCore;
import com.iwant2tryhard.hytils.gui.*;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.lwjgl.opengl.Display;

@Mod(modid = Hytils.MODID, version = Hytils.VERSION)
public class Hytils
{
    public static final String MODID = "hytils";
    public static final String VERSION = "0.22a";

    @Mod.Instance(Hytils.MODID)
    public static Hytils instance;

    public Minecraft mc = Minecraft.getMinecraft();

    private final Utils utils = new Utils();
    private MainConfig config;
    public HytilsDiscordRPCCore discordRPC = new HytilsDiscordRPCCore();

    private boolean showConfigScreen = false;
    private boolean showGamesMenuScreen = false;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        instance = this;
        config = new MainConfig(event.getSuggestedConfigurationFile());
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new TeammateHUD());
        MinecraftForge.EVENT_BUS.register(new GunAmmoHUD());
        MinecraftForge.EVENT_BUS.register(new OnScreenHUD());
        MinecraftForge.EVENT_BUS.register(new InCombatHUD());
        MinecraftForge.EVENT_BUS.register(new ToolHelperHUD());
        MinecraftForge.EVENT_BUS.register(new Events());
        ClientCommandHandler.instance.registerCommand(new GamesMenuCommand());
        ClientCommandHandler.instance.registerCommand(new QuickQueueCommand());
        ClientCommandHandler.instance.registerCommand(new ReQueueCommand());
        ClientCommandHandler.instance.registerCommand(new HytilsConfigCommand());
        ClientCommandHandler.instance.registerCommand(new QueuePreviousCommand());
        ClientCommandHandler.instance.registerCommand(new PlayerCheckCommand());
        ClientCommandHandler.instance.registerCommand(new ViewTeammatesCommand());
        ClientCommandHandler.instance.registerCommand(new ClearTeammatesCommand());
        ClientCommandHandler.instance.registerCommand(new ReDetectTeammatesCommand());
        ClientCommandHandler.instance.registerCommand(new CheckTntCommand());
        ClientCommandHandler.instance.registerCommand(new CheckStateCommand());
        utils.runLobbyCheckerTimer();
        config.loadConfig();
        discordRPC.start();
        Display.setTitle("Hytils " + Hytils.VERSION + (Integer.parseInt(Hytils.VERSION.substring(2, Hytils.VERSION.length() - 1)) % 10f == 0 ? " - Release" : ""));
        //HytilsDiscordRPCCore.startRPC();
        System.out.println("attempted to start discord rpc");
        Runtime.getRuntime().addShutdownHook(new Thread("shutdown") {
            @Override
            public void run() {
                discordRPC.stop();
            }
        });
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }

    public Utils getUtils() {
        return utils;
    }

    public MainConfig getConfig() {
        return config;
    }

    public HytilsDiscordRPCCore getDiscordRPC() {
        return discordRPC;
    }

    public boolean getShowConfigScreen() {
        return showConfigScreen;
    }

    public void setShowConfigScreen(boolean showConfigScreen) {
        this.showConfigScreen = showConfigScreen;
    }

    public boolean getShowGamesMenuScreen() {
        return showGamesMenuScreen;
    }

    public void setShowGamesMenuScreen(boolean showGamesMenuScreen) {
        this.showGamesMenuScreen = showGamesMenuScreen;
    }
}
