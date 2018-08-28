package com.Moon_Eclipse.NameItemDrop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;

import com.Moon_Eclipse.MCgive.main;
import com.Moon_eclipse.EclipseLib.LibMain;

public class NameItemDrop extends JavaPlugin implements Listener
{
	
	private File DropItem;
	private FileConfiguration items;
	private File MobList;
	private FileConfiguration mobs;
	private Configuration c;
	String prefix;
	Random rn = new Random();

	public void onEnable()
	{
		Bukkit.getPluginManager().registerEvents(this, this);
		this.saveDefaultmobs();
		this.saveDefaultItems();
		this.saveDefaultConfig();
		c = this.getConfig();
		DropItem = new File(getDataFolder(), "items.yml");
		items = YamlConfiguration.loadConfiguration(DropItem);
		MobList = new File(getDataFolder(), "mobs.yml");
		mobs = YamlConfiguration.loadConfiguration(MobList);
		prefix = c.getString("config.prefix");
		
	}
	public void onDisable(){}
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if(sender.isOp())
		{
			if(command.getName().equalsIgnoreCase("nid"))
			{
				// nid reload/give nickname itemname
				if(args[0].equalsIgnoreCase("reload"))
				{
					prefix = c.getString("config.prefix");
					this.reloadConfig();
					c = this.getConfig();
					DropItem = new File(getDataFolder(), "items.yml");
					items = YamlConfiguration.loadConfiguration(DropItem);
					MobList = new File(getDataFolder(), "mobs.yml");
					mobs = YamlConfiguration.loadConfiguration(MobList);
					sender.sendMessage("[NameItemDrop] 문제없이 리로드.");	
				}
				if(args[0].equalsIgnoreCase("give"))
				{
					if(args.length >= 2)
					{
						Player TargetPlayer = Bukkit.getPlayer(args[1]);
						if(args.length >= 3)
						{
							String target = "items." + args[2];
							if(items.contains(target))
							{
								
								int itemid = items.getInt(target + ".id");
				   				int amount = items.getInt(target + ".amount");
				   				int metadata = items.getInt(target + ".metadata");
				   				String color = items.getString(target + ".color");
				   				String Disname = items.getString(target + ".name");
				   				List<String> lore = items.getStringList(target + ".lore");
				   				List<String> enchant = items.getStringList(target + ".enchants");
				   				ItemStack additem = this.createItem(itemid, amount, Disname, lore, color, enchant, metadata);
				   				TargetPlayer.getInventory().addItem(additem);
							}
							else
							{
								sender.sendMessage(prefix + "아이템이 없거나 잘못된 아이템 이릅입니다.");
							}
						}
						else
						{
							sender.sendMessage(prefix + "아이템의 이름을 입력해 주세요.");
						}
					}
					else
					{
						sender.sendMessage(prefix + "플레이어의 이름을 입력해 주세요.");
					}
				}
				if(args[0].equalsIgnoreCase("list"))
				{
					Set<String> list = items.getConfigurationSection("items").getKeys(false);
					for(String itemname : list)
					{
						sender.sendMessage(itemname);
					}
				}
			}
		}
		return true;
	}
	@EventHandler
	public void onEntityDeath(EntityDeathEvent e)
	{
		//Bukkit.broadcastMessage("이벤트 FIRE - NID");
		LivingEntity entity = e.getEntity();
		
		String PlayerName = "";
		if(entity.getKiller() instanceof Player)
		{
			Player p = (Player) entity.getKiller(); 
			//Bukkit.broadcastMessage("p.getname" + p.getName());
			PlayerName = p.getName();
		}
		
		if(entity instanceof Creature || entity instanceof Slime)
		{
			//Bukkit.broadcastMessage("엔티티는 크리쳐에 속함.");
		    LivingEntity creature = (LivingEntity) entity;
		    String getname = creature.getCustomName();
		    if(!((getname + "").equals("null")))
		    {
		    	//Bukkit.broadcastMessage("엔티티가 이름을 갖음. 이름: " + getname);
		    	String name = getname.replace(" ", "_");
			    String pluginname = name.replace("§", "&");
			    
			    List<ItemStack> newdrops = e.getDrops();
			    //Bukkit.broadcastMessage(pluginname);
			    Set<String> keys = mobs.getConfigurationSection("mobs").getKeys(false);
			    for(String key : keys)
			    {
			    	if(pluginname.contains(key))
			   		{
			    		//Bukkit.broadcastMessage("이름을 콘픽에서 찾음. 콘픽 이름: " + key);
			   			String path = "mobs." + key;
			   			int exp = mobs.getInt(path + ".exp");
					    e.setDroppedExp(exp);
		   				int	chanceint = c.getInt("config.ChanceInt");
			   			List<String> itemlist = mobs.getStringList(path + ".items");
			   			for(String item : itemlist)
			   			{
			   				// ("1, 10")
			   				String perstr = item.substring(item.indexOf(",") + 1, item.length());
			   				double perint = Double.parseDouble(perstr);
			   				//Bukkit.broadcastMessage(perstr + " + perstr");
			   				int random = rn.nextInt(chanceint -1) + 1;
			   				double percent = ((double)random / (double)chanceint) * 100;
			   				//Bukkit.broadcastMessage(perint + " + perint");
			   				//Bukkit.broadcastMessage(percent + " + percent");
			   				if(percent <= perint)
			   				{
			   					String itemname = item.substring(0 , item.indexOf(","));
			   					ItemStack dropitem = main.get_Mcgive_Item(PlayerName, itemname, 0);
				   				dropitem = LibMain.hideFlags_Unbreak(dropitem);
				   				newdrops.add(dropitem);
			   				}
			   			}
			   			break;
			   		}
			    }
		    }
		}
	}
	public void saveDefaultItems()
	{
		   if (DropItem == null)
		   {
			   DropItem = new File(getDataFolder(), "items.yml");
		   }
		   if (!DropItem.exists())
		   {            
			   this.saveResource("items.yml", true);
		   }
	}
	public void saveDefaultmobs()
	{
		   if (MobList == null)
		   {
			   MobList = new File(getDataFolder(), "mobs.yml");
		   }
		   if (!MobList.exists())
		   {            
			   this.saveResource("mobs.yml", true);
		   }
	}
	public ItemStack createItem(int typeId, int amount, String name, List<String> lore, String color, List<String> enchants, int damage)
	{
		ItemStack i = new ItemStack(typeId, amount, (short)damage);
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
		String Disname = name.replace("&", "§");
		im.setDisplayName(Disname);
		List<String> newlore = new ArrayList<String>();
		for(String oldlore : lore)
		{
			newlore.add(oldlore.replace("&", "§"));
		}
 		im.setLore(newlore);
		i.setItemMeta(im);
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
}
