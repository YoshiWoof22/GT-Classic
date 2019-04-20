package gtclassic.util;

import gtclassic.tile.GTTileBaseMultiInputMachine;
import ic2.api.classic.tile.IMachine;
import ic2.core.IC2;
import ic2.core.inventory.slots.SlotUpgrade;

public class GTSlotUpgrade extends SlotUpgrade {

	public GTSlotUpgrade(IMachine par1, int par2, int par3, int par4) {
		super(par1, par2, par3, par4);
	}

	@Override
	public void onSlotChanged() {
		if (IC2.platform.isSimulating()) {
			if (getMachine() instanceof GTTileBaseMultiInputMachine) {
				((GTTileBaseMultiInputMachine) getMachine()).setOverclockRates();
			}
		}
	}

}
