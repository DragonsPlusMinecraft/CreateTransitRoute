package plus.dragons.createdragontransit.content.logistics.transit;

import com.simibubi.create.foundation.networking.SimplePacketBase;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.Nullable;
import plus.dragons.createdragontransit.DragonTransitClient;

import java.util.UUID;
import java.util.function.Supplier;

public abstract class TransitNetworkSyncPacket extends SimplePacketBase {
    @Nullable
    TransitStation modifiedStation;
    @Nullable
    TransitLine modifiedLine;
    UUID id;

    protected TransitNetworkSyncPacket(FriendlyByteBuf buffer){
        if(buffer.readBoolean()){
            modifiedStation = TransitStation.read(buffer.readNbt());
        }
        if(buffer.readBoolean()){
            modifiedLine = TransitLine.read(buffer.readNbt());
        }
        id = buffer.readUUID();
    }

    protected TransitNetworkSyncPacket(TransitStation modifiedStation,UUID id) {
        this.modifiedStation = modifiedStation;
        this.modifiedLine = null;
        this.id = id;
    }

    protected TransitNetworkSyncPacket(TransitLine modifiedLine,UUID id) {
        this.modifiedStation = null;
        this.modifiedLine = modifiedLine;
        this.id = id;
    }

    protected TransitNetworkSyncPacket(UUID id) {
        this.modifiedStation = null;
        this.modifiedLine = null;
        this.id = id;
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeBoolean(modifiedStation!=null);
        if(modifiedStation!=null) {
            buffer.writeNbt(modifiedStation.write());
        }
        buffer.writeBoolean(modifiedLine!=null);
        if(modifiedLine!=null) {
            buffer.writeNbt(modifiedLine.write());
        }
        buffer.writeUUID(id);
    }

    public static class Update extends TransitNetworkSyncPacket{
        private final Operation op;
        public enum Operation{
            UPDATE,
            REMOVE_STATION,
            REMOVE_LINE
        }

        public Update(FriendlyByteBuf buffer){
            super(buffer);
            op = Operation.values()[buffer.readInt()];
        }

        private Update(TransitStation modifiedStation,UUID id) {
            super(modifiedStation,id);
            this.op = Operation.UPDATE;
        }

        private Update(TransitLine modifiedLine, UUID id) {
            super(modifiedLine,id);
            this.op = Operation.UPDATE;
        }

        private Update(UUID id, Operation removeOperation) {
            super(id);
            this.op = removeOperation;
        }

        public static Update updateStation(TransitStation station){
            return new Update(station,station.getId());
        }

        public static Update updateLine(TransitLine line){
            return new Update(line,line.getId());
        }

        public static Update removeStation(UUID id){
            return new Update(id,Operation.REMOVE_STATION);
        }

        public static Update removeLine(UUID id){
            return new Update(id,Operation.REMOVE_LINE);
        }

        @Override
        public void write(FriendlyByteBuf buffer) {
            super.write(buffer);
            buffer.writeInt(op.ordinal());
        }

        @Override
        public void handle(Supplier<NetworkEvent.Context> context) {
            context.get()
                    .enqueueWork(() -> {
                        var manager = DragonTransitClient.ROUTES;
                        if(op==Operation.UPDATE){
                            if(modifiedStation!=null){
                                manager.network.stations.put(modifiedStation.getId(),modifiedStation);
                            } else{
                                manager.network.lines.put(modifiedLine.getId(),modifiedLine);
                            }
                        } else if(op==Operation.REMOVE_STATION) {
                            manager.network.stations.remove(id);
                        } else {
                            manager.network.lines.remove(id);
                        }
                    });
            context.get()
                    .setPacketHandled(true);
        }
    }

    public static class Initialize extends TransitNetworkSyncPacket{
        private final boolean isStation;

        public Initialize(FriendlyByteBuf buffer){
            super(buffer);
            this.isStation = buffer.readBoolean();
        }

        private Initialize(TransitStation modifiedStation,UUID id) {
            super(modifiedStation,id);
            this.isStation = true;
        }

        private Initialize(TransitLine modifiedLine, UUID id) {
            super(modifiedLine,id);
            this.isStation = false;
        }

        public static Update station(TransitStation station){
            return new Update(station,station.getId());
        }

        public static Update line(TransitLine line){
            return new Update(line,line.getId());
        }

        @Override
        public void write(FriendlyByteBuf buffer) {
            super.write(buffer);
            buffer.writeBoolean(isStation);
        }

        @Override
        public void handle(Supplier<NetworkEvent.Context> context) {
            context.get()
                    .enqueueWork(() -> {
                        var manager = DragonTransitClient.ROUTES;
                        if(isStation){
                            assert modifiedStation != null;
                            manager.network.stations.put(modifiedStation.getId(),modifiedStation);
                        }  else {
                            assert modifiedLine != null;
                            manager.network.lines.put(modifiedLine.getId(),modifiedLine);
                        }
                    });
            context.get()
                    .setPacketHandled(true);
        }
    }
}
