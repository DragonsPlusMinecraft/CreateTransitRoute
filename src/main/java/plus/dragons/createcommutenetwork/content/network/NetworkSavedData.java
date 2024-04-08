package plus.dragons.createcommutenetwork.content.network;

import com.simibubi.create.foundation.utility.NBTHelper;
import it.unimi.dsi.fastutil.longs.Long2ObjectArrayMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;

public class NetworkSavedData extends SavedData {
    private Long2ObjectArrayMap<Route> allRoutes = new Long2ObjectArrayMap<>();
    private Long2ObjectArrayMap<Station> allStations = new Long2ObjectArrayMap<>();

    @Override
    public CompoundTag save(CompoundTag compoundTag) {
        compoundTag.put("routes", NBTHelper.writeCompoundList(allRoutes.values(), Route::serializeNBT));
        compoundTag.put("stations", NBTHelper.writeCompoundList(allStations.values(), Station::serializeNBT));
        return compoundTag;
    }

    private static NetworkSavedData load(CompoundTag compoundTag) {
        NetworkSavedData data = new NetworkSavedData();
        NBTHelper.iterateCompoundList(compoundTag.getList("routes", 10), (c) -> {
            Route route = new Route();
            route.deserializeNBT(c);
            data.allRoutes.put(route.id, route);
        });
        NBTHelper.iterateCompoundList(compoundTag.getList("stations", 10), (c) -> {
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

    public Long2ObjectArrayMap<Route> getRoutes() {
        return allRoutes;
    }

    public Long2ObjectArrayMap<Station> getStations() {
        return allStations;
    }
}
