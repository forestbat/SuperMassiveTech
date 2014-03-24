package tterrag.supermassivetech.network.packet;

import tterrag.supermassivetech.client.fx.EntityCustomSmokeFX;
import tterrag.supermassivetech.network.ISMTPacket;
import net.minecraft.client.Minecraft;
import io.netty.buffer.ByteBuf;

public class PacketHopperParticle implements ISMTPacket
{

	private int[] info = new int[6];

	public PacketHopperParticle()
	{
	}

	public PacketHopperParticle(int... information)
	{
		for (int i = 0; i < this.info.length; i++)
		{
			this.info[i] = information[i];
		}
	}

	@Override
	public void encodeInto(ByteBuf buffer)
	{
		for (int i = 0; i < info.length; i++)
		{
			buffer.writeInt(info[i]);
		}
	}

	@Override
	public void decodeInto(ByteBuf buffer)
	{
		int[] data = new int[6];
		for (int i = 0; i < data.length; i++)
		{
			data[i] = buffer.readInt();
		}

		Minecraft.getMinecraft().effectRenderer.addEffect(new EntityCustomSmokeFX(Minecraft.getMinecraft().thePlayer.worldObj, data[3] + 0.5, data[4] + 0.5, data[5] + 0.5,
				((double) (data[0] - data[3])) / 10, ((data[1] + 0.5) - data[4]) / 10, ((double) (data[2] - data[5])) / 10));
	}
}
