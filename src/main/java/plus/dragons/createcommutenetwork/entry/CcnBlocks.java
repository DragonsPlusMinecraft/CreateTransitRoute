package plus.dragons.createcommutenetwork.entry;

import com.simibubi.create.content.trains.track.TrackTargetingBlockItem;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MaterialColor;
import plus.dragons.createcommutenetwork.CommuteNetwork;
import plus.dragons.createcommutenetwork.content.commute.train.adminPanel.route.CreativeRouteAdminPanelBlock;
import plus.dragons.createcommutenetwork.content.commute.train.adminPanel.route.RouteAdminPanelBlock;
import plus.dragons.createcommutenetwork.content.commute.train.adminPanel.station.CreativeStationAdminPanelBlock;
import plus.dragons.createcommutenetwork.content.commute.train.adminPanel.station.StationAdminPanelBlock;
import plus.dragons.createcommutenetwork.content.commute.train.stationPlatform.PlatformBlock;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static plus.dragons.createcommutenetwork.CommuteNetwork.REGISTRATE;

public class CcnBlocks {

    public static final BlockEntry<PlatformBlock> PLATFORM = REGISTRATE
            .block("commute_platform", PlatformBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.PODZOL))
            .properties(p -> p.sound(SoundType.METAL))
            .transform(TagGen.pickaxeOnly())
            .blockstate((c, p) -> p.simpleBlock(c.get(), AssetLookup.partialBaseModel(c, p)))
            .item(TrackTargetingBlockItem.ofType(CommuteNetwork.PLATFORM))
            .transform(customItemModel())
            .register();

    public static final BlockEntry<RouteAdminPanelBlock> ROUTE_ADMIN_PANEL = REGISTRATE
            .block("commute_route_administrative_panel", RouteAdminPanelBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.PODZOL))
            .properties(p -> p.sound(SoundType.METAL))
            .transform(TagGen.pickaxeOnly())
            .blockstate((c, p) -> p.simpleBlock(c.get(), AssetLookup.partialBaseModel(c, p)))
            .register();

    public static final BlockEntry<CreativeRouteAdminPanelBlock> CREATIVE_ROUTE_ADMIN_PANEL = REGISTRATE
            .block("creative_commute_route_administrative_panel", CreativeRouteAdminPanelBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.PODZOL))
            .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
            .transform(TagGen.pickaxeOnly())
            .blockstate((c, p) -> p.simpleBlock(c.get(), AssetLookup.partialBaseModel(c, p)))
            .register();

    public static final BlockEntry<StationAdminPanelBlock> STATION_ADMIN_PANEL = REGISTRATE
            .block("commute_station_administrative_panel", StationAdminPanelBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.PODZOL))
            .properties(p -> p.sound(SoundType.METAL))
            .transform(TagGen.pickaxeOnly())
            .blockstate((c, p) -> p.simpleBlock(c.get(), AssetLookup.partialBaseModel(c, p)))
            .register();

    public static final BlockEntry<CreativeStationAdminPanelBlock> CREATIVE_STATION_ADMIN_PANEL = REGISTRATE
            .block("creative_commute_station_administrative_panel", CreativeStationAdminPanelBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.PODZOL))
            .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
            .transform(TagGen.pickaxeOnly())
            .blockstate((c, p) -> p.simpleBlock(c.get(), AssetLookup.partialBaseModel(c, p)))
            .register();

    public static void register() {
    }

}
