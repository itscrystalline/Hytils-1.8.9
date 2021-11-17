package com.iwant2tryhard.hytils.core;

import com.iwant2tryhard.hytils.Hytils;
import com.iwant2tryhard.hytils.gui.GamesMenuGui;
import com.iwant2tryhard.hytils.gui.HytilsConfigScreen;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Events {


    Frustum frustum = new Frustum();

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onJoin(TickEvent.RenderTickEvent event){
        if (Hytils.instance.mc != null){
            if (Hytils.instance.mc.currentScreen != null){
                if (Hytils.instance.mc.currentScreen instanceof GuiDownloadTerrain){
                    Hytils.instance.getUtils().teammates.clear();
                    Hytils.instance.getUtils().displayTeammates.clear();
                    Hytils.instance.getUtils().prevInitialAbsorption.clear();
                    Hytils.instance.getUtils().tntList.clear();
                    Hytils.instance.getUtils().highlightList.clear();
                    System.out.println("cleared");
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onWorldRender(RenderWorldLastEvent event){
        /*if (Hytils.instance.mc != null){
            if (!Hytils.instance.getUtils().highlightList.isEmpty()){
                List<Map<Integer, BlockPos>> thisTntSet = Hytils.instance.getUtils().highlightList;
                for (Map<Integer, BlockPos> tntSet : thisTntSet){
                    for (Map.Entry<Integer, BlockPos> entry: tntSet.entrySet()) {
                        int distance = entry.getKey();
                        BlockPos pos = entry.getValue();


                        Entity viewer = Hytils.instance.mc.getRenderViewEntity();
                        frustum.setPosition(viewer.posX, viewer.posY, viewer.posZ);
                        if (!frustum.isBoxInFrustum(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, 255, pos.getZ() + 1)){
                            continue;
                        }

                        double viewerX = viewer.lastTickPosX + (viewer.posX - viewer.lastTickPosX) * event.partialTicks;
                        double viewerY = viewer.lastTickPosY + (viewer.posY - viewer.lastTickPosY) * event.partialTicks;
                        double viewerZ = viewer.lastTickPosZ + (viewer.posZ - viewer.lastTickPosZ) * event.partialTicks;

                        double x = pos.getX() - viewerX;
                        double y = pos.getY() - viewerY;
                        double z = pos.getZ() - viewerZ;

                        Color color = Color.GREEN;

                        GlStateManager.disableDepth();
                        GlStateManager.disableCull();

                        if (frustum.isBoxInFrustum(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1) && distance == 0) {
                            RenderUtils.drawFilledBoundingBox(new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1), Color.BLUE, 0.4f);
                        }

                        if (Hytils.instance.mc.theWorld.getBlockState(pos).getBlock() != Blocks.air ||
                                Hytils.instance.mc.theWorld.getBlockState(pos).getBlock() != Blocks.water ||
                                Hytils.instance.mc.theWorld.getBlockState(pos).getBlock() != Blocks.lava){
                            if (frustum.isBoxInFrustum(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1)) {
                                RenderUtils.drawFilledBoundingBox(new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1), color, 0.4f);
                            }
                        }
                    }
                }
            }
        }*/
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onTickWorld(TickEvent.WorldTickEvent event) {
//        Hytils.instance.getUtils().detectTNT();
//        for (EntityTNTPrimed tnt : Hytils.instance.getUtils().tntList) {
//            Map<Integer, BlockPos> tntPos = new HashMap<Integer, BlockPos>();
//            BlockPos nearest = Utils.getNearestBlock(tnt.posX, tnt.posY, tnt.posZ);
//            tntPos.put(0, nearest);
//            List<BlockPos> inExplosionRange = Utils.getSphere(Utils.getNearestBlock(tnt.posX, tnt.posY, tnt.posZ), 15, true);
//            for (BlockPos pos : inExplosionRange) {
//                tntPos.put((int) Math.round(Utils.distanceOf(tnt.posX, tnt.posY, tnt.posZ, pos.getX(), pos.getY(), pos.getZ())), pos);
//            }
//            Hytils.instance.getUtils().highlightList.add(tntPos);
//
//            /*for (Map<Integer, BlockPos> map : Hytils.instance.getUtils().highlightList){
//                BlockPos pos = map.get(0);
//                if (pos.getX() != nearest.getX() || pos.getY() != nearest.getY() || pos.getZ() != nearest.getZ()){
//                    Hytils.instance.getUtils().highlightList.remove(map);
//                }
//            }*/
//        }
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onTickClient(TickEvent.ClientTickEvent event) {
        if (Hytils.instance.getUtils().currentGame == null && Hytils.instance.getUtils().isInHypixelLobby()) {
            Hytils.instance.getUtils().currentGame = GameTypes.NOT_IN_GAME;
        }

        if (Hytils.instance.getUtils().lastPlayerInteractedTimer <= 0) {
            Hytils.instance.getUtils().lastPlayerNameInteracted = null;
        }

        --Hytils.instance.getUtils().lastPlayerInteractedTimer;

        if (Hytils.instance.getShowConfigScreen()) {
            Hytils.instance.mc.displayGuiScreen(new HytilsConfigScreen());
            Hytils.instance.setShowConfigScreen(false);
        }

        if (Hytils.instance.getShowGamesMenuScreen()) {
            Hytils.instance.mc.displayGuiScreen(new GamesMenuGui());
            Hytils.instance.setShowGamesMenuScreen(false);
        }

        if (Hytils.instance.mc.theWorld == null) {
            Hytils.instance.getDiscordRPC().update("Idle", "In Menu Screen", "logomenuhires", "In Menu Screen", "logoshphrehires", "Hytils " + Hytils.VERSION + " by IWant2TryHard");
        } else {
            if (Hytils.instance.mc.isSingleplayer()) {
                Hytils.instance.getDiscordRPC().update("Singleplayer", "", "logosingleplayerhires", "Singleplayer", "logoshphrehires", "Hytils " + Hytils.VERSION + " by IWant2TryHard");
            } else {
                if (Hytils.instance.mc.getCurrentServerData() != null) {
                    String ip = Hytils.instance.mc.getCurrentServerData().serverIP.toLowerCase();
                    if (Hytils.instance.getUtils().isOnHypixel()) {
                        Hytils.instance.getDiscordRPC().update("Multiplayer - " + ip,
                                "In " + (Hytils.instance.getUtils().currentGame != null ? (Hytils.instance.getUtils().currentGame == GameTypes.NOT_IN_GAME ? "Lobby" : Hytils.instance.getUtils().currentGame.userFriendlyName) : "Unknown")
                                , "logomultiplayerhypixelhires", "Hypixel - In " + (Hytils.instance.getUtils().currentGame != null ? (Hytils.instance.getUtils().currentGame == GameTypes.NOT_IN_GAME ? "Lobby" : Hytils.instance.getUtils().currentGame.userFriendlyName) : "Unknown"),
                                "logoshphrehires", "Hytils " + Hytils.VERSION + " by IWant2TryHard");
                    } else {
                        Hytils.instance.getDiscordRPC().update("Multiplayer - " + ip, "", "logomultiplayerhires", "Multiplayer - " + ip, "logoshphrehires", "Hytils " + Hytils.VERSION + " by IWant2TryHard");
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onHit(AttackEntityEvent event) {
        if (event.target != null) {
            if (event.target instanceof EntityPlayer) {
                String name = ((EntityPlayer) event.target).getDisplayNameString();
                if (name.startsWith("\u00a7")) {
                    name = name.replace("\u00a7", "");
                    name = name.substring(1);
                }
                Hytils.instance.getUtils().lastPlayerNameInteracted = name;
                Hytils.instance.getUtils().lastPlayerInteractedTimer = 100;
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onHurt(LivingHurtEvent event) {
        if (event.entity != null) {
            if (event.entity instanceof EntityPlayer) {
                if (event.source.getEntity() != null && event.source.getEntity() instanceof EntityPlayer) {
                    String playerName = ((EntityPlayer) event.entity).getDisplayNameString();
                    if (playerName.startsWith("\u00a7")) {
                        playerName = playerName.replace("\u00a7", "");
                        playerName = playerName.substring(1);
                    }
                    String myName = Hytils.instance.mc.thePlayer.getDisplayNameString();
                    if (myName.startsWith("\u00a7")) {
                        myName = myName.replace("\u00a7", "");
                        myName = myName.substring(1);
                    }
                    String attackerName = ((EntityPlayer) event.source.getEntity()).getDisplayNameString();
                    if (attackerName.startsWith("\u00a7")) {
                        attackerName = attackerName.replace("\u00a7", "");
                        attackerName = attackerName.substring(1);
                    }
                    if (playerName.equals(myName)) {
                        Hytils.instance.getUtils().lastPlayerNameInteracted = attackerName;
                        Hytils.instance.getUtils().lastPlayerInteractedTimer = 100;
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onChat(ClientChatReceivedEvent event){
        if (event.type == 1){
            if (event.message.getUnformattedText().equalsIgnoreCase("right click with the compass in a lobby to select a game!")){
                event.message = new ChatComponentText(EnumChatFormatting.RED + "Mentioned game either does not exist or is not currently available!");
            }
            if (event.message.getUnformattedText().contains("to access powerful upgrades.")){
                Hytils.instance.getUtils().detectTeammates();
                System.out.println("automatically checking teammates...");
            }
        }
    }
}
