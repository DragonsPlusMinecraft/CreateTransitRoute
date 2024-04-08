package plus.dragons.createcommutenetwork.content.network;

import it.unimi.dsi.fastutil.longs.Long2ObjectArrayMap;


public class Network {
    public Long2ObjectArrayMap<Route> allRoutes = new Long2ObjectArrayMap<>();
    public Long2ObjectArrayMap<Station> allStations = new Long2ObjectArrayMap<>();
}
