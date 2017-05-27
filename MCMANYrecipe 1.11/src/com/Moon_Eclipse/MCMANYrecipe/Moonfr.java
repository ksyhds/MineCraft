package com.Moon_Eclipse.MCMANYrecipe;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;

public class Moonfr {
	/**
	 * @author Plo457
	 */
	private ItemStack input;
	private ItemStack source;
	private ItemStack output;
	public Moonfr(ItemStack output){
		this.output = output;
		this.input = new ItemStack(Material.AIR);
		this.source = new ItemStack(Material.AIR);

	}
	public ItemStack getOutput(){
		return this.output;
	}
	public void register(){
		FurnaceRecipe rec = new FurnaceRecipe(output, source.getType());
		if(input.getType() != Material.AIR)
			rec.setInput(input.getType(), input.getData().getData());
			Bukkit.addRecipe(rec);
		
		PloRecipeList.addFurnaceRecipe(this);
	}
	public void register2(){
		FurnaceRecipe rec = new FurnaceRecipe(output, source.getType());
		if(input.getType() != Material.AIR)
			rec.setInput(input.getType(), input.getData().getData());
			Bukkit.addRecipe(rec);
			PloRecipeList.addFurnaceRecipe(this);
	}
	public Moonfr addsource(ItemStack i){
		i.setAmount(1);
		this.source = i;
		return this;
	}
	
	public ItemStack getsource(){
		return new ItemStack(source);
	}
	public boolean contain(String s, Material m, Moonfr recipe)
	{
		boolean b = false;

			if(recipe.getsource().getType() == m 
			&& recipe.getsource().getItemMeta().hasDisplayName() 
			&& recipe.getsource().getItemMeta().getDisplayName().equals(s)){b = true;}else{}

				//Bukkit.broadcastMessage(recipe.getsource().getItemMeta().getDisplayName().toString() + " << 레시피에 등록된 이름");
				//Bukkit.broadcastMessage(s + " <<< 넘어온 아이템의 이름");
				//Bukkit.broadcastMessage(b + "화로 컨테인 반환 불린값");

		return b;
	}
	
}

