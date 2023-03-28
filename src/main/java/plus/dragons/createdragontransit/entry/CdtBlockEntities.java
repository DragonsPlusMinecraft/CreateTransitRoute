package plus.dragons.createdragontransit.entry;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import plus.dragons.createdragontransit.content.logistics.transit.edgepoint.platform.PlatformBlockEntity;
import plus.dragons.createdragontransit.content.logistics.transit.edgepoint.platform.PlatformRenderer;

import static plus.dragons.createdragontransit.DragonTransit.REGISTRATE;

public class CdtBlockEntities {

    public static final BlockEntityEntry<PlatformBlockEntity> TRANSIT_STATION_PLATFORM = REGISTRATE
            .tileEntity("platform", PlatformBlockEntity::new)
            .renderer(() -> PlatformRenderer::new)
            .validBlocks(CdtBlocks.TRANSIT_STATION_PLATFORM)
            .register();

    public static void register() {}
}
