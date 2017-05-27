package com.Moon_Eclipse.MCMANYrecipe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Recipe;

public class PloRecipeList {
	/**
	 * @author Plo457
	 */
	static List<PloRecipe> shaped;
	static List<MoonShapeLessRecipe> shapeless;
	static List<Moonfr> Furnace;
	public static void addShapedRecipe(PloRecipe recipe)
	{
		if (shaped == null)
		{
			shaped = new ArrayList<PloRecipe>();
		}
		shaped.add(recipe);
		//Bukkit.broadcastMessage(recipe.getSlots() + " 레시피 추가 구문");
		//Bukkit.broadcastMessage(shaped.size() + "양식있는 레시피 갯수");
	}
	public static List<PloRecipe> getShapedRecipes()
	{
		return shaped;
	}
	public static void addShapelessRecipe(MoonShapeLessRecipe recipe)
	{
		if (shapeless == null)
		{
			shapeless = new ArrayList<MoonShapeLessRecipe>();
		}
		shapeless.add(recipe);
		//Bukkit.broadcastMessage(shapeless.size() + "양식없는 레시피 갯수");
	}
	public static List<MoonShapeLessRecipe> getShapelessRecipes()
	{
		return shapeless;
	}
	public static void addFurnaceRecipe(Moonfr recipe)
	{
		if (Furnace == null)
		{
			Furnace = new ArrayList<Moonfr>();
		}
		Furnace.add(recipe);
		//Bukkit.broadcastMessage(recipe.toString() + "화로 레시피 추가 구문");
		//Bukkit.broadcastMessage(Furnace.size() + "화로 레시피 갯수");
	}
	public static List<Moonfr> getFurnaceRecipes()
	{
		return Furnace;
	}

	public static List<PloRecipe> resetrecipes()
	{
		shaped = new ArrayList<PloRecipe>();
		shapeless = new ArrayList<MoonShapeLessRecipe>();
		Furnace = new ArrayList<Moonfr>();
		return shaped;
	}
	public static boolean isnull()
	{
		if(shaped.isEmpty() || shapeless.isEmpty() || Furnace.isEmpty())
		{
			return true;
		}
		return false;
	}
}
