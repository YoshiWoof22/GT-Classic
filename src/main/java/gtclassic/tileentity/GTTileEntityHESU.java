package gtclassic.tileentity;

import gtclassic.GTClassic;
import gtclassic.container.GTContainerHESU;
import gtclassic.util.GTLang;
import ic2.core.block.base.tile.TileEntityElectricBlock;
import ic2.core.inventory.container.ContainerIC2;
import ic2.core.platform.lang.components.base.LocaleComp;
import ic2.core.platform.lang.components.base.LangComponentHolder.LocaleBlockComp;
import net.minecraft.entity.player.EntityPlayer;

public class GTTileEntityHESU extends TileEntityElectricBlock {
	
	public GTTileEntityHESU() 
    {
        super(4, 2048, 100000000);
    }

    @Override
    public ContainerIC2 getGuiContainer(EntityPlayer player) 
    {
        return new GTContainerHESU(player.inventory, this);
    }


    @Override
    public int getProcessRate()
    {
        return 128;
    }
    
    @Override
    public double getWrenchDropRate() 
    {
		return 0.70D;
	}

    public LocaleComp getBlockName() {
    	return GTLang.hfsu;
	}

	@Override
	public void update() 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public String getName() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasCustomName() 
	{
		// TODO Auto-generated method stub
		return false;
	}
}