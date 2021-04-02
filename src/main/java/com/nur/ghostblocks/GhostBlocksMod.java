package com.nur.ghostblocks;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = GhostBlocksMod.MODID, version = GhostBlocksMod.VERSION, acceptedMinecraftVersions = "")
public class GhostBlocksMod {
    public static final String MODID = "ghostblocks";
    public static final String VERSION = "1.0";

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new RenderHandler());
        ClientCommandHandler.instance.registerCommand(new CommandToggleGhostBlocks());
    }
}
