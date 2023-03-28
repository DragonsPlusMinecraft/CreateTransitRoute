package plus.dragons.createdragontransit.content.logistics.transit;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.simibubi.create.foundation.networking.SimplePacketBase;
import com.simibubi.create.foundation.utility.NBTHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import plus.dragons.createdragontransit.DragonTransitClient;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class DTNetworkSyncPacket extends SimplePacketBase {

    CompoundTag content;

    protected DTNetworkSyncPacket(FriendlyByteBuf buffer){
        content = buffer.readNbt();
    }

    protected DTNetworkSyncPacket(CompoundTag content) {
        this.content = content;
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeNbt(content);
    }

    public static class Update extends DTNetworkSyncPacket {

        public Update(FriendlyByteBuf buffer){
            super(buffer);
        }

        private Update(CompoundTag content) {
            super(content);
        }

        @SuppressWarnings("ConstantConditions")
        @Override
        public void handle(Supplier<NetworkEvent.Context> context) {
            context.get()
                    .enqueueWork(() -> {
                        var manager = DragonTransitClient.TRANSIT_MANAGER;
                        CompoundTag line = content.contains("Line")? content.getCompound("Line"): null;
                        CompoundTag seg = content.contains("Segment")? content.getCompound("Segment"): null;
                        CompoundTag station = content.contains("Station")? content.getCompound("Station"): null;
                        CompoundTag platform = content.contains("Platform")? content.getCompound("Platform"): null;
                        if(line!=null){
                            if(line.getBoolean("Update")){
                                var l = new DTLine(line.getCompound("Content"));
                                manager.network.lines.put(l.id,l);
                            }  else {
                                manager.network.lines.remove(line.getUUID("ID"));
                            }
                        }
                        if(seg!=null){
                            if(seg.getBoolean("Update")){
                                var s = new DTLineSegment(seg.getCompound("Content"));
                                manager.network.segments.put(s.id,s);
                            }  else {
                                manager.network.lines.remove(seg.getUUID("ID"));
                            }
                        }
                        if(station!=null){
                            if(station.getBoolean("Update")){
                                var s = new DTStation(station.getCompound("Content"));
                                manager.network.stations.put(s.id,s);
                            }  else {
                                manager.network.lines.remove(station.getUUID("ID"));
                            }
                        }
                        if(platform!=null){
                            if(platform.getBoolean("Update")){
                                var p = new DTStationPlatform(platform.getCompound("Content"));
                                manager.network.platform.put(p.id,p);
                            }  else {
                                manager.network.lines.remove(platform.getUUID("ID"));
                            }
                        }
                    });
            context.get()
                    .setPacketHandled(true);
        }

        public static class Builder{
            final CompoundTag content;

            public Builder() {
                this.content = new CompoundTag();
            }

            public Builder updateLine(DTLine line){
                var tag = new CompoundTag();
                tag.put("Content",line.write());
                tag.putBoolean("Update",true);
                content.put("Line",tag);
                return this;
            }

            public Builder removeLine(DTLine line){
                var tag = new CompoundTag();
                tag.putUUID("ID",line.id);
                tag.putBoolean("Update",false);
                content.put("Line",tag);
                return this;
            }

            public Builder updateSegment(DTLineSegment segment){
                var tag = new CompoundTag();
                tag.put("Content",segment.write());
                tag.putBoolean("Update",true);
                content.put("Segment",tag);
                return this;
            }

            public Builder removeSegment(DTLineSegment segment){
                var tag = new CompoundTag();
                tag.putUUID("ID",segment.id);
                tag.putBoolean("Update",false);
                content.put("Segment",tag);
                return this;
            }

            public Builder updateStation(DTStation station){
                var tag = new CompoundTag();
                tag.put("Content",station.write());
                tag.putBoolean("Update",true);
                content.put("Station",tag);
                return this;
            }

            public Builder removeStation(DTStation station){
                var tag = new CompoundTag();
                tag.putUUID("ID",station.id);
                tag.putBoolean("Update",false);
                content.put("Station",tag);
                return this;
            }

            public Builder updatePlatform(DTStationPlatform platform){
                var tag = new CompoundTag();
                tag.put("Content",platform.write());
                tag.putBoolean("Update",true);
                content.put("Platform",tag);
                return this;
            }

            public Builder removePlatform(DTStationPlatform platform){
                var tag = new CompoundTag();
                tag.putUUID("ID",platform.id);
                tag.putBoolean("Update",false);
                content.put("Platform",tag);
                return this;
            }

            public Update build(){
                return new Update(content);
            }
        }
    }

    public static class Initialize extends DTNetworkSyncPacket {

        public Initialize(FriendlyByteBuf buffer){
            super(buffer);
        }

        private Initialize(CompoundTag content) {
            super(content);
        }

        public static List<Initialize> of(DTNetwork network){
            List<Initialize> ret = new ArrayList<>();
            if(!network.lines.isEmpty())
                ret.addAll(Lists.partition(network.lines.values().stream().toList(),10).stream().map(collections -> {
                    var tag = new CompoundTag();
                    tag.putString("Type","Line");
                    tag.put("Content",NBTHelper.writeCompoundList(collections,DTLine::write));
                    return new Initialize(tag);
                }).toList());
            if(!network.segments.isEmpty())
                ret.addAll(Lists.partition(network.segments.values().stream().toList(),10).stream().map(collections -> {
                    var tag = new CompoundTag();
                    tag.putString("Type","Segments");
                    tag.put("Content",NBTHelper.writeCompoundList(collections,DTLineSegment::write));
                    return new Initialize(tag);
                }).toList());
            if(!network.stations.isEmpty())
                ret.addAll(Lists.partition(network.stations.values().stream().toList(),10).stream().map(collections -> {
                    var tag = new CompoundTag();
                    tag.putString("Type","Stations");
                    tag.put("Content",NBTHelper.writeCompoundList(collections,DTStation::write));
                    return new Initialize(tag);
                }).toList());
            if(!network.platform.isEmpty())
                ret.addAll(Lists.partition(network.platform.values().stream().toList(),10).stream().map(collections -> {
                    var tag = new CompoundTag();
                    tag.putString("Type","Platform");
                    tag.put("Content",NBTHelper.writeCompoundList(collections,DTStationPlatform::write));
                    return new Initialize(tag);
                }).toList());
            return ret;
        }

        @SuppressWarnings("ConstantConditions")
        @Override
        public void handle(Supplier<NetworkEvent.Context> context) {
            context.get()
                    .enqueueWork(() -> {
                        var manager = DragonTransitClient.TRANSIT_MANAGER;
                        var type = content.getString("Type");
                        switch (type){
                            case "Line"->
                                    NBTHelper.readCompoundList((ListTag)content.get("Content"), DTLine::new).forEach(line -> manager.network.lines.put(line.id,line));
                            case "Segments"->
                                    NBTHelper.readCompoundList((ListTag)content.get("Content"), DTLineSegment::new).forEach(segment -> manager.network.segments.put(segment.id,segment));
                            case "Stations"->
                                    NBTHelper.readCompoundList((ListTag)content.get("Content"), DTStation::new).forEach(station -> manager.network.stations.put(station.id,station));
                            case "Platform"->
                                    NBTHelper.readCompoundList((ListTag)content.get("Content"), DTStationPlatform::new).forEach(platform -> manager.network.platform.put(platform.id,platform));
                            default -> throw new RuntimeException("DTNetworkSyncPacket.Initialize has bad packet! Type of " + type + ".");
                        }
                    });
            context.get()
                    .setPacketHandled(true);
        }
    }
}
