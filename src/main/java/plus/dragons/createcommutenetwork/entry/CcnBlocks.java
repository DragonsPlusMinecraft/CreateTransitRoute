package plus.dragons.createcommutenetwork.entry;

import com.simibubi.create.content.trains.track.TrackTargetingBlockItem;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MaterialColor;
import plus.dragons.createcommutenetwork.CommuteNetwork;
import plus.dragons.createcommutenetwork.content.commute.train.stationPlatform.PlatformBlock;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static plus.dragons.createcommutenetwork.CommuteNetwork.REGISTRATE;

public class CcnBlocks {

    public static final BlockEntry<PlatformBlock> PLATFORM = REGISTRATE
            .block("platform", PlatformBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.PODZOL))
            .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
            .transform(TagGen.pickaxeOnly())
            .blockstate((c, p) -> p.simpleBlock(c.get(), AssetLookup.partialBaseModel(c, p)))
            .item(TrackTargetingBlockItem.ofType(CommuteNetwork.PLATFORM))
            .transform(customItemModel())
            .register();

    public static void register() {
    }

}
