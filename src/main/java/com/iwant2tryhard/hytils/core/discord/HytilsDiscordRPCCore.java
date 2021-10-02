package com.iwant2tryhard.hytils.core.discord;

import com.iwant2tryhard.hytils.Hytils;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.ReadyCallback;

public class HytilsDiscordRPCCore {

    private boolean running = true;
    private long created = 0L;

    public void start() {
        this.created = System.currentTimeMillis();

        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler(new ReadyCallback() {
            @Override
            public void apply(DiscordUser discordUser) {
                System.out.println("[Hytils] Discord RPC service requested logon with user: " + discordUser.username + "#" + discordUser.discriminator);
                update("Launching Hytils", "", "logohires", "Hytils " + Hytils.VERSION + " by IWant2TryHard", "", "");
            }
        }).build();

        DiscordRPC.discordInitialize("892999215515578379", handlers, true);
        System.out.println("[Hytils] Discord RPC logon initialized");

        new Thread("HytilsDiscordRPCCallback") {
            @Override
            public void run() {
                while (running) {
                    DiscordRPC.discordRunCallbacks();
                }
            }
        }.start();
    }

    public void stop() {
        running = false;
        DiscordRPC.discordShutdown();
    }

    public void update(String details, String state, String bigImageKey, String bigImageText, String smallImageKey, String smallImageText) {
        DiscordRichPresence.Builder b = new DiscordRichPresence.Builder(state);
        b.setBigImage(bigImageKey, bigImageText);
        b.setSmallImage(smallImageKey, smallImageText);
        b.setDetails(details);
        b.setStartTimestamps(created);

        DiscordRPC.discordUpdatePresence(b.build());
    }

}
