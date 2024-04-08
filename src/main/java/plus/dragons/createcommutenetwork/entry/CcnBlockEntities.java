package plus.dragons.createcommutenetwork.entry;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import plus.dragons.createcommutenetwork.content.commute.trains.commuteStation.CommutePlatformBlockEntity;
import plus.dragons.createcommutenetwork.content.commute.trains.commuteStation.CommutePlatformRenderer;

import static plus.dragons.createcommutenetwork.CommuteNetwork.REGISTRATE;

public class CcnBlockEntities {

    public static final BlockEntityEntry<CommutePlatformBlockEntity> COMMUTE_PLATFORM = REGISTRATE
            .blockEntity("commute_platform", CommutePlatformBlockEntity::new)
            .renderer(() -> CommutePlatformRenderer::new)
            .validBlocks(CcnBlocks.COMMUTE_PLATFORM)
            .register();

    public static void register() {
    }
}
