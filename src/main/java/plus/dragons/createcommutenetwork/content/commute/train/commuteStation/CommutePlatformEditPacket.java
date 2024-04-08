package plus.dragons.createcommutenetwork.content.commute.train.commuteStation;

import com.simibubi.create.foundation.networking.BlockEntityConfigurationPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

import java.util.UUID;

public class CommutePlatformEditPacket {

    public static class PlatformUnbind extends BlockEntityConfigurationPacket<CommutePlatformBlockEntity> {

        UUID unbindStation;

        public static PlatformUnbind of(BlockPos pos, UUID unbindStation) {
            var ret = new PlatformUnbind(pos);
            ret.unbindStation = unbindStation;
            return ret;

        }

        public PlatformUnbind(FriendlyByteBuf buffer) {
            super(buffer);
        }

        public PlatformUnbind(BlockPos pos) {
            super(pos);
        }

        @Override
        protected void writeSettings(FriendlyByteBuf buffer) {
            buffer.writeUUID(unbindStation);
        }

        @Override
        protected void readSettings(FriendlyByteBuf buffer) {
            unbindStation = buffer.readUUID();
        }

        @Override
        protected void applySettings(CommutePlatformBlockEntity te) {
            // TODO
            /*te.getPlatform().station = null;
            PlatformEdgePoint platform = te.getPlatform();
            GraphLocation graphLocation = te.edgePoint.determineGraphLocation();
            if (platform != null && graphLocation != null) {
                Create.RAILWAYS.sync.pointAdded(graphLocation.graph, platform);
                Create.RAILWAYS.markTracksDirty();
            }

            DragonTransit.TRANSIT_MANAGER.removePlatformFromStation(unbindStation,te.getPlatform().id);*/
        }
    }
}
