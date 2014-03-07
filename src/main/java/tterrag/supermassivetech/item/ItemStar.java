package tterrag.supermassivetech.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tterrag.supermassivetech.util.Utils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemStar extends ItemSMT{

	public static enum StarType
	{
		/* normal stars */
		YELLOW_DWARF("Yellow Dwarf"),
		RED_DWARF("Red Dwarf"),
		
		/* giant stars */
		RED_GIANT("Red Giant"),
		BLUE_GIANT("Blue Giant"),
		SUPERGIANT("Supergiant"),
		
		/* weak, strange stars */
		BROWN_DWARF("Brown Dwarf"),
		WHITE_DWARF("White Dwarf"),
		NEUTRON("Neutron"),
		PULSAR("Pulsar");
		
		private String name;
		
		StarType(String name) {this.name = name; }
		
		public String toString()
		{
			return name;
		}
	}
	
	public ItemStar(String unlocName) {
		super(unlocName, unlocName);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
		StarType type = Utils.getType(par1ItemStack);
		if (type == null) return 0;
		
		switch(type.ordinal())
		{
		case 0: return 0xFFFF00;
		case 1: return 0xFF0000;
		case 2: return 0xFF0000;
		case 3: return 0x0000FF;
		case 4: return 0xFFFFFF;
		case 5: return 0x993238;
		case 6: return 0x999999;
		case 7: return 0x000077;
		case 8: return 0xFF00FF;
		default: return 0;
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		NBTTagCompound tag = new NBTTagCompound();
		for (StarType t : StarType.values())
		{
			tag = new NBTTagCompound();
			tag.setString("type", t.name());
			ItemStack i = new ItemStack(this);
			i.setTagCompound(tag);
			list.add(i);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		par3List.add(Utils.getType(par1ItemStack).toString());
	}
}