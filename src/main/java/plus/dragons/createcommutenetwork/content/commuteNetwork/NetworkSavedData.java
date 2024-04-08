package plus.dragons.createcommutenetwork.content.commuteNetwork;

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
        tag.put("routes", NBTHelper.writeCompoundList(allRoutes.values(), Route::serializeNBT));
        tag.put("stations", NBTHelper.writeCompoundList(allStations.values(), Station::serializeNBT));
        return tag;
    }

    private static NetworkSavedData load(CompoundTag tag) {
        NetworkSavedData data = new NetworkSavedData();
        NBTHelper.iterateCompoundList(tag.getList("routes", 10), (c) -> {
            Route route = new Route();
            route.deserializeNBT(c);
            data.allRoutes.put(route.id, route);
        });
        NBTHelper.iterateCompoundList(tag.getList("stations", 10), (c) -> {
            Station station = new Station();
            station.deserializeNBT(c);
            data.allStations.put(station.id, station);
        });
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
