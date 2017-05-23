package com.Moon_eclipse.EclipseLib;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;

import net.minecraft.server.v1_11_R1.NBTTagCompound;
import net.minecraft.server.v1_11_R1.NBTTagInt;
import net.minecraft.server.v1_11_R1.NBTTagList;

public class LibMain extends JavaPlugin{
	
	public void onEnable(){}
	public void onDisable(){}
	
	public static boolean hasString(List<String> lore, String name)
	{
		boolean b = false;
		for(String search : lore)
		{
			if(search.contains(name))
			{
				b = true;
			}
		}
		return b;
	}
	public static void takeitem( Player p, Inventory inv, int count, String name)
	{
		// ./팔기 이름 코드 메타 디스플레이이름 가격 수량
		// ./ -   0  1  2      3      4   5
		int before = 0;
		int tofor2 = count;
		for(int i = 0 ; i<54 ; i++)
		{
			boolean b = false;
			ItemStack is = inv.getItem(i);
			if(is != null && is.getAmount() > 0 && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().equals(name))
			{
				int amount = is.getAmount();
				for(int g = 0 + before ; g < tofor2; g++ )
				{
					if(amount > 0)
					{
						if(amount == 1)
						{
							inv.clear(i);
							before = g +1;
							b = true;
							break;
						}
					else
					{
						amount -= 1;
						is.setAmount(amount);
						b = false;
					}					
					}
				}
				if(b == false)
				{
					break;
				}
				if(b == true)
				{
					//Bukkit.broadcastMessage("dist < tofor");
				}
			}
		}
	}
	public static boolean hasitem( Player p, Inventory inv, int code, byte meta, int count, String name)
    {
    	boolean hasitem = false;
    	String Disname = name;
    	String Disname2 = Disname.replace("&", "§");
    	String Disname3 = Disname2.replace("_", " ");
    	int total = 0;
    	
    	for(int i = 0 ; i<54 ; i++)
    	{
    		try
    		{
    			if(
    					!(inv.getItem(i).equals(null))
    					&& inv.getItem(i).getTypeId() == code 
    					&& inv.getItem(i).getData().getData() == meta 
    					&& inv.getItem(i).getAmount() > 0
    					&& inv.getItem(i).getItemMeta().hasDisplayName()
    					&& inv.getItem(i).getItemMeta().getDisplayName().equals(Disname3)
    			  )
    				{
    					total += inv.getItem(i).getAmount();
    				}
    		}catch(Exception e){}
    		
    	}
    	if(total >= count)
    	{
    		hasitem = true;
    	}
    	else
    	{
    		p.sendMessage("§b[MCMANY]§r " + Disname3 + " §e이(가) 부족합니다.");
    	}
    	return hasitem;
    }
    public static boolean hasitem2( Player p, Inventory inv, int code, int count)
    {
    	boolean hasitem = false;
    	int total = 0;
    	
    	for(int i = 0 ; i<54 ; i++)
    	{
    		try
    		{
    			if(
    					!(inv.getItem(i).equals(null))
    					&& inv.getItem(i).getTypeId() == code 
    					&& inv.getItem(i).getAmount() > 0
    			  )
    				{
    					total += inv.getItem(i).getAmount();
    				}
    		}catch(Exception e){}
    		
    	}
    	if(total >= count)
    	{
    		hasitem = true;
    	}
    	else
    	{
    		p.sendMessage("§b[MCMANY]§4 " + code + "§4" + " §e이(가) 부족합니다.");
    	}
    	return hasitem;
    }public static boolean hasitem3( Player p, Inventory inv, int code, int count, String name)
    {
    	boolean hasitem = false;
    	String Disname = name;
    	String Disname2 = Disname.replace("&", "§");
    	String Disname3 = Disname2.replace("_", " ");
    	int total = 0;
    	
    	for(int i = 0 ; i<54 ; i++)
    	{
    		try
    		{
    			if(
    					!(inv.getItem(i).equals(null))
    					&& inv.getItem(i).getTypeId() == code 
    					&& inv.getItem(i).getAmount() > 0
    					&& inv.getItem(i).getItemMeta().hasDisplayName()
    					&& inv.getItem(i).getItemMeta().getDisplayName().equals(Disname3)
    			  )
    				{
    					total += inv.getItem(i).getAmount();
    				}
    		}catch(Exception e){}
    		
    	}
    	if(total >= count)
    	{
    		hasitem = true;
    	}
    	else
    	{
    		p.sendMessage("§b[MCMANY]§4 " + Disname3 + " §e이(가) 부족합니다.");
    	}
    	return hasitem;
    }
    public static ItemStack createItem(int typeId, int damage, int amount, String name, List<String> lore, String color, List<String> enchants)
	{
    	ItemStack i = new ItemStack(typeId);
		i.setDurability((short) damage);
		i.setAmount(amount);
		ItemMeta im = i.getItemMeta();
		String ColorHex = color;
		try
		{
			if(typeId == 298 || typeId == 299 || typeId == 300 || typeId == 301)
			{
				LeatherArmorMeta im2 = (LeatherArmorMeta) im;
				im2.setColor(Color.fromRGB(Integer.parseInt(ColorHex, 16)));
			}
		}catch(Exception e){}
		im.setDisplayName(name);
		im.setLore(lore);
		i.setItemMeta(im);
		Random rnd = new Random();
		if(enchants != null && !(enchants.isEmpty()) && !(enchants.toString().equals("")))
		{
			for(String enchant : enchants)
			{
				//'16: 1'
				int enchantname = Integer.parseInt(enchant.substring(0, enchant.length() - 3));
				int level = Integer.parseInt(enchant.substring(enchant.length() - 1));
				i.addUnsafeEnchantment(Enchantment.getById(enchantname), level);
			}
		}
		i = removeAttributes(i);
		return i;
	}
	public static void AddItem(Player p, Inventory inv, int code, int meta,int amount, String Disname, String lore)
	{
		ItemStack item = new ItemStack(code, amount,(short) 0, (byte) meta);
		ItemMeta im = item.getItemMeta();
		List<String> loreSet = new ArrayList<String>();
		im.setDisplayName(Disname);
		loreSet.add(lore);
		im.setLore(loreSet);
		item.setItemMeta(im);
		inv.addItem(item);
	}
	public static ItemStack removeAttributes(ItemStack i)
	{
        if(i == null) {
            return i;
        }
        if(i.getType() == Material.BOOK_AND_QUILL) {
            return i;
        }
        ItemStack item = i.clone();
        net.minecraft.server.v1_11_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag;
        if (!nmsStack.hasTag()){
            tag = new NBTTagCompound();
            nmsStack.setTag(tag);
        }
        else {
            tag = nmsStack.getTag();
        }
        NBTTagList am = new NBTTagList();
        tag.set("AttributeModifiers", am);
        nmsStack.setTag(tag);
        return CraftItemStack.asCraftMirror(nmsStack);
    }
	public static ItemStack hideFlags(ItemStack item)
    {
        net.minecraft.server.v1_11_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag;
         if (!nmsStack.hasTag()) {
            tag = new NBTTagCompound();
            nmsStack.setTag(tag);
        } else {
            tag = nmsStack.getTag();
        }
        tag.set("HideFlags", new NBTTagInt(63));
        nmsStack.setTag(tag);
        return CraftItemStack.asCraftMirror(nmsStack);
    }
	public static ItemStack hideFlags_Unbreak(ItemStack item)
    {
		item = removeAttributes(item);
		ItemMeta im = item.getItemMeta();
		im.setUnbreakable(true);
		item.setItemMeta(im);
        net.minecraft.server.v1_11_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag;
         if (!nmsStack.hasTag()) {
            tag = new NBTTagCompound();
            nmsStack.setTag(tag);
        } else {
            tag = nmsStack.getTag();
        }
        tag.set("HideFlags", new NBTTagInt(63));
        nmsStack.setTag(tag);
        return CraftItemStack.asCraftMirror(nmsStack);
    }
	public static List<String> ChangeString(String PlaceHolder, String NextString, List<String> lore)
	{
		ArrayList<String> newlore = new ArrayList<String>();
		for(int i = 0; i < lore.size() ; i++)
		{
			if(lore.get(i).contains(PlaceHolder))
			{
				newlore.add(lore.get(i).replaceAll(PlaceHolder, NextString));
			}
			else
			{
				newlore.add(lore.get(i));
			}
		}
		return newlore;
	}

	
}
