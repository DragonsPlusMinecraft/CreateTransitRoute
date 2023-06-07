package plus.dragons.createcommutenetwork.entry;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import plus.dragons.createcommutenetwork.content.logistics.commute.train.commuteStation.PlatformBlockEntity;
import plus.dragons.createcommutenetwork.content.logistics.commute.train.commuteStation.PlatformRenderer;

import static plus.dragons.createcommutenetwork.DragonTransit.REGISTRATE;

public class CcnBlockEntities {

    public static final BlockEntityEntry<PlatformBlockEntity> TRANSIT_STATION_PLATFORM = REGISTRATE
            .blockEntity("platform", PlatformBlockEntity::new)
            .renderer(() -> PlatformRenderer::new)
            .validBlocks(CcnBlocks.TRANSIT_STATION_PLATFORM)
            .register();

    public static void register() {}
}
