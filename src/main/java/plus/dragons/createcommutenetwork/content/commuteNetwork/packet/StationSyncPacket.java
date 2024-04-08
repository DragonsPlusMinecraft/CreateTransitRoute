package plus.dragons.createcommutenetwork.content.commuteNetwork.packet;

import com.simibubi.create.foundation.networking.SimplePacketBase;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import plus.dragons.createcommutenetwork.CommuteNetworkClient;
import plus.dragons.createcommutenetwork.content.commuteNetwork.Station;

public class StationSyncPacket extends SimplePacketBase {
    public Station station;

    public StationSyncPacket(Station station) {
        this.station = station;
    }

    public StationSyncPacket(FriendlyByteBuf friendlyByteBuf) {
        this.station = new Station();
        station.deserializeNBT(friendlyByteBuf.readNbt());
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeNbt(station.serializeNBT());
    }

    @Override
    public boolean handle(NetworkEvent.Context context) {
        CommuteNetworkClient.COMMUTE_NETWORK_MANAGER.allStations.put(station.id,station);
        return true;
    }
}
