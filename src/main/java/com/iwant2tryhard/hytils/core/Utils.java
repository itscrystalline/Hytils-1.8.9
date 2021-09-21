package com.iwant2tryhard.hytils.core;

import com.iwant2tryhard.hytils.Hytils;
import jdk.nashorn.internal.ir.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Utils {
    private static String COMPASS_NAME = EnumChatFormatting.GREEN+"Game Menu "+EnumChatFormatting.GRAY+"(Right Click)";
    private static String COMPASS_LORE = EnumChatFormatting.DARK_PURPLE.toString()+EnumChatFormatting.ITALIC.toString()+EnumChatFormatting.GRAY+"Right Click to bring up the Game Menu!";
    private boolean inHypixelLobby = false;
    private boolean onHypixel = false;

    public GameTypes currentGame;
    public GameTypes previousGame;
    public GameTypes prevPreviousGame;

    public List<String> teammates = new ArrayList<String>();
    public List<String> displayTeammates = new ArrayList<String>();

    public Map<String, Float> prevInitialAbsorption = new HashMap<String, Float>();

    public List<EntityTNTPrimed> tntList = new ArrayList<EntityTNTPrimed>();
    public List<Map<Integer, BlockPos>> highlightList = new ArrayList<Map<Integer, BlockPos>>();

    public int displayTimes = 0;

    private Random rnd = new Random();

    public String lastPlayerNameAttacker = null;
    public String lastPlayerNameAttacked = null;
    public int lastPlayerInteractedTimer = 0;

    public void runLobbyCheckerTimer() {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                checkIP(Hytils.instance.mc);
                checkForItem(Hytils.instance.mc);
                detectTeammatesAuto();
                randomizeDisplayTeammates();
            }
        }, 1000, 1000);
    }

    private void checkForItem(Minecraft mc) {
        if (mc != null && mc.thePlayer != null && mc.thePlayer.inventory != null) {
            ItemStack item = mc.thePlayer.inventory.getStackInSlot(0);
            if (item != null && item.hasDisplayName() && item.getDisplayName().equals(COMPASS_NAME)) {
                List<String> toolip = item.getTooltip(mc.thePlayer, false);
                if (toolip.get(1).equals(COMPASS_LORE)) {
                    inHypixelLobby = true;
                    currentGame = GameTypes.NOT_IN_GAME;
                    teammates.clear();
                    displayTeammates.clear();
                    prevInitialAbsorption.clear();
                    return;
                }
            }
        }
        inHypixelLobby = false;
    }

    private void checkIP(Minecraft mc) {
        if (mc.getCurrentServerData() != null) {
            String ip = mc.getCurrentServerData().serverIP.toLowerCase();
            onHypixel = ip.equals("hypixel.net") || ip.endsWith(".hypixel.net") || ip.contains(".hypixel.net:") || ip.startsWith("hypixel.net:");
            return;
        }
        onHypixel = false;
    }

    private void detectTeammatesAuto(){
        if (isOnHypixel() && !isInHypixelLobby()) {
            if (teammates.isEmpty()) {
                detectTeammates();
            }
        }
    }

    public void detectTeammates(){
        Minecraft mc = Hytils.instance.mc;
        if (mc.theWorld != null && mc.thePlayer != null){
            List<EntityPlayer> players = mc.theWorld.playerEntities;
            if (currentGame.gameGroup.equals("bedwars")){
                teammates.clear();
                displayTeammates.clear();
                prevInitialAbsorption.clear();
                for (EntityPlayer player: players) {
                    String filteredPlayerName = player.getDisplayNameString();
                    if (filteredPlayerName.startsWith("\u00a7")){
                        filteredPlayerName = filteredPlayerName.replace("\u00a7", "");
                        filteredPlayerName = filteredPlayerName.substring(1);
                    }
                    ItemStack chestplate = player.getCurrentArmor(2);
                    int chestplateColor = 0;
                    boolean shouldBeTeammate = false;
                    if (chestplate != null && chestplate.getItem() != null){
                        if (chestplate.getItem() instanceof ItemArmor){
                            chestplateColor = ((ItemArmor) chestplate.getItem()).getColor(chestplate);
                        }
                    }
                    if (mc.thePlayer.getCurrentArmor(2) != null && mc.thePlayer.getCurrentArmor(2).getItem() != null) {
                        if (mc.thePlayer.getCurrentArmor(2).getItem() instanceof ItemArmor){
                            int myColor = ((ItemArmor) mc.thePlayer.getCurrentArmor(2).getItem()).getColor(mc.thePlayer.getCurrentArmor(2));
                            shouldBeTeammate = chestplateColor == myColor;
                        }
                    }
                    if (shouldBeTeammate && !teammates.contains(filteredPlayerName)){
                        teammates.add(filteredPlayerName);
                    }else{
                        teammates.remove(filteredPlayerName);
                    }
                }
            }else if (Hytils.instance.getUtils().currentGame == GameTypes.ARCADE_BLOCKINGDEAD ||
                    Hytils.instance.getUtils().currentGame == GameTypes.ARCADE_ZOMBIES_DEADEND ||
                    Hytils.instance.getUtils().currentGame == GameTypes.ARCADE_ZOMBIES_BADBLOOD ||
                    Hytils.instance.getUtils().currentGame == GameTypes.ARCADE_ZOMBIES_ALIENARCADIUM){
                teammates.clear();
                List<EntityPlayer> players1 = mc.theWorld.playerEntities;
                List<String> players2 = new ArrayList<String>();
                for (EntityPlayer player: players1) {
                    if (!player.getDisplayNameString().startsWith("\u00a7c")){
                        String playerName = player.getDisplayNameString();
                        if (playerName.startsWith("\u00a7")){
                            playerName = playerName.replace("\u00a7", "");
                            playerName = playerName.substring(1);
                        }
                        players2.add(playerName);
                    }
                }
                teammates = players2;
            }
        }
    }

    public void detectTNT(){
        if (Hytils.instance.mc != null && Hytils.instance.mc.theWorld != null) {
            tntList.clear();
            World wrd = Hytils.instance.mc.theWorld;
            List<Entity> allEntities = wrd.loadedEntityList;
            List<EntityTNTPrimed> allTnt = new ArrayList<EntityTNTPrimed>();
            for (Entity e : allEntities) {
                if (e instanceof EntityTNTPrimed) {
                    allTnt.add((EntityTNTPrimed) e);
                }
            }
            tntList = allTnt;
        }
    }

    public void randomizeDisplayTeammates() {
        System.out.println("called");
        if (displayTimes > 9){
            if (teammates.size() > 0){
                if (teammates.size() <= Hytils.instance.getConfig().maxTeammateDisplay) { displayTeammates = teammates; }
                else {
                    displayTeammates.clear();
                    prevInitialAbsorption.clear();
                    int times = Hytils.instance.getConfig().maxTeammateDisplay;
                    while (times > 0){
                        int rand = MathHelper.getRandomIntegerInRange(rnd, 0, teammates.size() - 1);
                        if (!displayTeammates.contains(teammates.get(rand))){
                            displayTeammates.add(teammates.get(rand));
                            --times;
                        }
                    }
                    System.out.println("randomized display teammates list");
                }
            }
            displayTimes = 0;
        }else{
            ++displayTimes;
        }
    }

    public boolean isInHypixelLobby() {
        return inHypixelLobby;
    }

    public boolean isOnHypixel() {
        return onHypixel;
    }

    public static void sendMessage(IChatComponent sendMessage) {
        ClientChatReceivedEvent event = new ClientChatReceivedEvent((byte)1, sendMessage);
        MinecraftForge.EVENT_BUS.post(event);
        if (!event.isCanceled()) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(event.message);
        }
    }

    public static double distanceOf(Entity a, Entity b) {
        double x1 = a.posX;
        double y1 = a.posY;
        double z1 = a.posZ;
        double x2 = b.posX;
        double y2 = b.posY;
        double z2 = b.posZ;
        return distanceOf(x1, y1, z1, x2, y2, z2);
    }

    public static double distanceOf(Entity a, double x2, double y2, double z2) {
        double x1 = a.posX;
        double y1 = a.posY;
        double z1 = a.posZ;
        return distanceOf(x1, y1, z1, x2, y2, z2);
    }

    public static double distanceOf(double x1, double y1, double z1, double x2, double y2, double z2) {
        return Math.sqrt(Math.pow((x1 - x2), 2d) + Math.pow((y1 - y2), 2d) + Math.pow((z1 - z2), 2d));
    }

    public static BlockPos getNearestBlock(double x, double y, double z) {
        double nearestX = Math.round(x);
        double nearestY = Math.round(y);
        double nearestZ = Math.round(z);
        return new BlockPos(nearestX, nearestY, nearestZ);
    }

    private static double lengthSq(double x, double y, double z) {
        return (x * x) + (y * y) + (z * z);
    }

    private static double lengthSq(double x, double z) {
        return (x * x) + (z * z);
    }

    /**
     * Returns all blocks inside a specified sphere.
     *
     * @param center Center of the sphere or ellipsoid
     * @param diameterX The sphere/ellipsoid's largest north/south extent
     * @param diameterY The sphere/ellipsoid's largest up/down extent
     * @param diameterZ The sphere/ellipsoid's largest east/west extent
     * @param filled If false, only a shell will be returned.
     * @return List of blocks in the sphere or ellipsoid
     */
    public static List<BlockPos> getSphere(BlockPos center, double diameterX, double diameterY, double diameterZ, boolean filled) {
        List<BlockPos> posList = new ArrayList<BlockPos>();

        diameterX += 0.5;
        diameterY += 0.5;
        diameterZ += 0.5;

        final double invRadiusX = 1 / diameterX;
        final double invRadiusY = 1 / diameterY;
        final double invRadiusZ = 1 / diameterZ;

        final int ceilRadiusX = (int) Math.ceil(diameterX);
        final int ceilRadiusY = (int) Math.ceil(diameterY);
        final int ceilRadiusZ = (int) Math.ceil(diameterZ);

        double nextXn = 0;
        forX: for (int x = 0; x <= ceilRadiusX; ++x) {
            final double xn = nextXn;
            nextXn = (x + 1) * invRadiusX;
            double nextYn = 0;
            forY: for (int y = 0; y <= ceilRadiusY; ++y) {
                final double yn = nextYn;
                nextYn = (y + 1) * invRadiusY;
                double nextZn = 0;
                forZ: for (int z = 0; z <= ceilRadiusZ; ++z) {
                    final double zn = nextZn;
                    nextZn = (z + 1) * invRadiusZ;

                    double distanceSq = lengthSq(xn, yn, zn);
                    if (distanceSq > 1) {
                        if (z == 0) {
                            if (y == 0) {
                                break forX;
                            }
                            break forY;
                        }
                        break forZ;
                    }

                    if (!filled) {
                        if (lengthSq(nextXn, yn, zn) <= 1 && lengthSq(xn, nextYn, zn) <= 1 && lengthSq(xn, yn, nextZn) <= 1) {
                            continue;
                        }
                    }

                    posList.add(new BlockPos(x, y, z));
                    posList.add(new BlockPos(-x, y, z));
                    posList.add(new BlockPos(x, -y, z));
                    posList.add(new BlockPos(x, y, -z));
                    posList.add(new BlockPos(-x, -y, z));
                    posList.add(new BlockPos(-x, y, -z));
                    posList.add(new BlockPos(x, -y, -z));
                    posList.add(new BlockPos(-x, -y, -z));
                }
            }
        }

        return posList;
    }

    /**
     * Makes a sphere or ellipsoid.
     *
     * @param center Center of the sphere or ellipsoid
     * @param diameter The sphere's diameter
     * @param filled If false, only a shell will be generated.
     * @return List of blocks in the sphere or ellipsoid
     */
    public static List<BlockPos> getSphere(BlockPos center, double diameter, boolean filled) {
        return getSphere(center, diameter, diameter, diameter, filled);
    }
}
