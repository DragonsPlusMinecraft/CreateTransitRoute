package plus.dragons.createcommutenetwork.entry;

import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
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
        items.add(CcnBlocks.ROUTE_ADMIN_PANEL.asStack());
        items.add(CcnBlocks.STATION_ADMIN_PANEL.asStack());
        items.add(CcnBlocks.CREATIVE_ROUTE_ADMIN_PANEL.asStack());
        items.add(CcnBlocks.CREATIVE_STATION_ADMIN_PANEL.asStack());
    }


    @Override
    public Component getDisplayName() {
        return Component.literal("CCN");
    }

    @Override
    public @NotNull ItemStack makeIcon() {
        return CcnItems.TEST_RODE.asStack();
    }
}
