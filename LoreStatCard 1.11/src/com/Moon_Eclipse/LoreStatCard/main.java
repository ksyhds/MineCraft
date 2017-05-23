package com.Moon_Eclipse.LoreStatCard;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener{
	Configuration c;
	String getprefix;
	String prefix;
	String name0;
	String name;
	String getdrawsearchword;
	String drawsearchword;
	String getSoketString;
	String SoketString;
	String getExSoketString;
	String ExSoketString;
	String getDrawOutName;
	String DrawOutName;
	int count;
	
	public void onEnable()
	{
		this.saveDefaultConfig(); 
		c = this.getConfig();
		Bukkit.getPluginManager().registerEvents(this, this);
		getprefix = c.getString("config.prefix");
		prefix = getprefix.replace("&", "§");
		getdrawsearchword = c.getString("config.drawsearchword");
		drawsearchword = getdrawsearchword.replace("&", "§");
		getSoketString = c.getString("config.SoketString");
		SoketString = getSoketString.replace("&", "§");
		getExSoketString = c.getString("config.ExSoketString");
		ExSoketString = getExSoketString.replace("&", "§");
		getDrawOutName = c.getString("config.DrawOutName");
		DrawOutName = getDrawOutName.replace("&", "§");
		count = c.getInt("config.nessAmount");
		
		
	}
	
	public void onDisable(){}
	public boolean onCommand(CommandSender sender, Command command, String Label, String[] args)
	{
		if(command.getName().equals("lsc"))
		{
			if(sender.isOp())
			{
				if(args[0].equalsIgnoreCase("reload"))
				{
					this.reload(sender);
				}
			}
			else
			{
				sender.sendMessage("§b[마인아레나]§e 당신은 권한이 부족합니다.");
			}
		}
		return true;
	}
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e)
	{
		Player p = (Player) e.getWhoClicked();
		//p.sendMessage("클릭 이벤트 발생.");
		ItemStack CursorItem = e.getCursor();
		ItemStack TargetItem = e.getCurrentItem();
		if(e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR)
		{
			if(e.getClick().equals(ClickType.RIGHT) && TargetItem.getTypeId() != 0 && CursorItem.getTypeId() != 0)
			{
				//p.sendMessage("우클릭 이벤트 발생.");
				if(TargetItem.getItemMeta().hasLore())
				{
					ItemMeta im = CursorItem.getItemMeta();
					ItemMeta Targetim = TargetItem.getItemMeta();
					List<String> CursorLore = im.getLore();
					List<String> TargetLore = Targetim.getLore();
					Inventory inv = p.getInventory();
					
					if(this.isLoreStatCard(im))
					{
						//p.sendMessage("아이템 로어 스탯 카드임을 확인함.");
						//p.sendMessage(e.getInventory().getName());
						if(e.getInventory().getName().equals("container.crafting"))
						{
							//p.sendMessage("사용중인 인벤토리가 어느곳인지 확인함.");
							if(this.TargetLoreInt(TargetLore) < TargetLore.size())
							{
								//p.sendMessage("로어에 교체할 문구가 있는지 체크함.");
								String Helmet = c.getString("config.Helmet");
								String chest = c.getString("config.chest");
								String leggings = c.getString("config.leggings");
								String boots = c.getString("config.boots");
								String weapons = c.getString("config.weapons");
								String shield = c.getString("config.shield");
								
								List<String> HelmetId = c.getStringList("config.HelmetId");
								List<String> ChestId = c.getStringList("config.ChestId");
								List<String> LeggingsId = c.getStringList("config.LeggingsId");
								List<String> BootsId = c.getStringList("config.BootsId");
								List<String> ShieldId = c.getStringList("config.ShieldId");
								List<String> WeaponsId = c.getStringList("config.WeaponsId");
								String Disname = im.getDisplayName();
								String Disname2 = Targetim.getDisplayName();
								
								if(HelmetId.contains(TargetItem.getTypeId() + ""))
								{
									if(hasString(CursorLore, Helmet))
									{
										TargetLore.set(this.TargetLoreInt(TargetLore), CursorLore.get(c.getInt("config.getlorenum") - 1));							
										Targetim.setLore(TargetLore);
										TargetItem.setItemMeta(Targetim);
										e.setCancelled(true);
										this.Itemremove(e);
										p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
										p.sendMessage(prefix + "\"" + Disname + "§e\"" + "가§f " + "§e\"§f" + Disname2 + "§e\"" + " 에 부여되었습니다.");
									}
									else
									{
										p.sendMessage(prefix + "올바른 장비가 아닙니다.");
									}
									
								}
								else if(ChestId.contains(TargetItem.getTypeId() + ""))
								{
									if(hasString(CursorLore, chest))
									{
										TargetLore.set(this.TargetLoreInt(TargetLore), CursorLore.get(c.getInt("config.getlorenum") - 1));							
										Targetim.setLore(TargetLore);
										TargetItem.setItemMeta(Targetim);
										e.setCancelled(true);
										this.Itemremove(e);
										p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
										p.sendMessage(prefix + "\"" + Disname + "§e\"" + "가§f " + "§e\"§f" + Disname2 + "§e\"" + " 에 부여되었습니다.");
									}
									else
									{
										p.sendMessage(prefix + "올바른 장비가 아닙니다.");
									}
								}
								else if(LeggingsId.contains(TargetItem.getTypeId() + ""))
								{
									if(hasString(CursorLore, leggings))
									{
										TargetLore.set(this.TargetLoreInt(TargetLore), CursorLore.get(c.getInt("config.getlorenum") - 1));							
										Targetim.setLore(TargetLore);
										TargetItem.setItemMeta(Targetim);
										e.setCancelled(true);
										this.Itemremove(e);
										p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
										p.sendMessage(prefix + "\"" + Disname + "§e\"" + "가§f " + "§e\"§f" + Disname2 + "§e\"" + " 에 부여되었습니다.");
									}
									else
									{
										p.sendMessage(prefix + "올바른 장비가 아닙니다.");
									}
								}
								else if(BootsId.contains(TargetItem.getTypeId() + ""))
								{
									if(hasString(CursorLore, boots))
									{
										TargetLore.set(this.TargetLoreInt(TargetLore), CursorLore.get(c.getInt("config.getlorenum") - 1));							
										Targetim.setLore(TargetLore);
										TargetItem.setItemMeta(Targetim);
										e.setCancelled(true);
										this.Itemremove(e);
										p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
										p.sendMessage(prefix + "\"" + Disname + "§e\"" + "가§f " + "§e\"§f" + Disname2 + "§e\"" + " 에 부여되었습니다.");
									}
									else
									{
										p.sendMessage(prefix + "올바른 장비가 아닙니다.");
									}
								}
								else if(ShieldId.contains(TargetItem.getTypeId() + ""))
								{
									if(hasString(CursorLore, shield))
									{
										TargetLore.set(this.TargetLoreInt(TargetLore), CursorLore.get(c.getInt("config.getlorenum") - 1));							
										Targetim.setLore(TargetLore);
										TargetItem.setItemMeta(Targetim);
										e.setCancelled(true);
										this.Itemremove(e);
										p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
										p.sendMessage(prefix + "\"" + Disname + "§e\"" + "가§f " + "§e\"§f" + Disname2 + "§e\"" + " 에 부여되었습니다.");
									}
									else
									{
										p.sendMessage(prefix + "올바른 장비가 아닙니다.");
									}
								}
								else if(WeaponsId.contains(TargetItem.getTypeId() + ""))
								{
									if(hasString(CursorLore, weapons))
									{
										TargetLore.set(this.TargetLoreInt(TargetLore), CursorLore.get(c.getInt("config.getlorenum") - 1));							
										Targetim.setLore(TargetLore);
										TargetItem.setItemMeta(Targetim);
										e.setCancelled(true);
										this.Itemremove(e);
										p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
										p.sendMessage(prefix + "\"" + Disname + "§e\"" + "가§f " + "§e\"§f" + Disname2 + "§e\"" + " 에 부여되었습니다.");
									}
									else
									{
										p.sendMessage(prefix + "올바른 장비가 아닙니다.");
									}
								}
								/*
								switch(TargetItem.getTypeId())
								{
								case 298: case 302: case 306: case 310: case 314:
									if(this.hasString(CursorLore, c.getString("config.Helmet")))
									{
										if(im.getDisplayName().equals(DrawOutName))
										{
											TargetLore.set(this.TargetLoreInt(TargetLore), CursorLore.get(0));
										}
										else
										{
											TargetLore.set(this.TargetLoreInt(TargetLore), CursorLore.get(c.getInt("config.getlorenum") - 1));
										}									
										Targetim.setLore(TargetLore);
										TargetItem.setItemMeta(Targetim);
										e.setCancelled(true);
										this.Itemremove(e);
										this.takeitem(p, inv, count, name);
										p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_FALL, 1, 1);
										p.sendMessage(prefix + "헬멧에 적용 완료.");
									}
									else
									{
										p.sendMessage(prefix + "사용 가능한 부위에 적용해 주세요.");
									}
									break;
								case 299: case 303: case 307: case 311: case 315: case 443:
									if(this.hasString(CursorLore, c.getString("config.chest")))
									{
										if(im.getDisplayName().equals(DrawOutName))
										{
											TargetLore.set(this.TargetLoreInt(TargetLore), CursorLore.get(0));
										}
										else
										{
											TargetLore.set(this.TargetLoreInt(TargetLore), CursorLore.get(c.getInt("config.getlorenum") - 1));
										}									
										Targetim.setLore(TargetLore);
										TargetItem.setItemMeta(Targetim);
										e.setCancelled(true);
										this.Itemremove(e);
										this.takeitem(p, inv, count, name);
										p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_FALL, 1, 1);
										p.sendMessage(prefix + "상의에 적용완료");
									}
									else
									{
										p.sendMessage(prefix + "사용 가능한 부위에 적용해 주세요.");
									}
									break;
								case 300: case 304: case 308: case 312: case 316:
									if(this.hasString(CursorLore, c.getString("config.leggings")))
									{
										if(im.getDisplayName().equals(DrawOutName))
										{
											TargetLore.set(this.TargetLoreInt(TargetLore), CursorLore.get(0));
										}
										else
										{
											TargetLore.set(this.TargetLoreInt(TargetLore), CursorLore.get(c.getInt("config.getlorenum") - 1));
										}									
										Targetim.setLore(TargetLore);
										TargetItem.setItemMeta(Targetim);
										e.setCancelled(true);
										this.Itemremove(e);
										this.takeitem(p, inv, count, name);
										p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_FALL, 1, 1);
										p.sendMessage(prefix + "갑옷 하의에 적용 완료.");
									}
									else
									{
										p.sendMessage(prefix + "사용 가능한 부위에 적용해 주세요.");
									}
									break;
								case 442:
									p.sendMessage("방패 구문으로 진입");
									if(this.hasString(CursorLore, c.getString("config.shield")))
									{
										if(im.getDisplayName().equals(DrawOutName))
										{
											TargetLore.set(this.TargetLoreInt(TargetLore), CursorLore.get(0));
										}
										else
										{
											TargetLore.set(this.TargetLoreInt(TargetLore), CursorLore.get(c.getInt("config.getlorenum") - 1));
										}									
										Targetim.setLore(TargetLore);
										TargetItem.setItemMeta(Targetim);
										e.setCancelled(true);
										this.Itemremove(e);
										this.takeitem(p, inv, count, name);
										p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_FALL, 1, 1);
										p.sendMessage(prefix + "방패에 적용 완료.");
									}
									else
									{
										p.sendMessage(prefix + "사용 가능한 부위에 적용해 주세요.");
									}
									break;
								case 301: case 305: case 309: case 313: case 317:
									if(this.hasString(CursorLore, c.getString("config.boots")))
									{
										if(im.getDisplayName().equals(DrawOutName))
										{
											TargetLore.set(this.TargetLoreInt(TargetLore), CursorLore.get(0));
										}
										else
										{
											TargetLore.set(this.TargetLoreInt(TargetLore), CursorLore.get(c.getInt("config.getlorenum") - 1));
										}									
										Targetim.setLore(TargetLore);
										TargetItem.setItemMeta(Targetim);
										e.setCancelled(true);
										this.Itemremove(e);
										this.takeitem(p, inv, count, name);
										p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_FALL, 1, 1);
										p.sendMessage(prefix + "부츠에 적용 완료.");
									}
									else
									{
										p.sendMessage(prefix + "사용 가능한 부위에 적용해 주세요.");
									}
									break;
								case 267: case 268: case 272: case 276: case 283: case 258: case 271: case 275: case 279: case 286: case 261:
								case 256: case 290:  case 269: case 291: case 273: case 292: case 277: case 293: case 284: case 294:
								case 257: case 270: case 274: case 278: case 285: case 346:
									if(this.hasString(CursorLore, c.getString("config.weapons")))
									{
										if(im.getDisplayName().equals(DrawOutName))
										{
											TargetLore.set(this.TargetLoreInt(TargetLore), CursorLore.get(0));
										}
										else
										{
											TargetLore.set(this.TargetLoreInt(TargetLore), CursorLore.get(c.getInt("config.getlorenum") - 1));
										}									
										Targetim.setLore(TargetLore);
										TargetItem.setItemMeta(Targetim);
										e.setCancelled(true);
										this.Itemremove(e);
										this.takeitem(p, inv, count, name);
										p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_FALL, 1, 1);
										p.sendMessage(prefix + "무기에 적용 완료.");
									}
									else if(
											!(this.hasString(CursorLore, c.getString("config.boots")))
											&& !(this.hasString(CursorLore, c.getString("config.leggings")))
											&& !(this.hasString(CursorLore, c.getString("config.chest")))
											&& !(this.hasString(CursorLore, c.getString("config.Helmet")))
											&& !(this.hasString(CursorLore, c.getString("config.weapons")))
											&& !(this.hasString(CursorLore, c.getString("config.tools")))
											&& !(this.hasString(CursorLore, c.getString("config.pickaxe")))
											)
											
									{
										if(im.getDisplayName().equals(DrawOutName))
										{
											TargetLore.set(this.TargetLoreInt(TargetLore), CursorLore.get(0));
										}
										else
										{
											TargetLore.set(this.TargetLoreInt(TargetLore), CursorLore.get(c.getInt("config.getlorenum") - 1));
										}
										Targetim.setLore(TargetLore);
										TargetItem.setItemMeta(Targetim);
										e.setCancelled(true);
										this.Itemremove(e);
										this.takeitem(p, inv, count, name);
										p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_FALL, 1, 1);
										p.sendMessage(prefix + "무기에 적용 완료.");
									}
									else
									{
										p.sendMessage(prefix + "사용 가능한 부위에 적용해 주세요.");
									}
									break;
								}	
								*/
							}	
						}
						else
						{
							p.sendMessage(prefix + "해당 인벤토리에서는 사용할 수 없습니다.");
						}
					}	
					if(this.isRemoveItem(im))
					{
						if(e.getInventory().getName().equals("container.crafting"))
						{
							int i = 0;
							for(String lore : TargetLore)
							{
								if(lore.contains(drawsearchword))
								{
									e.setCancelled(true);
									TargetLore.set(i, SoketString);
									Targetim.setLore(TargetLore);
									TargetItem.setItemMeta(Targetim);
									p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_FALL, 1, 2);
									p.sendMessage(prefix + "소켓을 초기화 했습니다.");
									this.Itemremove(e);
									break;
								}
								i += 1;
							}
						}
						else
						{
							p.sendMessage(prefix + "해당 인벤토리에서는 사용할 수 없습니다.");
						}
					}
					if(this.isExRemoveItem(im))
					{
						if(e.getInventory().getName().equals("container.crafting"))
						{
							int i = 0;
							int s = 0;
							for(String lore : TargetLore)
							{
								if(lore.contains(drawsearchword) && s == 1)
								{
									e.setCancelled(true);
									TargetLore.set(i, ExSoketString);
									Targetim.setLore(TargetLore);
									TargetItem.setItemMeta(Targetim);
									p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_FALL, 1, 2);
									p.sendMessage(prefix + "소켓을 초기화 했습니다.");
									this.Itemremove(e);
									break;
								}
								else if (lore.contains(drawsearchword))
								{
									s += 1;
								}
								i += 1;
							}
						}
						else
						{
							p.sendMessage(prefix + "해당 인벤토리에서는 사용할 수 없습니다.");
						}
					}
					if(this.isDrawItem(im))
					{
						if(e.getInventory().getName().equals("container.crafting"))
						{
							int i = 0;
							for(String lore : TargetLore)
							{
								if(lore.contains(drawsearchword))
								{
									int code = CursorItem.getTypeId();
									int meta = CursorItem.getData().getData();
									String Disname3 = DrawOutName;
									e.setCancelled(true);
									TargetLore.set(i, SoketString);
									Targetim.setLore(TargetLore);
									TargetItem.setItemMeta(Targetim);
									p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_FALL, 1, 2);
									p.sendMessage(prefix + "소켓의 추출에 성공했습니다.");
									this.Itemremove(e);
									this.AddItem(p, inv, code, meta, 1, Disname3, lore);
									break;
								}
								i += 1;
							}
						}
						else
						{
							p.sendMessage(prefix + "해당 인벤토리에서는 사용할 수 없습니다.");
						}
					}
				}
			}
		}
	}
	public boolean isLoreStatCard(ItemMeta im)
	{
		boolean b = false;
		List<String> getname = c.getStringList("config.name");
		for(String name : getname)
		{
			String Disname  = name.replace("&", "§");
			if(im.hasDisplayName() && im.getDisplayName().equals(Disname))
			{
				b = true;
				break;
			}
		}
		return b;
	}
	public boolean isDrawItem(ItemMeta im)
	{
		boolean b = false;
		List<String> getname = c.getStringList("config.drawitemname");
		for(String name : getname)
		{
			String Disname  = name.replace("&", "§");
			if(im.hasDisplayName() && im.getDisplayName().equals(Disname))
			{
				b = true;
			}
		}
		return b;
	}
	public boolean isRemoveItem(ItemMeta im)
	{
		boolean b = false;
		List<String> getname = c.getStringList("config.RemoveItem");
		for(String name : getname)
		{
			String Disname  = name.replace("&", "§");
			if(im.hasDisplayName() && im.getDisplayName().equals(Disname))
			{
				b = true;
			}
		}
		return b;
	}
	public boolean isExRemoveItem(ItemMeta im)
	{
		boolean b = false;
		List<String> getname = c.getStringList("config.ExRemoveItem");
		for(String name : getname)
		{
			String Disname  = name.replace("&", "§");
			if(im.hasDisplayName() && im.getDisplayName().equals(Disname))
			{
				b = true;
			}
		}
		return b;
	}
	public boolean hasString(List<String> lore, String name)
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
	public int TargetLoreInt(List<String> lore)
	{
		int re = 0;
		for(String target : lore)
		{
			if(target.contains(c.getString("config.searchword")))
			{
				break;
			}
			re += 1;
		}
		return re;
		
	}
	public void Itemremove(InventoryClickEvent e)
	{
		int amount = e.getCursor().getAmount();
		if(amount == 1)
		{
			e.setCursor(new ItemStack(Material.AIR));
		}
		else
		{
			e.getCursor().setAmount(amount - 1);
		}
	}
	public void takeitem( Player p, Inventory inv, int count, String name)
	{
		// ./팔기 이름 코드 메타 디스플레이이름 가격 수량
		// ./ -   0  1  2      3      4   5
		int before = 0;
		int tofor2 = count;
		for(int i = 0 ; i<54 ; i++)
		{
			try
			{
				boolean b = false;
				if(!(inv.getItem(i).equals(null)) && inv.getItem(i).getAmount() > 0 && inv.getItem(i).getItemMeta().hasDisplayName() && inv.getItem(i).getItemMeta().getDisplayName().equals(name))
				{
					int amount = inv.getItem(i).getAmount();
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
							inv.getItem(i).setAmount(amount);
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
			}catch(Exception e){}	
		}
	}
	public void AddItem(Player p, Inventory inv, int code, int meta,int amount, String Disname, String lore)
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
	/*
	public boolean hasitem( Player p, Inventory inv, int count, String name)
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
			p.sendMessage("§b[MCMANY]§4 " + Disname3 + " §e가 부족합니다.");
		}
		return hasitem;
	}
	*/
	public void reload(CommandSender sender)
	{
		getprefix = c.getString("config.prefix");
		prefix = getprefix.replace("&", "§");
		getdrawsearchword = c.getString("config.drawsearchword");
		drawsearchword = getdrawsearchword.replace("&", "§");
		getSoketString = c.getString("config.SoketString");
		SoketString = getSoketString.replace("&", "§");
		getExSoketString = c.getString("config.ExSoketString");
		ExSoketString = getExSoketString.replace("&", "§");
		count = c.getInt("config.nessAmount");
		this.reloadConfig();
		c = this.getConfig();
		sender.sendMessage("LoreStatCard 문제없이 리로드 되었습니다.");
	}
}
