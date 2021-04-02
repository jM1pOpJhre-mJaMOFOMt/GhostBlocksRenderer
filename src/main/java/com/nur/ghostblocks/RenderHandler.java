package com.nur.ghostblocks;

import net.minecraft.block.BlockColored;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class RenderHandler {
    public static boolean enabled = false;
    public final int radius = 24;
    public List<BlockPos> ghostBlocks = new ArrayList<BlockPos>();
    @SubscribeEvent
    public void pre(RenderWorldLastEvent event) {
        if (!enabled) return;
        Minecraft minecraft = Minecraft.getMinecraft();
        final Entity renderViewEntity = minecraft.getRenderViewEntity();

        if (renderViewEntity == null) return;

        final double d0 = renderViewEntity.lastTickPosX + ((renderViewEntity.posX - renderViewEntity.lastTickPosX) * event.partialTicks);
        final double d1 = renderViewEntity.lastTickPosY + ((renderViewEntity.posY - renderViewEntity.lastTickPosY) * event.partialTicks);
        final double d2 = renderViewEntity.lastTickPosZ + ((renderViewEntity.posZ - renderViewEntity.lastTickPosZ) * event.partialTicks);

        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldRenderer = tessellator.getWorldRenderer();

        worldRenderer.setTranslation(-d0, -d1, -d2);

        GlStateManager.pushMatrix();
        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);





        World world = minecraft.theWorld;
        IBlockState redWool = Blocks.wool.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.RED);
        for(BlockPos pos : ghostBlocks) minecraft.getBlockRendererDispatcher().renderBlock(redWool, pos, world, worldRenderer);



        tessellator.draw();
        GlStateManager.popMatrix();
        worldRenderer.setTranslation(0, 0, 0);
    }



    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onPlayerTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        if (event.side == Side.SERVER) return;
        ghostBlocks.clear();
        Minecraft minecraft = Minecraft.getMinecraft();
        if (enabled && minecraft.thePlayer != null) {
            Minecraft.getMinecraft().ingameGUI.setRecordPlaying("" + EnumChatFormatting.GOLD + EnumChatFormatting.BOLD + "Ghost blocks are being rendered.", true);
            World world = minecraft.theWorld;
            BlockPos playerPos = new BlockPos(minecraft.thePlayer.posX, minecraft.thePlayer.posY, minecraft.thePlayer.posZ);
            for (int y = Math.max(-radius, -playerPos.getY()); y < Math.min(radius + 1, 256); y++) {
                for (int x = -radius; x < radius + 1; x++) {
                    for (int z = -radius; z < radius + 1; z++) {
                        BlockPos pos = playerPos.add(x, y, z);
                        IBlockState blockState = world.getBlockState(pos);
                        if (Blocks.piston_extension.equals(blockState.getBlock())) ghostBlocks.add(pos);
                    }
                }
            }
        }
    }

}
