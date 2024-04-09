package plus.dragons.createcommutenetwork.content.commuteNetwork;

import com.simibubi.create.foundation.utility.Couple;
import com.simibubi.create.foundation.utility.NBTHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NetworkSavedData extends SavedData {

    private final Map<UUID,Route> allRoutes = new HashMap<>();
    private final Map<UUID,Station> allStations = new HashMap<>();

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.put("Routes", NBTHelper.writeCompoundList(allRoutes.values(), Route::serializeNBT));
        tag.put("Stations", NBTHelper.writeCompoundList(allStations.values(), Station::serializeNBT));
        return tag;
    }

    private static NetworkSavedData load(CompoundTag tag) {
        NetworkSavedData data = new NetworkSavedData();
        NBTHelper.iterateCompoundList(tag.getList("Routes", 10), (c) -> {
            Route route = new Route();
            route.deserializeNBT(c);
            data.allRoutes.put(route.id, route);
        });
        NBTHelper.iterateCompoundList(tag.getList("Stations", 10), (c) -> {
            Station station = new Station();
            station.deserializeNBT(c);
            data.allStations.put(station.id, station);
        });

        // TODO test only
        {
            if (data.allStations.isEmpty()) {
                var station = new Station();
                station.name = Couple.create("Test Station", "测试站点");
                data.allStations.put(UUID.randomUUID(), station);
            }
        }
        return data;
    }

    private NetworkSavedData() {
    }

    public static NetworkSavedData load(MinecraftServer server) {
        return server.overworld()
                .getDataStorage()
                .computeIfAbsent(NetworkSavedData::load, NetworkSavedData::new, "commute_network");
    }

    public Map<UUID,Route> getRoutes() {
        return allRoutes;
    }

    public Map<UUID,Station> getStations() {
        return allStations;
    }
}
