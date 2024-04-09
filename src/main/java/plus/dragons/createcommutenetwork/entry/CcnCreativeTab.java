package plus.dragons.createcommutenetwork.entry;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import plus.dragons.createcommutenetwork.CommuteNetwork;

public class CcnCreativeTab extends CreativeModeTab {
    public CcnCreativeTab() {
        super(CommuteNetwork.ID + ".base");
    }

    @Override
    public void fillItemList(NonNullList<ItemStack> items) {
        items.add(CcnItems.TEST_RODE.asStack());
        items.add(CcnBlocks.PLATFORM.asStack());
    }


    @Override
    public @NotNull ItemStack makeIcon() {
        return CcnItems.TEST_RODE.asStack();
    }
}
