package plus.dragons.createcommutenetwork.content.logistics.network;

import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.SetMultimap;

import java.util.HashMap;
import java.util.UUID;


public class CommuteNetwork {
    public HashMap<UUID, Route> allRoute = new HashMap<>();
    public HashMap<UUID, RouteSegment> allRouteSegments = new HashMap<>();
    public SetMultimap<UUID, UUID> routeToSegmentMultiMap = MultimapBuilder.hashKeys().hashSetValues().build();
}
