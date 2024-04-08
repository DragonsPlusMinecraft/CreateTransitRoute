package plus.dragons.createcommutenetwork.content.commuteNetwork.packet;

import com.simibubi.create.foundation.networking.SimplePacketBase;
import com.simibubi.create.foundation.utility.NBTHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import plus.dragons.createcommutenetwork.CommuteNetworkClient;
import plus.dragons.createcommutenetwork.content.commuteNetwork.Station;

import java.util.ArrayList;
import java.util.List;

public class MultipleStationSyncPacket extends SimplePacketBase {
    public List<Station> stations;

    public MultipleStationSyncPacket(List<Station> stations) {
        this.stations = stations;
    }

    public MultipleStationSyncPacket(FriendlyByteBuf friendlyByteBuf) {
        this.stations = new ArrayList<>();
        NBTHelper.iterateCompoundList(friendlyByteBuf.readNbt().getList("V", 10), (c) -> {
            Station s = new Station();
            s.deserializeNBT(c);
            stations.add(s);
        });
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        CompoundTag tag = new CompoundTag();
        tag.put("V",NBTHelper.writeCompoundList(stations, Station::serializeNBT));
        friendlyByteBuf.writeNbt(tag);
    }

    @Override
    public boolean handle(NetworkEvent.Context context) {
        stations.forEach(s-> CommuteNetworkClient.COMMUTE_NETWORK_MANAGER.allStations.put(s.id,s));
        return true;
    }
}
