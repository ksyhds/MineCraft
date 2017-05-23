package com.Moon_Eclipse.lorecommand;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.sun.media.sound.AiffFileReader;

import net.minecraft.server.v1_11_R1.ItemAir;
import net.minecraft.server.v1_11_R1.Material;

public class main extends JavaPlugin implements Listener{
	Configuration c;
	HashMap<String,Long> cooldowns = new HashMap<String,Long>();
	HashMap<String,Long> timer = new HashMap<String,Long>();
	HashMap<String,Integer> leftsecond = new HashMap<String,Integer>();
	HashMap<String,Integer> leftsecondShare = new HashMap<String,Integer>();
	HashMap<String,Boolean> iscooled = new HashMap<String,Boolean>();
	HashMap<String,Boolean> iscooledShare = new HashMap<String,Boolean>();
	int seconds = 0;
	int Cosec = 0;
	List<String> RejectItemCode;
	public void onEnable()
	{
		Bukkit.getPluginManager().registerEvents(this, this);
		this.saveDefaultConfig();
		c = this.getConfig();	
		RejectItemCode = c.getStringList("config.RejectItemCode");
	}
	public void onDisable(){}
	
	
	public boolean onCommand(CommandSender sender, Command command, String Label, String[] args)
	{
		if(command.getName().equals("lorecommand") && args[0].equals("reload"))
		{
			this.reloadConfig();
			c = this.getConfig();
			sender.sendMessage("lorecommand 리로드 되었습니다. 버전: 1.3v[06.12]");
			RejectItemCode = c.getStringList("config.RejectItemCode");
		}
		return true;
	}
	@EventHandler
	public void onRightclick(PlayerInteractEvent event)
	{
		Player p = event.getPlayer();
		
		if(!(event.getPlayer().getItemInHand().getType().toString().equals("AIR"))  && (p.isSneaking() && event.getAction().equals(event.getAction().LEFT_CLICK_AIR) || p.isSneaking() && event.getAction().equals(event.getAction().LEFT_CLICK_BLOCK) || p.isSneaking() && event.getAction().equals(event.getAction().PHYSICAL)))
		{
			this.Run(p);
		}
		if(!(event.getPlayer().getItemInHand().getType().toString().equals("AIR")) && !p.isSneaking() && (event.getAction().equals(event.getAction().RIGHT_CLICK_AIR) || event.getAction().equals(event.getAction().RIGHT_CLICK_BLOCK) || event.getAction().equals(event.getAction().PHYSICAL)))
		{
			this.Run2(p);
		}
	}
	@EventHandler
	public void ondamageevent(EntityDamageByEntityEvent event)
	{
		if(event.getDamager() instanceof Player)
		{
			Player p = (Player) event.getDamager();
			
			if(!(p.getItemInHand().getType().toString().equals("AIR"))  && p.isSneaking())
			{
				this.Run(p);
			}
		}
	}
	public void Run(Player p)
	{
		String playerworld = p.getWorld().getName();
		//Bukkit.broadcastMessage("이벤트 발생");
		ItemStack item = p.getItemInHand();
		if(!(RejectItemCode.contains(String.valueOf(item.getTypeId()))))
		{
			if(item.getItemMeta().hasLore())
			{
				//Bukkit.broadcastMessage("로어 조건문 통과");
				Set<String> keys = c.getConfigurationSection("config").getKeys(false);
				for(String search : keys)
				{
					String key = "config." + search;  //아이템이름
					if(search.equalsIgnoreCase("RejectItemCode"))
					{
						continue;
					}
					List<String> commands = c.getStringList(key + ".commands");
					String consume = c.getString(key + ".consume");
					

					//Bukkit.broadcastMessage(command);
					String lore = c.getString(key + ".lore").replace("&", "§");
					if(item.getItemMeta().getLore().contains(lore))
					{
						List<String> worldname = c.getStringList(key + ".world");
						
						if(worldname.contains(playerworld))
						{
							seconds = c.getInt(key + ".cooldown");
							Cosec = c.getInt(key + ".sharecooldown");
							if(Cosec != 0)
							{
								if(iscooledShare.get(p.getName()))
								{
									if(p.getFoodLevel() >= c.getInt(key + ".hunger"))
									{
										//Bukkit.broadcastMessage(p.getFoodLevel()+ " < " + c.getInt(key + ".hunger") + "");
										p.setFoodLevel(p.getFoodLevel() - c.getInt(key + ".hunger"));
										//Bukkit.broadcastMessage(cooldowns.get(p.getName()) + " < " + (System.currentTimeMillis() - seconds*1000) + "" );
										//Bukkit.broadcastMessage("조건문 통과");
										
										this.SetTimer(p, seconds, search);
										if(Cosec != 0)
										{
											this.SetShareTimer(p, Cosec);
										}
										
										boolean removeOp = new Boolean(p.isOp());
										
										p.setOp(true);
										for(String command : commands)
										{
											String pcommand = command.replaceAll("PLAYER", p.getName());
											p.performCommand(pcommand);
										}
										p.setOp(removeOp);
										if(consume.equalsIgnoreCase("yes"))
										{
											this.takeitem(p, 1);
										}
									
									}
									else
									{
										p.sendMessage("§b[마인아레나]§e 허기가 부족합니다.");
									}
								}
								else
								{
									p.sendMessage("§b[마인아레나]§e 공용 재사용 대기시간이 " + leftsecondShare.get(p.getName()) + "s 남았습니다.");
								}
							}
							if(iscooled.get(p.getName() + search))
							{
								if(p.getFoodLevel() >= c.getInt(key + ".hunger"))
								{
									//Bukkit.broadcastMessage(p.getFoodLevel()+ " < " + c.getInt(key + ".hunger") + "");
									p.setFoodLevel(p.getFoodLevel() - c.getInt(key + ".hunger"));
									//Bukkit.broadcastMessage(cooldowns.get(p.getName()) + " < " + (System.currentTimeMillis() - seconds*1000) + "" );
									//Bukkit.broadcastMessage("조건문 통과");
									
									this.SetTimer(p, seconds, search);
									if(Cosec != 0)
									{
										this.SetShareTimer(p, Cosec);
									}
									
									boolean removeOp = new Boolean(p.isOp());
									
									p.setOp(true);
									for(String command : commands)
									{
										String pcommand = command.replaceAll("PLAYER", p.getName());
										p.performCommand(pcommand);
									}
									p.setOp(removeOp);
									if(consume.equalsIgnoreCase("yes"))
									{
										this.takeitem(p, 1);
									}
								
								}
								else
								{
									p.sendMessage("§b[마인아레나]§e 허기가 부족합니다.");
								}
							}
							else
							{
								p.sendMessage("§b[마인아레나]§e 재사용 대기시간이 " + leftsecond.get(p.getName() + search)+"s 남았습니다.");
							}
						}
					}
				}
			}
		}
	
	}
	public void Run2(Player p)
	{

		String playerworld = p.getWorld().getName();
		//Bukkit.broadcastMessage("이벤트 발생");
		ItemStack item = p.getItemInHand();
		if(!(RejectItemCode.contains(String.valueOf(item.getTypeId()))))
		{
			if(item.getItemMeta().hasLore())
			{
				//Bukkit.broadcastMessage("로어 조건문 통과");
				Set<String> keys = c.getConfigurationSection("config").getKeys(false);
				for(String search : keys)
				{
					String key = "config." + search;  //아이템이름
					if(search.equalsIgnoreCase("RejectItemCode"))
					{
						continue;
					}
					List<String> commands = c.getStringList(key + ".commands");
					String consume = c.getString(key + ".consume");
					
					boolean b = c.getBoolean(key + ".right");
					if(b)
					{
						//Bukkit.broadcastMessage(command);
						String lore = c.getString(key + ".lore").replace("&", "§");
						if(item.getItemMeta().getLore().contains(lore))
						{
							List<String> worldname = c.getStringList(key + ".world");
							
							if(worldname.contains(playerworld))
							{
								seconds = c.getInt(key + ".cooldown");
								Cosec = c.getInt(key + ".sharecooldown");
								if(Cosec != 0)
								{
									if(iscooledShare.get(p.getName()))
									{
										if(p.getFoodLevel() >= c.getInt(key + ".hunger"))
										{
											//Bukkit.broadcastMessage(p.getFoodLevel()+ " < " + c.getInt(key + ".hunger") + "");
											p.setFoodLevel(p.getFoodLevel() - c.getInt(key + ".hunger"));
											//Bukkit.broadcastMessage(cooldowns.get(p.getName()) + " < " + (System.currentTimeMillis() - seconds*1000) + "" );
											//Bukkit.broadcastMessage("조건문 통과");
											
											this.SetTimer(p, seconds, search);
											if(Cosec != 0)
											{
												this.SetShareTimer(p, Cosec);
											}
											
											boolean removeOp = new Boolean(p.isOp());
											
											p.setOp(true);
											for(String command : commands)
											{
												String pcommand = command.replaceAll("PLAYER", p.getName());
												p.performCommand(pcommand);
											}
											p.setOp(removeOp);
											if(consume.equalsIgnoreCase("yes"))
											{
												this.takeitem(p, 1);
											}
										
										}
										else
										{
											p.sendMessage("§b[마인아레나]§e 허기가 부족합니다.");
										}
									}
									else
									{
										p.sendMessage("§b[마인아레나]§e 공용 재사용 대기시간이 " + leftsecondShare.get(p.getName()) + "s 남았습니다.");
									}
								}
								if(iscooled.get(p.getName() + search))
								{
									if(p.getFoodLevel() >= c.getInt(key + ".hunger"))
									{
										//Bukkit.broadcastMessage(p.getFoodLevel()+ " < " + c.getInt(key + ".hunger") + "");
										p.setFoodLevel(p.getFoodLevel() - c.getInt(key + ".hunger"));
										//Bukkit.broadcastMessage(cooldowns.get(p.getName()) + " < " + (System.currentTimeMillis() - seconds*1000) + "" );
										//Bukkit.broadcastMessage("조건문 통과");
										
										this.SetTimer(p, seconds, search);
										if(Cosec != 0)
										{
											this.SetShareTimer(p, Cosec);
										}
										
										boolean removeOp = new Boolean(p.isOp());
										
										p.setOp(true);
										for(String command : commands)
										{
											String pcommand = command.replaceAll("PLAYER", p.getName());
											p.performCommand(pcommand);
										}
										p.setOp(removeOp);
										if(consume.equalsIgnoreCase("yes"))
										{
											this.takeitem(p, 1);
										}
									
									}
									else
									{
										p.sendMessage("§b[마인아레나]§e 허기가 부족합니다.");
									}
								}
								else
								{
									p.sendMessage("§b[마인아레나]§e 재사용 대기시간이 " + leftsecond.get(p.getName() + search)+"s 남았습니다.");
								}
							}
						}
					}
				}
			}
		}
	
	
	}
	@EventHandler
	public void onJoin(PlayerJoinEvent event)
	{
		Player p = event.getPlayer();
		leftsecondShare.put(p.getName(), 0);
		iscooledShare.put(p.getName(), true);
		Set<String> keys = c.getConfigurationSection("config").getKeys(false);
		for(String search : keys)
		{
			leftsecond.put(p.getName() + search, 0);
			iscooled.put(p.getName() + search, true);
			
		}
		
	}
	public void SetShareTimer(Player p, int second)
	{
		if(second != 0)
		{
			leftsecondShare.put(p.getName(), second);
			iscooledShare.put(p.getName(), false);

			BukkitTask task = new BukkitRunnable() 
			{
	            @Override
	            public void run() 
	            {
	            	leftsecondShare.put(p.getName(), (leftsecondShare.get(p.getName())-1));
	            	if(leftsecondShare.get(p.getName()) == 0)
	            	{
	            		this.cancel();
	            		iscooledShare.put(p.getName(), true);
	            		p.sendMessage("§b[마인아레나]§e 공용 재사용 대기시간이 끝났습니다.");
	            	}
	        	}
	        }.runTaskTimer(this, 0, 20);
		}
	}
	public void SetTimer(Player p, int second, String name)
	{
		if(second != 0)
		{
			leftsecond.put(p.getName() + name, second);
			iscooled.put(p.getName() + name, false);
			BukkitTask task = new BukkitRunnable() 
			{
	            @Override
	            public void run() 
	            {
	            	leftsecond.put(p.getName() + name, (leftsecond.get(p.getName() + name)-1));
	            	if(leftsecond.get(p.getName() + name) == 0)
	            	{
	            		this.cancel();
	            		iscooled.put(p.getName() + name, true);
	            		p.sendMessage("§b[마인아레나]§e 당신의 스킬 " + c.getString("config." + name + ".lore") + "이(가) 준비 되었습니다.");
	            	}
	        	}
	        }.runTaskTimer(this, 0, 20);
		}
	}
	public void takeitem( Player p, int count)
	{
		ItemStack i = p.getItemInHand();
		int amount = i.getAmount();
		if(amount == 1)
		{
			p.setItemInHand(new ItemStack(0));
		}
		else
		{
			i.setAmount(amount - count);
			p.setItemInHand(i);
		}
		
	}	
	public List<String> ChangeString(String PlaceHolder, String NextString, List<String> lore)
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

