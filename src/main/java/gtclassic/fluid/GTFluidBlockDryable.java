package gtclassic.fluid;

import java.util.List;
import java.util.Random;

import gtclassic.GTMod;
import gtclassic.material.GTMaterial;
import ic2.core.platform.lang.ILocaleBlock;
import ic2.core.platform.lang.components.base.LangComponentHolder.LocaleBlockComp;
import ic2.core.platform.lang.components.base.LocaleComp;
import ic2.core.platform.registry.Ic2Lang;
import ic2.core.platform.textures.models.BaseModel;
import ic2.core.platform.textures.obj.ICustomModeledBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GTFluidBlockDryable extends BlockFluidClassic implements ILocaleBlock, ICustomModeledBlock {

	LocaleComp comp;
	Block result;
	GTMaterial mat;

	public GTFluidBlockDryable(GTMaterial mat, Block result) {
		super(FluidRegistry.getFluid(mat.getDisplayName().toLowerCase()), Material.WATER);
		setRegistryName(mat.getDisplayName().toLowerCase() + "_fluidblock");
		setUnlocalizedName(GTMod.MODID + "." + mat.getDisplayName().toLowerCase() + "_fluidblock");
		setCreativeTab(GTMod.creativeTabGT);
		this.mat = mat;
		this.comp = Ic2Lang.nullKey;
		this.result = result;
		this.setTickRandomly(true);
		this.setTickRate(10);
	}

	public LocaleComp getName() {
		return this.comp;
	}

	public Block setUnlocalizedName(LocaleComp name) {
		this.comp = name;
		return super.setUnlocalizedName(name.getUnlocalized());
	}

	@Override
	public Block setUnlocalizedName(String name) {
		this.comp = new LocaleBlockComp("tile." + name);
		return super.setUnlocalizedName(name);
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {

		if (state.getValue(LEVEL) == 0) {
			IBlockState down = worldIn.getBlockState(pos.down(1));
			if (isDryingBlock(down) && isCorrectEnviornment(worldIn, pos)) {
				if (rand.nextInt(7) == 0) {
					worldIn.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_STONE_BREAK, SoundCategory.BLOCKS,
							1.0F, 1.0F);
					worldIn.setBlockState(pos, result.getDefaultState());
				}
			}
		}
		super.updateTick(worldIn, pos, state, rand);
	}

	public List<IBlockState> getValidModelStates() {
		return this.getBlockState().getValidStates();
	}

	public boolean isDryingBlock(IBlockState state) {
		return (state == Blocks.HARDENED_CLAY.getDefaultState() || state == Blocks.CONCRETE.getDefaultState());
	}

	public boolean isCorrectEnviornment(World worldIn, BlockPos pos) {
		if (worldIn.provider.hasSkyLight()) {
			if (!worldIn.canBlockSeeSky(pos)) {
				return false;
			} else {
				Biome biome = worldIn.getBiome(pos);
				if (BiomeDictionary.hasType(biome, Type.COLD)) {
					return false;
				}
				if (BiomeDictionary.hasType(biome, Type.HOT) && !biome.canRain()) {
					return true;
				} else {
					return !worldIn.isRaining() && !worldIn.isThundering();
				}
			}
		} else {
			return false;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BaseModel getModelFromState(IBlockState state) {
		return new GTFluidModel(FluidRegistry.getFluid(mat.getDisplayName().toLowerCase()));
	}

	@Override
	public IBlockState getStateFromStack(ItemStack stack) {
		return this.getDefaultState();
	}

}
