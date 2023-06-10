package plus.dragons.createcommutenetwork.content.commute.trains.commuteStation;

import com.simibubi.create.content.contraptions.ITransformableBlockEntity;
import com.simibubi.create.content.contraptions.StructureTransform;
import com.simibubi.create.content.trains.track.TrackTargetingBehaviour;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import plus.dragons.createcommutenetwork.DragonTransit;

import java.util.List;

public class CommuteStationBlockEntity extends SmartBlockEntity implements ITransformableBlockEntity {

    public TrackTargetingBehaviour<CommuteStationEdgePoint> edgePoint;

    public CommuteStationBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public CommuteStationEdgePoint getPlatform() {
        return edgePoint.getEdgePoint();
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        behaviours.add(edgePoint = new TrackTargetingBehaviour<>(this, DragonTransit.COMMUTE_STATION));
    }

    @Override
    protected void write(CompoundTag tag, boolean clientPacket) {
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
