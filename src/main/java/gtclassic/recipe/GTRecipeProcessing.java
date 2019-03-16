package gtclassic.recipe;

import java.util.Iterator;
import java.util.Map;

import gtclassic.GTBlocks;
import gtclassic.material.GTMaterial;
import gtclassic.material.GTMaterialGen;
import gtclassic.tile.GTTileBlastFurnace;
import gtclassic.tile.GTTileFusionComputer;
import ic2.api.classic.recipe.ClassicRecipes;
import ic2.api.classic.recipe.machine.IMachineRecipeList;
import ic2.core.block.machine.low.TileEntityCompressor;
import ic2.core.block.machine.low.TileEntityExtractor;
import ic2.core.block.machine.low.TileEntityMacerator;
import ic2.core.platform.registry.Ic2Items;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class GTRecipeProcessing {

	static GTMaterialGen GT;
	static GTMaterial M;

	static IMachineRecipeList smelting = ClassicRecipes.furnace;

	public static void recipesProcessing() {

		removeSmelting(GT.getIc2(Ic2Items.rubber, 1));
		removeSmelting(GT.getIc2(Ic2Items.refinedIronIngot, 1));

		/*
		 * Recipes specific to GT Classic blocks
		 */
		maceratorUtil("oreBauxite", 1, GT.getDust(M.Bauxite, 4));
		maceratorUtil("oreIridium", 1, GT.getIc2(Ic2Items.iridiumOre, 2));
		maceratorUtil("orePyrite", 1, GT.getDust(M.Pyrite, 5));
		maceratorUtil("oreCinnabar", 1, GT.getDust(M.Cinnabar, 5));
		maceratorUtil("oreSphalerite", 1, GT.getDust(M.Sphalerite, 5));
		maceratorUtil("oreSodalite", 1, GT.getDust(M.Sodalite, 12));

		GameRegistry.addSmelting(GT.getDust(M.Galena, 1), (GT.getNugget(M.Lead, 3)), 0.1F);
		GameRegistry.addSmelting(GTBlocks.iridiumOre, (GT.getIc2(Ic2Items.iridiumOre, 1)), 0.1F);
		GameRegistry.addSmelting(GT.getDust(M.Magnetite, 1), GT.get(Items.IRON_NUGGET, 3), 0.1F);
		GameRegistry.addSmelting(GT.get(GTBlocks.magnetiteSand), GT.get(Items.IRON_NUGGET, 3), 0.1F);
		GameRegistry.addSmelting(GTBlocks.pyriteOre, GT.get(Items.IRON_INGOT), 0.1F);
		GameRegistry.addSmelting(GTBlocks.slagSand, GT.get(GTBlocks.slagGlass), 0.1F);

		TileEntityExtractor.addRecipe("oreRuby", 1, GT.getGem(M.Ruby, 3), 0.1F);
		TileEntityExtractor.addRecipe("oreSapphire", 1, GT.getGem(M.Sapphire, 3), 0.1F);
		TileEntityExtractor.addRecipe("oreOlivine", 1, GT.getGem(M.Olivine, 3), 0.1F);

		/*
		 * Maceration recipes not covered by Ic2c automatically or that need to be
		 * different
		 */
		TileEntityMacerator.addRecipe(GT.get(Items.FLINT, 1), GT.getDust(M.Flint, 1), 0.1F);
		// TODO add granite recipe to granite dust
		TileEntityMacerator.addRecipe(Ic2Items.uraniumDrop, 1, GT.getDust(M.Uranium, 1), 0.1F);
		TileEntityMacerator.addRecipe("enderpearl", 1, GT.getDust(M.EnderPearl, 1), 0.2F);
		TileEntityMacerator.addRecipe(GT.get(Items.ENDER_EYE, 1), GT.getDust(M.EnderEye, 1), 0.2F);
		TileEntityMacerator.addRecipe("gemDiamond", 1, GT.getDust(M.Diamond, 1), 0.1F);
		TileEntityMacerator.addRecipe("gemEmerald", 1, GT.getDust(M.Emerald, 1), 0.1F);
		TileEntityMacerator.addRecipe("plateRefinedIron", 1, GT.getIc2(Ic2Items.ironDust, 1));

		/*
		 * Compressor recipes
		 */
		TileEntityCompressor.addRecipe(GT.getChemical(M.Carbon, 8), GT.getIc2(Ic2Items.carbonFiber, 1), 0.1F);
		TileEntityCompressor.addRecipe("dustUranium", 1, GT.getIc2(Ic2Items.uraniumIngot, 1), 0.1F);
		TileEntityCompressor.addRecipe("dustEmerald", 1, GT.get(Items.EMERALD), 0.1F);
		TileEntityCompressor.addRecipe("dustDiamond", 1, GT.get(Items.DIAMOND), 0.1F);
		TileEntityCompressor.addRecipe("dustGraphite", 1, GT.getIngot(M.Graphite, 1), 0.1F);
		TileEntityCompressor.addRecipe("dustSmallGraphite", 4, GT.getIngot(M.Graphite, 1), 0.1F);

		/*
		 * Just a few test fusion recipes
		 */
		GTTileFusionComputer.addRecipe("dustTungsten", 1, GT.getChemical(M.Lithium, 1),
				GT.getIc2(Ic2Items.iridiumOre, 1));
		GTTileFusionComputer.addRecipe("dustTungsten", 1, GT.getChemical(M.Berilium, 1), GT.getDust(M.Platinum, 1));

		/*
		 * Test recipes for new machines
		 */

		// GTTileArcFurnace.addRecipe("ingotIron", 1, "dustCoal", 2,
		// GT.getIngot(M.Steel, 1), GT.getDust(M.DarkAshes, 2));
		GTTileBlastFurnace.addRecipe("ingotIron", 1, "dustCoal", 2, GT.getIngot(M.Steel, 1),
				GT.getDust(M.DarkAshes, 2));
		GTTileBlastFurnace.addRecipe("ingotRefinedIron", 1, "dustCoal", 2, GT.getIngot(M.Steel, 1),
				GT.getDust(M.DarkAshes, 2));
		GTTileBlastFurnace.addRecipe("oreIron", 1, "dustCalcite", 1, GT.getIc2(Ic2Items.refinedIronIngot, 3),
				GT.getSmallDust(M.Slag, 2));
		GTTileBlastFurnace.addRecipe("dustPyrite", 1, "dustCalcite", 1, GT.getIc2(Ic2Items.refinedIronIngot, 2),
				GT.getSmallDust(M.Slag, 1));
		GTTileBlastFurnace.addRecipe("dustMagnetite", 1, "dustCalcite", 1, GT.getIc2(Ic2Items.refinedIronIngot, 2),
				GT.getSmallDust(M.Slag, 1));
		GTTileBlastFurnace.addRecipe("dustTantalum", 1, GT.getIngot(M.Tantalum, 1));
		GTTileBlastFurnace.addRecipe("dustSmallTantalum", 4, GT.getIngot(M.Tantalum, 1));
		GTTileBlastFurnace.addRecipe("dustTungsten", 1, GT.getIngot(M.Tungsten, 1));
		GTTileBlastFurnace.addRecipe("dustSmallTungsten", 4, GT.getIngot(M.Tungsten, 1));

	}

	/*
	 * Adds a macerator recipe while removing duplicates generated by ic2c
	 */
	public static void maceratorUtil(String input, int amount, ItemStack output) {
		TileEntityMacerator.oreBlacklist.add(input);
		TileEntityMacerator.addRecipe(input, amount, output);
	}

	/*
	 * removing smelting recipes code by Muramasa
	 */
	public static void removeSmelting(ItemStack resultStack) {
		ItemStack recipeResult;
		Map<ItemStack, ItemStack> recipes = FurnaceRecipes.instance().getSmeltingList();
		Iterator<ItemStack> iterator = recipes.keySet().iterator();
		while (iterator.hasNext()) {
			ItemStack tmpRecipe = iterator.next();
			recipeResult = recipes.get(tmpRecipe);
			if (ItemStack.areItemStacksEqual(resultStack, recipeResult)) {
				iterator.remove();
			}
		}
	}

}