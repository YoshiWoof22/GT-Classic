package gtclassic.tile.multi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import gtclassic.GTItems;
import gtclassic.GTMod;
import gtclassic.container.GTContainerChemicalReactor;
import gtclassic.gui.GTGuiMachine.GTChemicalReactorGui;
import gtclassic.material.GTMaterial;
import gtclassic.material.GTMaterialFlag;
import gtclassic.material.GTMaterialGen;
import gtclassic.util.int3;
import gtclassic.util.recipe.GTMultiInputRecipeList;
import ic2.api.classic.item.IMachineUpgradeItem.UpgradeType;
import ic2.api.classic.recipe.RecipeModifierHelpers.IRecipeModifier;
import ic2.api.classic.recipe.RecipeModifierHelpers.ModifierType;
import ic2.api.classic.recipe.crafting.RecipeInputFluid;
import ic2.api.classic.recipe.machine.MachineOutput;
import ic2.api.recipe.IRecipeInput;
import ic2.core.RotationList;
import ic2.core.inventory.container.ContainerIC2;
import ic2.core.inventory.filters.IFilter;
import ic2.core.inventory.filters.MachineFilter;
import ic2.core.inventory.management.AccessRule;
import ic2.core.inventory.management.InventoryHandler;
import ic2.core.inventory.management.SlotType;
import ic2.core.platform.lang.components.base.LangComponentHolder.LocaleBlockComp;
import ic2.core.platform.lang.components.base.LocaleComp;
import ic2.core.platform.registry.Ic2Sounds;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class GTTileMultiChemicalReactor extends GTTileMultiBase {

	protected static final int[] slotInputs = { 0, 1, 2, 3, 4, 5 };
	protected static final int[] slotOutputs = { 6, 7, 8, 9, 10, 11 };

	IFilter filter = new MachineFilter(this);

	public static final GTMultiInputRecipeList RECIPE_LIST = new GTMultiInputRecipeList("gt.chemicalreactor");
	public static final ResourceLocation GUI_LOCATION = new ResourceLocation(GTMod.MODID,
			"textures/gui/chemicalreactor.png");

	public GTTileMultiChemicalReactor() {
		super(12, 2, 256, 512);
		maxEnergy = 10000;
	}

	@Override
	protected void addSlots(InventoryHandler handler) {
		handler.registerDefaultSideAccess(AccessRule.Both, RotationList.ALL);
		handler.registerDefaultSlotAccess(AccessRule.Import, slotInputs);
		handler.registerDefaultSlotAccess(AccessRule.Export, slotOutputs);
		handler.registerDefaultSlotsForSide(RotationList.UP, slotInputs);
		handler.registerDefaultSlotsForSide(RotationList.HORIZONTAL, slotInputs);
		handler.registerDefaultSlotsForSide(RotationList.HORIZONTAL, slotOutputs);
		handler.registerSlotType(SlotType.Input, slotInputs);
		handler.registerSlotType(SlotType.Output, slotOutputs);
	}

	@Override
	public TileEntity getImportTile() {
		int3 dir = new int3(getPos(), getFacing());
		return world.getTileEntity(dir.left(1).back(1).up(1).asBlockPos());
	}

	@Override
	public TileEntity getExportTile() {
		int3 dir = new int3(getPos(), getFacing());
		return world.getTileEntity(dir.right(1).back(1).up(1).asBlockPos());
	}

	@Override
	public LocaleComp getBlockName() {
		return new LocaleBlockComp(this.getBlockType().getUnlocalizedName());
	}

	@Override
	public Set<UpgradeType> getSupportedTypes() {
		return new LinkedHashSet(Arrays.asList(UpgradeType.values()));
	}

	@Override
	public ContainerIC2 getGuiContainer(EntityPlayer player) {
		return new GTContainerChemicalReactor(player.inventory, this);
	}

	@Override
	public Class<? extends GuiScreen> getGuiClass(EntityPlayer player) {
		return GTChemicalReactorGui.class;
	}

	@Override
	public int[] getInputSlots() {
		return slotInputs;
	}

	@Override
	public IFilter[] getInputFilters(int[] slots) {
		return new IFilter[] { filter };
	}

	@Override
	public boolean isRecipeSlot(int slot) {
		return true;
	}

	@Override
	public int[] getOutputSlots() {
		return slotOutputs;
	}

	@Override
	public GTMultiInputRecipeList getRecipeList() {
		return RECIPE_LIST;
	}

	public ResourceLocation getGuiTexture() {
		return GUI_LOCATION;
	}

	@Override
	public boolean hasGui(EntityPlayer player) {
		return true;
	}

	@Override
	public ResourceLocation getStartSoundFile() {
		return Ic2Sounds.compressorOp;
	}

	// @formatter:off
	public static void init() {
		
		addRecipe(new IRecipeInput[] { 
				input("dustBauxite", 1), 
				input(GTMaterialGen.getFluid(GTMaterial.Lye, 3))}, 
				totalEu(48000), 
				GTMaterialGen.getFluid(GTMaterial.BauxiteTailings, 1),
				GTMaterialGen.get(GTItems.testTube, 2));
		
		addRecipe(new IRecipeInput[] { 
				input("dustSalt", 1), 
				input(GTMaterialGen.getModFluid("water", 1))}, 
				totalEu(48000), 
				GTMaterialGen.getFluid(GTMaterial.Brine, 1));
		
		addRecipe(new IRecipeInput[] { 
				input(GTMaterialGen.get(Items.WATER_BUCKET)), 
				input(GTMaterialGen.getFluid(GTMaterial.SulfurDioxide, 1))}, 
				totalEu(24000), 
				GTMaterialGen.getFluid(GTMaterial.SulfuricAcid, 1));
		
		addRecipe(new IRecipeInput[] { 
				input("dustDarkAshes", 12), 
				input(GTMaterialGen.getFluid(GTMaterial.SulfuricAcid, 4))}, 
				totalEu(24000), 
				GTMaterialGen.getFluid(GTMaterial.Lye, 4));
		
		addRecipe(new IRecipeInput[] { 
				input("dustBauxiteTailings", 12), 
				input(GTMaterialGen.getFluid(GTMaterial.SulfuricAcid, 8))}, 
				totalEu(150000),
				GTMaterialGen.getDust(GTMaterial.Titanium, 1),
				GTMaterialGen.getDust(GTMaterial.Alumina, 8), 
				GTMaterialGen.getDust(GTMaterial.Silicon, 2), 
				GTMaterialGen.getFluid(GTMaterial.Hydrogen, 5),
				GTMaterialGen.getFluid(GTMaterial.Oxygen, 3));
		
		Boolean speiger_is_testing_recipes = false;
		if (speiger_is_testing_recipes) {
			addRecipe(new IRecipeInput[] { 
					new RecipeInputFluid(GTMaterialGen.getFluidTest(GTMaterial.Sodium)),
					new RecipeInputFluid(GTMaterialGen.getFluidTest(GTMaterial.Oxygen)), 
					new RecipeInputFluid(GTMaterialGen.getFluidTest(GTMaterial.Hydrogen)), }, 
					totalEu(12000), 
					GTMaterialGen.getFluid(GTMaterial.Lye, 1),
					GTMaterialGen.get(GTItems.testTube, 2));
		}
	}
	// @formatter:on

	public static IRecipeModifier[] totalEu(int total) {
		return new IRecipeModifier[] { ModifierType.RECIPE_LENGTH.create((total / 256) - 100) };
	}

	public static void addRecipe(IRecipeInput[] inputs, IRecipeModifier[] modifiers, ItemStack... outputs) {
		List<IRecipeInput> inlist = new ArrayList<>();
		List<ItemStack> outlist = new ArrayList<>();

		for (IRecipeInput input : inputs) {
			inlist.add(input);
		}
		NBTTagCompound mods = new NBTTagCompound();
		for (IRecipeModifier modifier : modifiers) {
			modifier.apply(mods);
		}
		for (ItemStack output : outputs) {
			outlist.add(output);
		}
		addRecipe(inlist, new MachineOutput(mods, outlist));
	}

	static void addRecipe(List<IRecipeInput> input, MachineOutput output) {
		RECIPE_LIST.addRecipe(input, output, output.getAllOutputs().get(0).getDisplayName());
	}

	@Override
	public boolean checkStructure() {
		int3 dir = new int3(getPos(), getFacing());
		// layer 0
		if (!(isMachineCasing(dir.left(1)) && isMachineCasing(dir.back(1)) && isMachineCasing(dir.back(1))
				&& isMachineCasing(dir.right(1)) && isMachineCasing(dir.forward(1)) && isMachineCasing(dir.right(1))
				&& isMachineCasing(dir.forward(1)) && isMachineCasing(dir.back(2)) &&
				// bottom layer
				isMachineCasing(dir.down(1)) && isMachineCasing(dir.forward(1)) && isMachineCasing(dir.forward(1))
				&& isMachineCasing(dir.left(1)) && isMachineCasing(dir.back(1)) && isMachineCasing(dir.back(1))
				&& isMachineCasing(dir.left(1)) && isMachineCasing(dir.forward(1))
				&& isMachineCasing(dir.forward(1)))) {
			return false;
		}
		return true;
	}

	public boolean isMachineCasing(int3 pos) {
		return world.getBlockState(pos.asBlockPos()) == GTMaterialGen
				.getBlock(GTMaterial.StainlessSteel, GTMaterialFlag.WALL).getDefaultState();
	}

}
