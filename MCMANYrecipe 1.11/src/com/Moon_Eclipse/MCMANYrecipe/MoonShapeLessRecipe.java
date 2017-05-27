package com.Moon_Eclipse.MCMANYrecipe;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;

public class MoonShapeLessRecipe {
/**
 * @author Plo457
 */
private ItemStack slot1;
private ItemStack slot2;
private ItemStack slot3;
private ItemStack slot4;
private ItemStack slot5;
private ItemStack slot6;
private ItemStack slot7;
private ItemStack slot8;
private ItemStack slot9;
private ItemStack output;
public MoonShapeLessRecipe(ItemStack output){
	this.output = output;
	this.slot1 = new ItemStack(Material.AIR);
	this.slot2 = new ItemStack(Material.AIR);
	this.slot3 = new ItemStack(Material.AIR);
	this.slot4 = new ItemStack(Material.AIR);
	this.slot5 = new ItemStack(Material.AIR);
	this.slot6 = new ItemStack(Material.AIR);
	this.slot7 = new ItemStack(Material.AIR);
	this.slot8 = new ItemStack(Material.AIR);
	this.slot9 = new ItemStack(Material.AIR);
}
public ItemStack getOutput(){
	return this.output;
}
public void register()
{
	ShapelessRecipe rec = new ShapelessRecipe(output);
	if (slot1.getType() != Material.AIR)
	rec.addIngredient(slot1.getType(), slot1.getData().getData());
	if (slot2.getType() != Material.AIR)
	rec.addIngredient(slot2.getType(), slot2.getData().getData());
	if (slot3.getType() != Material.AIR)
	rec.addIngredient(slot3.getType(), slot3.getData().getData());
	if (slot4.getType() != Material.AIR)
	rec.addIngredient(slot4.getType(), slot4.getData().getData());
	if (slot5.getType() != Material.AIR)
	rec.addIngredient(slot5.getType(), slot5.getData().getData());
	if (slot6.getType() != Material.AIR)
	rec.addIngredient(slot6.getType(), slot6.getData().getData());
	if (slot7.getType() != Material.AIR)
	rec.addIngredient(slot7.getType(), slot7.getData().getData());
	if (slot8.getType() != Material.AIR)
	rec.addIngredient(slot8.getType(), slot8.getData().getData());
	if (slot9.getType() != Material.AIR)
	rec.addIngredient(slot9.getType(), slot9.getData().getData());
	Bukkit.addRecipe(rec);
	PloRecipeList.addShapelessRecipe(this);
}
public void register2()
{
	ShapelessRecipe rec = new ShapelessRecipe(output);
	if (slot1.getType() != Material.AIR)
	rec.addIngredient(slot1.getType(), slot1.getData().getData());
	if (slot2.getType() != Material.AIR)
	rec.addIngredient(slot2.getType(), slot2.getData().getData());
	if (slot3.getType() != Material.AIR)
	rec.addIngredient(slot3.getType(), slot3.getData().getData());
	if (slot4.getType() != Material.AIR)
	rec.addIngredient(slot4.getType(), slot4.getData().getData());
	if (slot5.getType() != Material.AIR)
	rec.addIngredient(slot5.getType(), slot5.getData().getData());
	if (slot6.getType() != Material.AIR)
	rec.addIngredient(slot6.getType(), slot6.getData().getData());
	if (slot7.getType() != Material.AIR)
	rec.addIngredient(slot7.getType(), slot7.getData().getData());
	if (slot8.getType() != Material.AIR)
	rec.addIngredient(slot8.getType(), slot8.getData().getData());
	if (slot9.getType() != Material.AIR)
	rec.addIngredient(slot9.getType(), slot9.getData().getData());
	Bukkit.addRecipe(rec);
	PloRecipeList.addShapelessRecipe(this);
}
public MoonShapeLessRecipe addItem1(ItemStack i){
	i.setAmount(1);
	this.slot1 = i;
	return this;
}
public MoonShapeLessRecipe addItem2(ItemStack i){
	i.setAmount(1);
	this.slot2 = i;
	return this;
}
public MoonShapeLessRecipe addItem3(ItemStack i){
	i.setAmount(1);
	this.slot3 = i;
	return this;
}
public MoonShapeLessRecipe addItem4(ItemStack i){
	i.setAmount(1);
	this.slot4 = i;
	return this;
}
public MoonShapeLessRecipe addItem5(ItemStack i){
	i.setAmount(1);
	this.slot5 = i;
	return this;
}
public MoonShapeLessRecipe addItem6(ItemStack i){
	i.setAmount(1);
	this.slot6 = i;
	return this;
}
public MoonShapeLessRecipe addItem7(ItemStack i){
	i.setAmount(1);
	this.slot7 = i;
	return this;
}
public MoonShapeLessRecipe addItem8(ItemStack i){
	i.setAmount(1);
	this.slot8 = i;
	return this;
}
public MoonShapeLessRecipe addItem9(ItemStack i){
	i.setAmount(1);
	this.slot9 = i;
	return this;
}
public ItemStack[] getSlots(){
	return new ItemStack[]{slot1,slot2,slot3,slot4,slot5,slot6,slot7,slot8,slot9};
}
public boolean contain(String s, Material m, MoonShapeLessRecipe recipe)
{
	
	
	boolean b = false;
	for (int i = 0;i < 9;i++)
	{
		
		//Bukkit.broadcastMessage(i + "");
		if(recipe.getSlots()[i].getType() == m 
		&& recipe.getSlots()[i].getItemMeta().hasDisplayName() 
		&& recipe.getSlots()[i].getItemMeta().getDisplayName().equals(s)){b = true;}else{}
		
	}
	
	//Bukkit.broadcastMessage(b + "셰이프리스 컨테인 메소드에서 반환되는 불린값.");
	return b;
}
	//Bukkit.broadcastMessage("Set slot "+i);

}

