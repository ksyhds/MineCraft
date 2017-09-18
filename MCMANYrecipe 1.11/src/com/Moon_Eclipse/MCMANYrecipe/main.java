package com.Moon_Eclipse.MCMANYrecipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener
{
	int debugint = 0;
	static boolean debug = false;
	
	
	Configuration c;
	static HashMap<ItemStack, ItemStack[]> ShapedRecipes = new HashMap<ItemStack, ItemStack[]>();
	static HashMap<ItemStack, ItemStack[]> ShapelessRecipes = new HashMap<ItemStack, ItemStack[]>();
	public void onEnable()
	{
		this.saveDefaultConfig();
		this.getServer().getPluginManager().registerEvents(new CraftManager(), this);
		this.reloaded();
	}
	public void onDisable(){}
	public boolean onCommand(CommandSender sender, Command command, String Label, String[] args)
	{
		if (sender.isOp())
		{
			if ((command.getName().equalsIgnoreCase("MCrecipe")) && (args[0].equals("리로드"))) 
			{
				Bukkit.resetRecipes();
				PloRecipeList.resetrecipes();
				reloadConfig();
				reloaded();
				sender.sendMessage("[mcrecipe] 문제없이 리로드 되었습니다. 버전: 1.4 [07.10]");
			}
			
			if ((command.getName().equalsIgnoreCase("MCrecipe")) && (args[0].equals("디버그")))
			{
				switch (this.debugint)
				{
					case 0:
						debug = true;
						sender.sendMessage("[MCrecipe] 디버그모드가 활성화 되었습니다.");
						this.debugint = 1;
					break;
					case 1:
						debug = false;
						sender.sendMessage("[MCrecipe] 디버그모드가 비활성화 되었습니다.");
						this.debugint = 0;
					break;
				}
				if (ShapedRecipes.isEmpty())
				{
					sender.sendMessage("등록된 정규식 레시피가 없습니다.");
				}
				else if( ShapelessRecipes.isEmpty())
				{
					sender.sendMessage("등록된 비정규식 레시피가 없습니다.");
				}
			}
			if ((command.getName().equalsIgnoreCase("MCrecipe")) && (args[0].equals("받기")))
			{
				//mcrecipe 받기 플레이어이름 아이템이름 갯수
				String itemname = args[2];
			    int getamount = Integer.parseInt(args[3]);
			    String playername = args[1];
			    Player targetPlayer = Bukkit.getPlayer(playername);
			    if (!itemname.equals(""))
				{
				    if (getamount != 0)
				    {
				    	if(targetPlayer.isOnline())
				    	{
				    		String key = "config." + itemname;
						    List<String> lore = c.getStringList(key + ".lore");
						    for (int i = 0; i < lore.size(); i++)
						    {
							    String s = lore.get(i).replace("&", "§");
							    String s2 = s.replaceAll("PLAYER", sender.getName());
							    lore.set(i, s2);
						    }
						    ItemStack is = createItem(c.getInt(key + ".id"), c.getInt(key + ".meta"), getamount, c.getString(key + ".name").replace("&", "§"), lore, c.getString(key + ".color"), c.getStringList(key + ".enchants"));
						    targetPlayer.getInventory().addItem(is);
				    	}
				    	else
				    	{
				    		sender.sendMessage("대상이 접속해 있지 않습니다.");
				    	}
				    }
				    else
				    {
				    	sender.sendMessage("갯수는 0개 이하일 수 없습니다.");
				    }
				}
			    else
			    {
			    sender.sendMessage("정확한 이름을 입력해 주세요.");
			    }
			}
	    }
	    else
	    {
	      sender.sendMessage("권한이 부족합니다.");
	    }
	    return true;
	}
	public ItemStack createItem(int typeId,int metadata, int amount, String name, List<String> lore, String color, List<String> enchants)
	{
		ItemStack i = new ItemStack(typeId, amount,(short) metadata);
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
		if(!(enchants.isEmpty()))
		{
			for(String enchant : enchants)
			{
				//'16: 1'
				int enchantname = Integer.parseInt(enchant.substring(0, enchant.length() - 3));
				int level = Integer.parseInt(enchant.substring(enchant.length() - 1));
				i.addUnsafeEnchantment(Enchantment.getById(enchantname), level);
			}
		}
		return i;
	}
	public ItemStack createItem2(int typeId, String name, int damage)
	{
		ItemStack i = new ItemStack(typeId);
		i.setDurability((short) damage);
		ItemMeta im = i.getItemMeta();
		if(!(name.equals("")))
		{
			im.setDisplayName(name);
		}
		i.setItemMeta(im);
		return i;			
	}
	public void reloaded()
	{
		ShapedRecipes.clear();
		ShapelessRecipes.clear();
		c = this.getConfig();
		Set<String> keys = c.getConfigurationSection("config").getKeys(false);
		if (keys == null || keys.isEmpty())
		{
			System.out.println("콘피그 파일에 이상이 있습니다.");
		}
		//System.out.println(keys.toString());
		for (String name : keys)
		{
			String key = "config." + name;
			List<String> lore = c.getStringList(key + ".lore");	
			for(int i = 0; i < lore.size() ; i++)
			{
				String s = lore.get(i).replace("&", "§");
				lore.set(i, s);
			}
			if(c.getString(key +".shape").equals("shape"))
			{			
				ItemStack item = this.createItem(c.getInt(key + ".id"), c.getInt(key + ".metadata"), c.getInt(key + ".amount"), c.getString(key + ".name").replace("&", "§"), lore, c.getString(key + ".color"), c.getStringList(key + ".enchants"));
				PloRecipe pl = new PloRecipe(item);

				
				ItemStack ing1 = new ItemStack(0);
				ItemStack ing2 = new ItemStack(0);
				ItemStack ing3 = new ItemStack(0);
				ItemStack ing4 = new ItemStack(0);
				ItemStack ing5 = new ItemStack(0);
				ItemStack ing6 = new ItemStack(0);
				ItemStack ing7 = new ItemStack(0);
				ItemStack ing8 = new ItemStack(0);
				ItemStack ing9 = new ItemStack(0);
				if(c.getInt(key + ".ing1.id") != 0)
				{
					ing1 = this.createItem2(c.getInt(key + ".ing1.id"), c.getString(key + ".ing1.name").replace("&", "§"), c.getInt(key + ".ing1.metadata"));
					pl.setSlot1(ing1);
				}
				if(c.getInt(key + ".ing2.id") != 0)
				{
					ing2 = this.createItem2(c.getInt(key + ".ing2.id"), c.getString(key + ".ing2.name").replace("&", "§"), c.getInt(key + ".ing2.metadata"));
					pl.setSlot2(ing2);
				}
				if(c.getInt(key + ".ing3.id") != 0)
				{
					ing3 = this.createItem2(c.getInt(key + ".ing3.id"), c.getString(key + ".ing3.name").replace("&", "§"), c.getInt(key + ".ing3.metadata"));
					pl.setSlot3(ing3);
				}
				if(c.getInt(key + ".ing4.id") != 0)
				{
					ing4 = this.createItem2(c.getInt(key + ".ing4.id"), c.getString(key + ".ing4.name").replace("&", "§"), c.getInt(key + ".ing4.metadata"));
					pl.setSlot4(ing4);
				}
				if(c.getInt(key + ".ing5.id") != 0)
				{
					ing5 = this.createItem2(c.getInt(key + ".ing5.id"), c.getString(key + ".ing5.name").replace("&", "§"), c.getInt(key + ".ing5.metadata"));
					pl.setSlot5(ing5);
				}
				if(c.getInt(key + ".ing6.id") != 0)
				{
					ing6 = this.createItem2(c.getInt(key + ".ing6.id"), c.getString(key + ".ing6.name").replace("&", "§"), c.getInt(key + ".ing6.metadata"));
					pl.setSlot6(ing6);
				}
				if(c.getInt(key + ".ing7.id") != 0)
				{
					ing7 = this.createItem2(c.getInt(key + ".ing7.id"), c.getString(key + ".ing7.name").replace("&", "§"), c.getInt(key + ".ing7.metadata"));
					pl.setSlot7(ing7);
				}
				if(c.getInt(key + ".ing8.id") != 0)
				{
					ing8 = this.createItem2(c.getInt(key + ".ing8.id"), c.getString(key + ".ing8.name").replace("&", "§"), c.getInt(key + ".ing8.metadata"));
					pl.setSlot8(ing8);
				}
				if(c.getInt(key + ".ing9.id") != 0)
				{
					ing9 = this.createItem2(c.getInt(key + ".ing9.id"), c.getString(key + ".ing9.name").replace("&", "§"), c.getInt(key + ".ing9.metadata"));
					pl.setSlot9(ing9);
				}
				ItemStack[] items = {ing1, ing2, ing3, ing4, ing5, ing6, ing7, ing8, ing9};
				ShapedRecipes.put(item, items);
				pl.register2();	
			}
			if(c.getString(key +".shape").equals("shapeless"))
			{
				
				ItemStack item = this.createItem(c.getInt(key + ".id"), c.getInt(key + ".metadata"), c.getInt(key + ".amount"), c.getString(key + ".name").replace("&", "§"), lore, c.getString(key + ".color"), c.getStringList(key + ".enchants"));
				MoonShapeLessRecipe pl = new MoonShapeLessRecipe(item);
				
				ItemStack ing1 = new ItemStack(0);
				ItemStack ing2 = new ItemStack(0);
				ItemStack ing3 = new ItemStack(0);
				ItemStack ing4 = new ItemStack(0);
				ItemStack ing5 = new ItemStack(0);
				ItemStack ing6 = new ItemStack(0);
				ItemStack ing7 = new ItemStack(0);
				ItemStack ing8 = new ItemStack(0);
				ItemStack ing9 = new ItemStack(0);
				if(c.getInt(key + ".ing1.id") != 0)
				{
					ing1 = this.createItem2(c.getInt(key + ".ing1.id"), c.getString(key + ".ing1.name").replace("&", "§"), c.getInt(key + ".ing1.metadata"));
					pl.addItem1(ing1);
				}
				if(c.getInt(key + ".ing2.id") != 0)
				{
					ing2 = this.createItem2(c.getInt(key + ".ing2.id"), c.getString(key + ".ing2.name").replace("&", "§"), c.getInt(key + ".ing2.metadata"));
					pl.addItem2(ing2);
				}
				if(c.getInt(key + ".ing3.id") != 0)
				{
					ing3 = this.createItem2(c.getInt(key + ".ing3.id"), c.getString(key + ".ing3.name").replace("&", "§"), c.getInt(key + ".ing3.metadata"));
					pl.addItem3(ing3);
				}
				if(c.getInt(key + ".ing4.id") != 0)
				{
					ing4 = this.createItem2(c.getInt(key + ".ing4.id"), c.getString(key + ".ing4.name").replace("&", "§"), c.getInt(key + ".ing4.metadata"));
					pl.addItem4(ing4);
				}
				if(c.getInt(key + ".ing5.id") != 0)
				{
					ing5 = this.createItem2(c.getInt(key + ".ing5.id"), c.getString(key + ".ing5.name").replace("&", "§"), c.getInt(key + ".ing5.metadata"));
					pl.addItem5(ing5);
				}
				if(c.getInt(key + ".ing6.id") != 0)
				{
					ing6 = this.createItem2(c.getInt(key + ".ing6.id"), c.getString(key + ".ing6.name").replace("&", "§"), c.getInt(key + ".ing6.metadata"));
					pl.addItem6(ing6);
				}
				if(c.getInt(key + ".ing7.id") != 0)
				{
					ing7 = this.createItem2(c.getInt(key + ".ing7.id"), c.getString(key + ".ing7.name").replace("&", "§"), c.getInt(key + ".ing7.metadata"));
					pl.addItem7(ing7);
				}
				if(c.getInt(key + ".ing8.id") != 0)
				{
					ing8 = this.createItem2(c.getInt(key + ".ing8.id"), c.getString(key + ".ing8.name").replace("&", "§"), c.getInt(key + ".ing8.metadata"));
					pl.addItem8(ing8);
				}
				if(c.getInt(key + ".ing9.id") != 0)
				{
					ing9 = this.createItem2(c.getInt(key + ".ing9.id"), c.getString(key + ".ing9.name").replace("&", "§"), c.getInt(key + ".ing9.metadata"));
					pl.addItem9(ing9);
				}
				ItemStack[] items = {ing1, ing2, ing3, ing4, ing5, ing6, ing7, ing8, ing9};
				ShapelessRecipes.put(item, items);
				
				pl.register();
					
			}
			if(c.getString(key +".shape").equals("furnace"))
			{
				if(c.getString(key +".override").equals("true"))
				{
					Moonfr pl = new Moonfr(this.createItem(c.getInt(key + ".id"), c.getInt(key + ".meta"), c.getInt(key + ".amount"), c.getString(key + ".name").replace("&", "§"), lore, c.getString(key + ".color"), c.getStringList(key + ".enchants")));
					if(c.getInt(key + ".ing1.id") != 0){pl.addsource(this.createItem2(c.getInt(key + ".ing1.id"), c.getString(key + ".ing1.name").replace("&", "§"), c.getInt(key + ".ing1.metadata")));}
					pl.register2();
				}
				else if(c.getString(key +".override").equals("false"))
				{
					if(c.getString(key +".override").equals("on")){};
					Moonfr pl = new Moonfr(this.createItem(c.getInt(key + ".id"), c.getInt(key + ".meta"), c.getInt(key + ".amount"), c.getString(key + ".name").replace("&", "§"), lore, c.getString(key + ".color"), c.getStringList(key + ".enchants")));
					if(c.getInt(key + ".ing1.id") != 0){pl.addsource(this.createItem2(c.getInt(key + ".ing1.id"), c.getString(key + ".ing1.name").replace("&", "§"), c.getInt(key + ".ing1.metadata")));}
					pl.register();
				}
			}
		}
	}
	public static boolean Debug()
	{
		return debug;
	}
}
