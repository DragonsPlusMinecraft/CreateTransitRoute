package plus.dragons.createcommutenetwork.entry;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import plus.dragons.createcommutenetwork.content.commute.trains.commuteStation.CommuteStationBlockEntity;
import plus.dragons.createcommutenetwork.content.commute.trains.commuteStation.CommuteStationRenderer;

import static plus.dragons.createcommutenetwork.CommuteNetwork.REGISTRATE;

public class CcnBlockEntities {

    public static final BlockEntityEntry<CommuteStationBlockEntity> COMMUTE_STATION = REGISTRATE
            .blockEntity("commute_station", CommuteStationBlockEntity::new)
            .renderer(() -> CommuteStationRenderer::new)
            .validBlocks(CcnBlocks.COMMUTE_STATION)
            .register();

    public static void register() {
    }
}
