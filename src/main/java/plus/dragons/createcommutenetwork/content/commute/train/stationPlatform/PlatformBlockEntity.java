package plus.dragons.createcommutenetwork.content.commute.train.stationPlatform;

import com.simibubi.create.Create;
import com.simibubi.create.content.contraptions.ITransformableBlockEntity;
import com.simibubi.create.content.contraptions.StructureTransform;
import com.simibubi.create.content.trains.graph.TrackGraphLocation;
import com.simibubi.create.content.trains.track.TrackTargetingBehaviour;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import plus.dragons.createcommutenetwork.CommuteNetwork;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class PlatformBlockEntity extends SmartBlockEntity implements ITransformableBlockEntity {

    public TrackTargetingBehaviour<Platform> edgePoint;

    public PlatformBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public Platform getPlatform() {
        return edgePoint.getEdgePoint();
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        behaviours.add(edgePoint = new TrackTargetingBehaviour<>(this, CommuteNetwork.PLATFORM));
    }

    public boolean updatePlatformInfo(@Nullable UUID stationId, String platformCode) {
        if (!this.updatePlatformState((platform) -> {
            platform.belongToStation = stationId;
            platform.platformCode = platformCode;
        })) {
            return false;
        } else {
            this.notifyUpdate();
            return true;
        }
    }

    private boolean updatePlatformState(Consumer<Platform> updateState) {
        Platform platform = this.getPlatform();
        TrackGraphLocation graphLocation = this.edgePoint.determineGraphLocation();
        if (platform != null && graphLocation != null) {
            updateState.accept(platform);
            Create.RAILWAYS.sync.pointAdded(graphLocation.graph, platform);
            Create.RAILWAYS.markTracksDirty();
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void write(CompoundTag tag, boolean clientPacket) { //TODO fix platform info not save
        super.write(tag, clientPacket);
    }

    @Override
    protected void read(CompoundTag tag, boolean clientPacket) {
        super.read(tag, clientPacket);
    }

    @Override
    public void transform(StructureTransform transform) {
        edgePoint.transform(transform);
    }
}
