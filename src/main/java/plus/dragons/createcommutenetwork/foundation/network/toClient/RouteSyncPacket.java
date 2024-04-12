package plus.dragons.createcommutenetwork.foundation.network.toClient;

import com.simibubi.create.foundation.networking.SimplePacketBase;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import plus.dragons.createcommutenetwork.CommuteNetworkClient;
import plus.dragons.createcommutenetwork.content.commuteNetwork.Route;

public class RouteSyncPacket extends SimplePacketBase {
    public Route route;

    public RouteSyncPacket(Route route) {
        this.route = route;
    }

    public RouteSyncPacket(FriendlyByteBuf friendlyByteBuf) {
        this.route = new Route();
        route.deserializeNBT(friendlyByteBuf.readNbt());
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeNbt(route.serializeNBT());
    }

    @Override
    public boolean handle(NetworkEvent.Context context) {
        CommuteNetworkClient.COMMUTE_NETWORK_MANAGER.allRoutes.put(route.id,route);
        CommuteNetworkClient.COMMUTE_NETWORK_MANAGER.markClientCacheDirty();
        return true;
    }
}
