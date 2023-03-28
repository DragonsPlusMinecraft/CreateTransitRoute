package plus.dragons.createdragontransit.content.logistics.transit.edgepoint.platform;

import com.simibubi.create.content.contraptions.components.structureMovement.ITransformableTE;
import com.simibubi.create.content.contraptions.components.structureMovement.StructureTransform;
import com.simibubi.create.content.logistics.trains.management.edgePoint.TrackTargetingBehaviour;
import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import plus.dragons.createdragontransit.DragonTransit;

import java.util.List;

public class PlatformBlockEntity extends SmartTileEntity implements ITransformableTE {

    public TrackTargetingBehaviour<PlatformEdgePoint> edgePoint;

    public PlatformBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public PlatformEdgePoint getPlatform() {
        return edgePoint.getEdgePoint();
    }

    @Override
    public void addBehaviours(List<TileEntityBehaviour> behaviours) {
        behaviours.add(edgePoint = new TrackTargetingBehaviour<>(this, DragonTransit.PLATFORM));
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
