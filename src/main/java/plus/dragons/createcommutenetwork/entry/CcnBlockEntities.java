package plus.dragons.createcommutenetwork.entry;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import plus.dragons.createcommutenetwork.content.commute.train.stationPlatform.PlatformBlockEntity;
import plus.dragons.createcommutenetwork.content.commute.train.stationPlatform.PlatformRenderer;

import static plus.dragons.createcommutenetwork.CommuteNetwork.REGISTRATE;

public class CcnBlockEntities {

    public static final BlockEntityEntry<PlatformBlockEntity> PLATFORM = REGISTRATE
            .blockEntity("platform", PlatformBlockEntity::new)
            .renderer(() -> PlatformRenderer::new)
            .validBlocks(CcnBlocks.PLATFORM)
            .register();

    public static void register() {
    }
}
