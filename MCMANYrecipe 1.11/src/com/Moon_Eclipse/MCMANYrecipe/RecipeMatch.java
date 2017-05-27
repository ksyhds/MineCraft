package com.Moon_Eclipse.MCMANYrecipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RecipeMatch {
	/**
	 * @author Plo457
	 */
	public static boolean matches(List<ItemStack> items, PloRecipe recipe)
	{
		/*for (int i = 0;i < 9;i++){
			ItemStack it = recipe.getSlots()[i];
			if (it.hasItemMeta()){
				ItemMeta im = it.getItemMeta();
				im.setLore(new ArrayList<String>());
				for (Entry<Enchantment, Integer> enchant : im.getEnchants().entrySet()){
					im.removeEnchant(enchant.getKey());
				}
				if (it.getType().getMaxDurability() > 30){
					it.setDurability((short) 0);
				}
				it.setItemMeta(im);
			}
			recipe.setSlot(i+1, it);
		}*/
		// 여기까지 이름말고 나머지 인챈트, 로어 삭제 부분
		boolean match = true;
		//Bukkit.broadcastMessage(recipe.getOutput().toString());
		for (int i = 0;i < 9;i++)
		{
			//Bukkit.broadcastMessage("조건문 넘어온 매치 반복문 번호(크래프트 자리) " + i);
			//Bukkit.broadcastMessage("�e-------");
			//Bukkit.broadcastMessage("�c"+items.get(i).getType().toString()+" �e== �6"+recipe.getSlots()[i].getType().toString());
			/*if (items.get(i).hasItemMeta() && items.get(i).getItemMeta().hasDisplayName()){
				Bukkit.broadcastMessage("�cCT:HasDisplay:"+items.get(i).getItemMeta().getDisplayName());
			}
			if (recipe.getSlots()[i].hasItemMeta() && recipe.getSlots()[i].getItemMeta().hasDisplayName()){
				Bukkit.broadcastMessage("�6RP:HasDisplay:"+recipe.getSlots()[i].getItemMeta().getDisplayName());
			}*/
			try
			{
				//Bukkit.broadcastMessage(recipe.getSlots()[i].getItemMeta().getDisplayName().toString() + "레시피에서 해당 자리의 아이템이름");
				//Bukkit.broadcastMessage(items.get(i).getItemMeta().getDisplayName().toString() + "조합아이템의 이름");
			}
			catch(Exception exe){}
			if ((	items.get(i).hasItemMeta() 
					&& items.get(i).getItemMeta().hasDisplayName() 
					&& recipe.getSlots()[i].hasItemMeta() 
					&& recipe.getSlots()[i].getItemMeta().hasDisplayName() 
					&& recipe.getSlots()[i].getItemMeta().getDisplayName().equalsIgnoreCase(items.get(i).getItemMeta().getDisplayName())))
			{
				//Bukkit.broadcastMessage(recipe.getSlots()[i].getItemMeta().getDisplayName().toString() + "레시피에서 해당 자리의 아이템이름");
				//Bukkit.broadcastMessage(items.get(i).getItemMeta().getDisplayName().toString() + "조합아이템의 이름");
				//Bukkit.broadcastMessage("DisplayName: True");
			} 
			else if (items.get(i).hasItemMeta() == false && recipe.getSlots()[i].hasItemMeta() == false)
			{
				//Bukkit.broadcastMessage("DisplayName: True");
			} 
			else if (items.get(i).hasItemMeta() == false 
					&& recipe.getSlots()[i].hasItemMeta() == false 
					&& items.get(i).getItemMeta().hasDisplayName() == false 
					&& recipe.getSlots()[i].getItemMeta().hasDisplayName() == false)
			{
				//Bukkit.broadcastMessage("DisplayName: True");
			} 
			else if (items.get(i).hasItemMeta() == true 
					&& recipe.getSlots()[i].hasItemMeta() == false
					&& items.get(i).getItemMeta().hasDisplayName() == false 
					){
				//Bukkit.broadcastMessage("DisplayName: True");
			}
			else 
			{
				//Bukkit.broadcastMessage("조합 하는 재료 아이템에 디스플레이 이름 없음.");
				
				//Bukkit.broadcastMessage(items.get(i).toString());
				//Bukkit.broadcastMessage(recipe.getSlots()[i].toString());
				//Bukkit.broadcastMessage("DisplayName: False");
				match = false;
			}
			if (!(items.get(i).getType() == recipe.getSlots()[i].getType())){
				//if(main.Debug()){Bukkit.broadcastMessage("양식조합의 각 성분 자리 조건문에서 마테리얼 타입 부정");}
				match = false;
				//Bukkit.broadcastMessage("Type: False");
			}
			else 
			{
				//Bukkit.broadcastMessage("Type: True");
			}
			/*if (items.get(i) != recipe.getSlots()[i]){
				match = false;
				Bukkit.broadcastMessage("�4False!");
			} else {
				Bukkit.broadcastMessage("�2True!");
			}*/
		}
		//Bukkit.broadcastMessage("match 리턴값 " + match);
		return match;
	}
	public static boolean matches2(List<ItemStack> items, MoonShapeLessRecipe recipe){
		for (int i = 0;i < 9;i++)
		{
			ItemStack it = items.get(i).clone();
			if (it.hasItemMeta())
			{
				ItemMeta im = it.getItemMeta();
				im.setLore(new ArrayList<String>());
				for (Entry<Enchantment, Integer> enchant : im.getEnchants().entrySet())
				{
					im.removeEnchant(enchant.getKey());
				}
				if (it.getType().getMaxDurability() > 30){
					it.setDurability((short) 0);
				}
				it.setItemMeta(im);
			}
			items.set(i,it);
		}
		/*for (int i = 0;i < 9;i++){
			ItemStack it = recipe.getSlots()[i];
			if (it.hasItemMeta()){
				ItemMeta im = it.getItemMeta();
				im.setLore(new ArrayList<String>());
				for (Entry<Enchantment, Integer> enchant : im.getEnchants().entrySet()){
					im.removeEnchant(enchant.getKey());
				}
				if (it.getType().getMaxDurability() > 30){
					it.setDurability((short) 0);
				}
				it.setItemMeta(im);
			}
			recipe.setSlot(i+1, it);
		}*/
		boolean match = false;
		boolean[] b123 = new boolean[9];
		int aircount = 0;
		
		for (int i = 0;i < 9;i++)
		{	
			b123[i] = true;
			if(!(items.get(i).getType().equals(Material.AIR)))
			{
				//if(main.Debug()){Bukkit.broadcastMessage("조건문 넘어온 매치 반복문 번호(크래프트 자리) less" + i);}
				//Bukkit.broadcastMessage(items.get(i).getItemMeta().getDisplayName().toString() + " 조합아이템의 이름");
				if(recipe.contain(items.get(i).getItemMeta().getDisplayName(), items.get(i).getType(), recipe))
				{}
				else
				{
					b123[i] = false; //if(main.Debug()){Bukkit.broadcastMessage("less컨테인 조건문에서 불린값 부정");}
				}
			}else{aircount += 1;}
			
			
		}
		
		if(b123[0] && b123[1] && b123[2] && b123[3] && b123[4] && b123[5] && b123[6] && b123[7] && b123[8])
		{
			match = true;
		}
		if(aircount == 8){match = false;}
		
		//Bukkit.broadcastMessage("리턴값 less" + match);
		return match;
	}
	public static boolean matches3(ItemStack items, Moonfr recipe){
		boolean match = true;

			if(!(items.getType().equals(Material.AIR)))
			{
				if(recipe.contain(items.getItemMeta().getDisplayName(), items.getType(), recipe)){}
				else{match = false;
				
				//Bukkit.broadcastMessage("화로 컨테인 조건문에서 불린값 부정");
				
				}
			}else{}
			//Bukkit.broadcastMessage("리턴값 화로 " + match);
		return match;
	}
	
	public static boolean hasDisname(List<ItemStack> items)
	{
		boolean match = false;
		for(int i = 0 ; i < 9 ; i++)
		{
			try
			{
				
				if(items.get(i).getItemMeta().hasDisplayName()){match = true;break;}else{/*Bukkit.broadcastMessage(i + "번 자리는 메타가 없음.");*/}
				
			}catch(Exception ex){}
		}	
		return match;
	}
	
	
}
