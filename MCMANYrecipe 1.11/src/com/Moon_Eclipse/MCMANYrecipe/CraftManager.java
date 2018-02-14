package com.Moon_Eclipse.MCMANYrecipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.Moon_eclipse.EclipseLib.LibMain;

import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagList;

public class CraftManager implements Listener {
	HashMap<String, String> truthmap = new HashMap<String, String>();
	ItemStack interactis;
	/**
	 * @author Plo457
	 */
	@EventHandler
	public void preCraftEvent(PrepareItemCraftEvent event)
	{
		Player p = (Player) event.getView().getPlayer();
		ItemStack[] CraftItems = event.getInventory().getContents();
		ItemStack Output = CraftItems[0];
		String invType = event.getInventory().getType().toString();
		CraftItems = ReValueMatrix(CraftItems,invType);
		if(main.Debug())
		{
			for(ItemStack is : CraftItems)
			{
				Bukkit.getPlayer("Moon_Eclipse").sendMessage("조합 아이템: " + is.toString());
			}	
		}
		
		
		/*
		Bukkit.broadcastMessage("조합 이벤트 발생.");
		if(invType.equals("WORKBENCH"))
		{
			Bukkit.broadcastMessage("워크 벤치에서 조합 이벤트 발생.");
			if(this.ingHasDisname(CraftItems))
			{
				Bukkit.broadcastMessage("모든 조합재료에 디스플레이 이름이 있음");
				ItemStack output = event.getRecipe().getResult();
				for(ItemStack item : main.ShapedRecipes.keySet())
				{
					if(item.equals(output))
					{
						//Bukkit.broadcastMessage(item.toString());
						//Bukkit.broadcastMessage(output.toString());
						ItemStack[] RecipeItems = main.ShapedRecipes.get(item);
						if(ingShapedMatch(RecipeItems, CraftItems))
						{
							
						}
						else
						{
							event.getInventory().setResult(new ItemStack(Material.AIR));
						}
					}
				}
				for(ItemStack item : main.ShapelessRecipes.keySet())
				{

						//Bukkit.broadcastMessage("레시피 아이템:" + item.getItemMeta().getDisplayName());
						//Bukkit.broadcastMessage("아웃풋 아이템:" + output.getItemMeta().getDisplayName());
						ItemStack[] RecipeItems = main.ShapelessRecipes.get(item);
						if(ingShapelessMatch(RecipeItems, CraftItems))
						{
							event.getInventory().setResult(item);
							break;
						}
						else
						{
							event.getInventory().setResult(new ItemStack(Material.AIR));
						}
				}
			}
			else
			{
				for(ItemStack item : main.ShapedRecipes.keySet())
				{

					ItemStack[] RecipeItems = main.ShapedRecipes.get(item);
					if(isThisCustomRecipe_Shapeless(RecipeItems, CraftItems));
					{
						event.getInventory().setResult(new ItemStack(Material.AIR));
					}
				
				}
				for(ItemStack item : main.ShapelessRecipes.keySet())
				{
					ItemStack[] RecipeItems = main.ShapelessRecipes.get(item);
					if(isThisCustomRecipe_Shapeless(RecipeItems, CraftItems));
					{
						event.getInventory().setResult(new ItemStack(Material.AIR));
					}
				}
			}
		}
		else if(invType.equals("CRAFTING"))
		{
			Bukkit.broadcastMessage("조합창에서 조합 이벤트 발생.");
			if(this.ingHasDisname(CraftItems))
			{
				Bukkit.broadcastMessage("모든 조합재료에 디스플레이 이름이 있음");
				ItemStack output = event.getRecipe().getResult();
				for(ItemStack item : main.ShapedRecipes.keySet())
				{
					if(item.equals(output))
					{
						//Bukkit.broadcastMessage(item.toString());
						//Bukkit.broadcastMessage(output.toString());
						ItemStack[] RecipeItems = main.ShapedRecipes.get(item);
						if(!ingShapedMatch(RecipeItems, CraftItems))
						{
							event.getInventory().setResult(new ItemStack(Material.AIR));
						}
					}
				}
				for(ItemStack item : main.ShapelessRecipes.keySet())
				{

						//Bukkit.broadcastMessage("레시피 아이템:" + item.getItemMeta().getDisplayName());
						//Bukkit.broadcastMessage("아웃풋 아이템:" + output.getItemMeta().getDisplayName());
						ItemStack[] RecipeItems = main.ShapelessRecipes.get(item);
						if(ingShapelessMatch(RecipeItems, CraftItems))
						{
							event.getInventory().setResult(item);
							break;
						}
						else
						{
							event.getInventory().setResult(new ItemStack(Material.AIR));
						}
				}
			}
		}
		*/
		
		truthmap.put(p.getName(), "false");
		
		if(main.Debug())
		{
			for(int x = 1; x < 10 ; x++)
			{
				try{Bukkit.getPlayer("Moon_Eclipse").sendMessage(x + "번은 "+ event.getInventory().getItem(x).toString());}catch(Exception exe){}
			}
		}
		if(main.Debug()){Bukkit.getPlayer("Moon_Eclipse").sendMessage("이벤트 발생 precraft");}
		/*
		if(invType.equals("WORKBENCH"))
		{

			if(!(PloRecipeList.getShapedRecipes() == null))
			{
				//Bukkit.broadcastMessage("셰이브 레시피 리스트가 비어있지 않음");
				if(RecipeMatch.hasDisname(Arrays.asList(CraftItems)))
				{
					
					//Bukkit.broadcastMessage("조건문 내부 진입 has Dis name");
					event.getInventory().setResult(new ItemStack(Material.AIR));
					//Bukkit.broadcastMessage("삭제완료");
					for (PloRecipe recipe : PloRecipeList.getShapedRecipes())
					{
						//Bukkit.broadcastMessage("셰이프 구문 통과");
						if (RecipeMatch.matches(Arrays.asList(CraftItems), recipe))
						{
							//Bukkit.broadcastMessage("아이템 등재 성공");
							event.getInventory().setResult(recipe.getOutput());
							truthmap.put(p.getName(), "true");
							break;
							
						}
					}
				}
			}
			if(truthmap.get(p.getName()).equals("false"))
			{
				if(!(PloRecipeList.getShapelessRecipes() == null))
				{
					if(RecipeMatch.hasDisname(Arrays.asList(CraftItems)))
					{
						for(MoonShapeLessRecipe recipe : PloRecipeList.getShapelessRecipes())
						{
							event.getInventory().setResult(new ItemStack(Material.AIR));
							//Bukkit.broadcastMessage("삭제완료 less");
							if (RecipeMatch.matches2(Arrays.asList(CraftItems), recipe))
							{
								//if(main.Debug()){Bukkit.broadcastMessage("아이템 등재 성공less");}
								event.getInventory().setResult(recipe.getOutput());
								break;
							}
						}
					}
				}
			}
		
		}
		else if(invType.equals("CRAFTING"))
		{
			*/
			if(main.Debug()){Bukkit.getPlayer("Moon_Eclipse").sendMessage("워크 벤치에서 조합 이벤트 발생.");}
			
			if(this.ingHasDisname(CraftItems))
			{
				if(main.Debug()){Bukkit.getPlayer("Moon_Eclipse").sendMessage("모든 조합재료에 디스플레이 이름이 있음");}
				
				for(ItemStack item : main.ShapedRecipes.keySet())
				{
					if(item.equals(Output))
					{
						if(main.Debug()){Bukkit.getPlayer("Moon_Eclipse").sendMessage("레시피의 출력과 서버 출력이 일치함");}
						//Bukkit.broadcastMessage(item.toString());
						//Bukkit.broadcastMessage(output.toString());
						ItemStack[] RecipeItems = main.ShapedRecipes.get(item);
						if(ingShapedMatch(RecipeItems, CraftItems))
						{
							event.getInventory().setResult(LibMain.hideFlags_Unbreak(item));
							if(main.Debug()){Bukkit.getPlayer("Moon_Eclipse").sendMessage("셰입이 매치하므로 아이템 설정");}
							return;
							
						}
						else
						{
							if(main.Debug()){Bukkit.getPlayer("Moon_Eclipse").sendMessage("셰입이 매치하지 않으므로 아이템 지움");}
							event.getInventory().setResult(new ItemStack(Material.AIR));
						}
					}
				}
				for(ItemStack item : main.ShapelessRecipes.keySet())
				{

						//Bukkit.broadcastMessage("레시피 아이템:" + item.getItemMeta().getDisplayName());
						//Bukkit.broadcastMessage("아웃풋 아이템:" + output.getItemMeta().getDisplayName());
						ItemStack[] RecipeItems = main.ShapelessRecipes.get(item);
						if(ingShapelessMatch(RecipeItems, CraftItems))
						{
							event.getInventory().setResult(LibMain.hideFlags_Unbreak(item));
							if(main.Debug()){Bukkit.getPlayer("Moon_Eclipse").sendMessage("셰입이 매치하므로 아이템 설정");}
							return;
						}
						else
						{
							event.getInventory().setResult(new ItemStack(Material.AIR));
							//if(main.Debug()){Bukkit.getPlayer("Moon_Eclipse").sendMessage("셰입이 매치하지 않으므로 아이템 지움");}
						}
				}				
			}
			else
			{
				if(main.Debug()){Bukkit.getPlayer("Moon_Eclipse").sendMessage("조합하려는 아이템에 이름이 없음");}
				for(ItemStack item : main.ShapedRecipes.keySet())
				{
					ItemStack[] RecipeItems = main.ShapedRecipes.get(item);
					if(isThisCustomRecipe_Shapeless(RecipeItems, CraftItems));
					{
						event.getInventory().setResult(new ItemStack(Material.AIR));
					}
				}
				for(ItemStack item : main.ShapelessRecipes.keySet())
				{
					ItemStack[] RecipeItems = main.ShapelessRecipes.get(item);
					if(isThisCustomRecipe_Shapeless(RecipeItems, CraftItems));
					{
						event.getInventory().setResult(new ItemStack(Material.AIR));
					}
				}
			}
			
		//}
	}

	@EventHandler
	public void onburnevent(FurnaceSmeltEvent e)
	{
		e.setCancelled(true);
		//Bukkit.broadcastMessage("Event Fire - MCMANYrecipe FurnaceBurnEvent");
		
		for(Moonfr recipe : PloRecipeList.getFurnaceRecipes())
		{
			//Bukkit.broadcastMessage(recipe.toString());
			//Bukkit.broadcastMessage(recipe.getOutput().toString());
			//Bukkit.broadcastMessage(e.getResult().toString());
			
			ItemStack items = e.getSource();
			
			if(e.getSource().getItemMeta().hasDisplayName())
			{
				e.setCancelled(false);
				e.setResult(new ItemStack(Material.AIR));
				
				//Bukkit.broadcastMessage("조건문 진입 (만일 버킷 등록값과 플긘 등록값이 같다면)");
				//Bukkit.broadcastMessage(e.getSource() + " 버킷에 등록된 구워지는 자원이름");
				//Bukkit.broadcastMessage(recipe.getsource() + " 플러그인에 등록된 구워지는 자원이름");
				
				if (RecipeMatch.matches3(e.getSource(), recipe))
				{
					//Bukkit.broadcastMessage("아이템 등재 성공 화로");
					e.setResult(recipe.getOutput());
					//Bukkit.broadcastMessage(recipe.getOutput().toString());
					break;
				}
				else
				{
					//Bukkit.broadcastMessage("이벤트 취소 화로");
					e.setCancelled(true);
				}
			}	
		}
		
		
		//Bukkit.broadcastMessage(e.getSource().toString() + "소스");
		//Bukkit.broadcastMessage(e.getBlock().toString() + "블럭");
		//Bukkit.broadcastMessage(e.getResult().toString() + "결과");
		
	}
	@EventHandler(priority = EventPriority.LOWEST)
	public void oncraft(CraftItemEvent e)
	{
		ItemStack output = e.getCurrentItem();
		if(main.Debug()){Bukkit.getPlayer("Moon_Eclipse").sendMessage("이벤트 형식" + e.getAction().toString());}
		if(e.isShiftClick() || e.getAction().toString().equalsIgnoreCase("MOVE_TO_OTHER_INVENTORY"))
		{
			/*
			ItemStack Temp_item = LibMain.ReCreateItem(output);			
			if(main.ShapedRecipes.keySet().contains(Temp_item))
			{
				if(main.Debug()){Bukkit.getPlayer("Moon_Eclipse").sendMessage("형식 있는 조합에서 이벤트 캔슬");}
				e.setCancelled(true);
			}
			else if(main.ShapelessRecipes.keySet().contains(Temp_item))
			{
				if(main.Debug()){Bukkit.getPlayer("Moon_Eclipse").sendMessage("형식 없는 조합에서 이벤트 캔슬");}
				e.setCancelled(true);
			}
			*/
			e.setCancelled(true);
		}
		else
		{
			Player p = (Player) e.getWhoClicked();
			try
			{
				ItemStack is = output;
				ItemMeta im = is.getItemMeta();
				ArrayList<String> ls = new ArrayList<String>();
				//Bukkit.broadcastMessage(im.getLore().size() + "<< 가지고있는 로어의 수량");
				for(int i = 0 ; i < im.getLore().size() ; i++)
				{
						String s = im.getLore().get(i).replaceAll("PLAYER", p.getName());
						ls.add(s);
				}
			im.setLore(ls);
			//Bukkit.broadcastMessage(ls.toString());
			is.setItemMeta(im);
			}catch(Exception e1){}
			
			e.setCurrentItem(LibMain.hideFlags_Unbreak(output));
			
		}
	}
	@EventHandler
	public void onquit(PlayerQuitEvent e)
	{
		try{truthmap.remove(e.getPlayer().getName());}catch(Exception ex){}
	}
	
	public boolean ingHasDisname(ItemStack[] inv)
	{
		boolean b = true;
		for(ItemStack item : inv)
		{
			if(item.getTypeId() != 0)
			{
				if(item.hasItemMeta())
				{
					if(!item.getItemMeta().hasDisplayName())
					{
						b = false;
					}
				}
				else
				{
					b = false;
				}
			}
		}
		return b;
	}
	public boolean ingShapedMatch(ItemStack[] recipe, ItemStack[] craft)
	{
		boolean b = true;
		for(int i = 0 ; i < 9 ; i++ )
		{
			if(recipe[i].hasItemMeta() && recipe[i].getItemMeta().hasDisplayName())
			{
				String name1 = recipe[i].getItemMeta().getDisplayName();
				String name2 = craft[i].getItemMeta().getDisplayName();
				if(!name1.equals(name2))
				{
					b = false;
				}
			}
		}

		return b;
	}
	public boolean isThisCustomRecipe_Shapeless(ItemStack[] recipe, ItemStack[] craft)
	{
		boolean b = true;
		HashMap<Integer,Integer> RecipeNames = new HashMap<Integer,Integer>();
		HashMap<Integer,Integer> CraftNames = new HashMap<Integer,Integer>();
		
		for(ItemStack item : recipe)
		{
			RecipeNames.put(item.getTypeId(), 0);
			CraftNames.put(item.getTypeId(), 0);
		}
		for(ItemStack item : craft)
		{
			RecipeNames.put(item.getTypeId(), 0);
			CraftNames.put(item.getTypeId(), 0);
		}
		
		for(ItemStack item : recipe)
		{
			RecipeNames.put(item.getTypeId(), RecipeNames.get(item.getTypeId())+1);
		}
		
		for(ItemStack item : craft)
		{
			CraftNames.put(item.getTypeId(), CraftNames.get(item.getTypeId())+1);
		}
		//if(main.Debug()){Bukkit.getPlayer("Moon_Eclipse").sendMessage("레시피에 등록된 아이템이름들: " + RecipeNames.toString());}
		//if(main.Debug()){Bukkit.getPlayer("Moon_Eclipse").sendMessage("조합대에 등록된 아이템이름들: " + CraftNames.toString());}
		for(Integer name : CraftNames.keySet())
		{
			if(!(CraftNames.get(name) == RecipeNames.get(name)))
			{
				b = false;
			}
		}
		return b;
	}
	public boolean ingShapelessMatch(ItemStack[] recipe, ItemStack[] craft)
	{
		boolean b = true;
		HashMap<String,Integer> RecipeNames = new HashMap<String,Integer>();
		HashMap<String,Integer> CraftNames = new HashMap<String,Integer>();
		
		for(ItemStack item : recipe)
		{
			if(item.hasItemMeta())
			{
				ItemMeta im = item.getItemMeta();
				if(im.hasDisplayName())
				{
					String name = im.getDisplayName();
					RecipeNames.put(name, 0);
					CraftNames.put(name, 0);
				}
			}
		}
		for(ItemStack item : craft)
		{
			if(item.hasItemMeta())
			{
				ItemMeta im = item.getItemMeta();
				if(im.hasDisplayName())
				{
					String name = im.getDisplayName();
					RecipeNames.put(name, 0);
					CraftNames.put(name, 0);
				}
			}
		}
		
		for(ItemStack item : recipe)
		{
			if(item.hasItemMeta())
			{
				ItemMeta im = item.getItemMeta();
				if(im.hasDisplayName())
				{
					String name = im.getDisplayName();
					RecipeNames.put(name, RecipeNames.get(name)+1);
				}
			}
		}
		
		for(ItemStack item : craft)
		{
			if(item.hasItemMeta())
			{
				ItemMeta im = item.getItemMeta();
				if(im.hasDisplayName())
				{
					String name = im.getDisplayName();
					CraftNames.put(name, CraftNames.get(name)+1);
				}
			}
		}
		//if(main.Debug()){Bukkit.getPlayer("Moon_Eclipse").sendMessage("레시피에 등록된 아이템이름들: " + RecipeNames.toString());}
		//if(main.Debug()){Bukkit.getPlayer("Moon_Eclipse").sendMessage("조합대에 등록된 아이템이름들: " + CraftNames.toString());}
		for(String name : CraftNames.keySet())
		{
			if(!(CraftNames.get(name) == RecipeNames.get(name)))
			{
				b = false;
			}
		}
		return b;
	}
	public ItemStack findOutputUseShapelessRecipe(ItemStack[] craft)
	{
		ItemStack is = new ItemStack(0);
		for(ItemStack item : main.ShapelessRecipes.keySet())
		{
			ItemStack[] recipe = main.ShapelessRecipes.get(item);
			if(this.ingShapelessMatch(recipe, craft))
			{
				is = item;
				break;
			}
		}
		return is;
	}
	public ItemStack[] ReValueMatrix(ItemStack[] craft, String index)
	{
		
		int max = 9;
		if(index.equalsIgnoreCase("CRAFTING"))
		{
			max = 4;
		}
		ItemStack[] mat = new ItemStack[max];
		for(int i = 0 ; i < max ; i++)
		{
			mat[i] = craft[i+1];
		}
		
		return mat;
	}
}
