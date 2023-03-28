package plus.dragons.createdragontransit.content.logistics.transit.management.edgepoint.station;

import com.simibubi.create.Create;
import com.simibubi.create.content.logistics.trains.GraphLocation;
import com.simibubi.create.foundation.networking.TileEntityConfigurationPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import plus.dragons.createdragontransit.DragonTransit;
import plus.dragons.createdragontransit.content.logistics.transit.TransitStationPlatform;

import java.util.UUID;

public class PlatformEditPacket {

    public static class PlatformUnbind extends TileEntityConfigurationPacket<TransitStationPlatformBlockEntity>{

        UUID unbindStation;

        public static PlatformUnbind of(BlockPos pos, UUID unbindStation){
            var ret = new PlatformUnbind(pos);
            ret.unbindStation = unbindStation;
            return ret;

        }
        public PlatformUnbind(FriendlyByteBuf buffer) {super(buffer);}

        public PlatformUnbind(BlockPos pos) {super(pos);}

        @Override
        protected void writeSettings(FriendlyByteBuf buffer) {
            buffer.writeUUID(unbindStation);
        }

        @Override
        protected void readSettings(FriendlyByteBuf buffer) {
            unbindStation = buffer.readUUID();
        }

        @Override
        protected void applySettings(TransitStationPlatformBlockEntity te) {
            te.getPlatform().station = null;
            TransitStationPlatform platform = te.getPlatform();
            GraphLocation graphLocation = te.edgePoint.determineGraphLocation();
            if (platform != null && graphLocation != null) {
                Create.RAILWAYS.sync.pointAdded(graphLocation.graph, platform);
                Create.RAILWAYS.markTracksDirty();
            }

            DragonTransit.ROUTES.removePlatformFromStation(unbindStation,te.getPlatform().id);
        }
    }
}
