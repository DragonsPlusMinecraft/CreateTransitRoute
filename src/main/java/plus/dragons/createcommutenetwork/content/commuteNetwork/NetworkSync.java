package plus.dragons.createcommutenetwork.content.commuteNetwork;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;
import plus.dragons.createcommutenetwork.content.commuteNetwork.packet.MultipleStationSyncPacket;
import plus.dragons.createcommutenetwork.content.commuteNetwork.packet.RouteSyncPacket;
import plus.dragons.createcommutenetwork.entry.CcnPackets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class NetworkSync {
    public void sendAllStationTo(Collection<Station> station, ServerPlayer player){
        int i = 5;
        List<Station> part = new ArrayList<>();
        station.forEach(s->{
            if(part.size()==5){
                CcnPackets.getChannel().send(PacketDistributor.PLAYER.with(() -> player), new MultipleStationSyncPacket(part));
                part.clear();
            } else part.add(s);
        });
        if(!part.isEmpty())
            CcnPackets.getChannel().send(PacketDistributor.PLAYER.with(() -> player), new MultipleStationSyncPacket(part));
    }

    public void sendAllRouteTo(Collection<Route> routes, ServerPlayer player){
        routes.forEach(r->{
            CcnPackets.getChannel().send(PacketDistributor.PLAYER.with(() -> player), new RouteSyncPacket(r));
        });
    }

}
