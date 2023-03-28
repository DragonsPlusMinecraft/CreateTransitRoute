package plus.dragons.createdragontransit.content.logistics.transit;

import com.simibubi.create.foundation.utility.NBTHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import plus.dragons.createdragontransit.DragonTransit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DTNetwork {

    public Map<UUID, DTLine> lines = new HashMap<>();
    public Map<UUID, DTLineSegment> segments = new HashMap<>();
    public Map<UUID, DTStation> stations = new HashMap<>();
    public Map<UUID, DTStationPlatform> platform = new HashMap<>();


    public CompoundTag save(CompoundTag compoundTag) {
        DTManager manager = DragonTransit.TRANSIT_MANAGER;
        compoundTag.put("Lines", NBTHelper.writeCompoundList(manager.network.lines.values(), DTLine::write));
        compoundTag.put("Segments", NBTHelper.writeCompoundList(manager.network.segments.values(), DTLineSegment::write));
        compoundTag.put("Stations", NBTHelper.writeCompoundList(manager.network.stations.values(), DTStation::write));
        compoundTag.put("Platforms", NBTHelper.writeCompoundList(manager.network.platform.values(), DTStationPlatform::write));
        return compoundTag;
    }

    public void load(CompoundTag compoundTag) {
        lines.clear();
        segments.clear();
        stations.clear();
        platform.clear();
        NBTHelper.iterateCompoundList(compoundTag.getList("Lines", Tag.TAG_COMPOUND), c -> {
            var result = new DTLine(c);
            lines.put(result.id, result);
        });
        NBTHelper.iterateCompoundList(compoundTag.getList("Segments", Tag.TAG_COMPOUND), c -> {
            var result = new DTLineSegment(c);
            segments.put(result.id, result);
        });
        NBTHelper.iterateCompoundList(compoundTag.getList("Stations", Tag.TAG_COMPOUND), c -> {
            var result = new DTStation(c);
            stations.put(result.id, result);
        });
        NBTHelper.iterateCompoundList(compoundTag.getList("Platforms", Tag.TAG_COMPOUND), c -> {
            var result = new DTStationPlatform(c);
            platform.put(result.id, result);
        });
    }
}
