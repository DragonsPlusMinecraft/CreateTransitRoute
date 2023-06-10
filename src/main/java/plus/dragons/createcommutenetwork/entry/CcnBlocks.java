package plus.dragons.createcommutenetwork.entry;

import com.simibubi.create.AllCreativeModeTabs;
import com.simibubi.create.content.trains.track.TrackTargetingBlockItem;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MaterialColor;
import plus.dragons.createcommutenetwork.DragonTransit;
import plus.dragons.createcommutenetwork.content.commute.trains.commuteStation.CommuteStationBlock;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static plus.dragons.createcommutenetwork.DragonTransit.REGISTRATE;

public class CcnBlocks {

    static {
        REGISTRATE.creativeModeTab(() -> AllCreativeModeTabs.BASE_CREATIVE_TAB);
    }

    public static final BlockEntry<CommuteStationBlock> COMMUTE_STATION = REGISTRATE
            .block("commute_station", CommuteStationBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.PODZOL))
            .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
            .transform(TagGen.pickaxeOnly())
            .blockstate((c, p) -> p.simpleBlock(c.get(), AssetLookup.partialBaseModel(c, p)))
            .item(TrackTargetingBlockItem.ofType(DragonTransit.COMMUTE_STATION))
            .transform(customItemModel())
            .register();

    public static void register() {
    }

}
