package tterrag.supermassivetech.item.armor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3Pool;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import org.lwjgl.opengl.GL11;

import tterrag.supermassivetech.block.waypoint.Waypoint;
import tterrag.supermassivetech.lib.Reference;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class HelmetOverlayHandler
{
    private static final ResourceLocation compass = new ResourceLocation(Reference.MOD_TEXTUREPATH, "textures/gui/overlay/compass.png");
    private static final ResourceLocation test = new ResourceLocation(Reference.MOD_TEXTUREPATH, "textures/gui/overlay/test.png");

    @SubscribeEvent
    public void onClientOverlay(RenderGameOverlayEvent.Text event)
    {
        Minecraft mc = Minecraft.getMinecraft();
        EntityClientPlayerMP player = mc.thePlayer;

        ItemStack helm = player.inventory.armorInventory[3];
        if (helm != null && helm.getItem() instanceof ItemGravityArmor)
        {
            int width = event.resolution.getScaledWidth();

            GL11.glColor3f(1f, 1f, 1f);
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
            mc.getTextureManager().bindTexture(compass);
            int v = getCompassAngle(player);
            mc.ingameGUI.drawTexturedModalRect((width - 120) / 2 - 35 * (mc.gameSettings.showDebugProfilerChart ? 1 : 0), BossStatus.bossName != null && BossStatus.statusBarTime > 0 ? 20
                    : mc.gameSettings.showDebugInfo ? 25 : 2, v, 256, 120, 16); // TODO make helper method

            renderWaypoints(width, player, player.posX, player.posY, player.posZ);
        }
    }

    private void renderWaypoints(int width, EntityPlayer player, double x, double y, double z)
    {
        for (Waypoint wp : Waypoint.waypoints)
        {
            Vec3 vec1 = Vec3.createVectorHelper(x, y, z);
            Vec3 vec2 = Vec3.createVectorHelper(wp.x + 0.5, wp.y + 1, wp.z + 0.5);
            Vec3 vec3 = player.getLookVec();
            
            Minecraft.getMinecraft().getTextureManager().bindTexture(test);
            Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect((int) angleTo(player, wp.x, wp.y, wp.z), 50, 16, 16, 16, 16);
            System.out.println(angleTo(player, wp.x, wp.y, wp.z));
        }
    }

    public static double angleTo(EntityPlayer player, double x, double y, double z)
    {
        double dx = Math.abs(player.posX - x);
        double dz = Math.abs(player.posZ - z);
             
        return (player.rotationYaw + Math.toDegrees(Math.atan(dx / dz)) + 1) % 360; 
    }

    private int getCompassAngle(EntityClientPlayerMP player)
    {
        int yaw = (int) player.rotationYawHead;
        yaw = (yaw - 90) % 360;
        yaw *= (256d / 360d);
        return yaw + 3; // arbitrary number to get the texture to line up...can
                        // be changed if texture changes
    }
}
