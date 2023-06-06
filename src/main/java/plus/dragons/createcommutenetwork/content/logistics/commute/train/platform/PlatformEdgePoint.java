package plus.dragons.createcommutenetwork.content.logistics.commute.train.platform;

import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.graph.DimensionPalette;
import com.simibubi.create.content.trains.signal.SingleBlockEntityEdgePoint;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;

import javax.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.util.UUID;

public class PlatformEdgePoint extends SingleBlockEntityEdgePoint {

    public WeakReference<Train> nearestTrain;
    @Nullable
    public UUID station;


    public PlatformEdgePoint() {
        station = null;
        nearestTrain = new WeakReference<>(null);
    }

    @Override
    public void blockEntityAdded(BlockEntity tile, boolean front) {
        super.blockEntityAdded(tile, front);
    }

    @Override
    public void read(CompoundTag nbt, boolean migration, DimensionPalette dimensions) {
        super.read(nbt, migration, dimensions);
        station = nbt.contains("StationID")? nbt.getUUID("StationID"): null;
        nearestTrain = new WeakReference<>(null);
    }



    @Override
    public void read(FriendlyByteBuf buffer, DimensionPalette dimensions) {
        super.read(buffer, dimensions);
        if(buffer.readBoolean()){
            station = buffer.readUUID();
        }
        if (buffer.readBoolean())
            blockEntityPos = buffer.readBlockPos();
    }

    @Override
    public void write(CompoundTag nbt, DimensionPalette dimensions) {
        super.write(nbt, dimensions);;
        if(station !=null){
            nbt.putUUID("StationID", station);
        }
    }

    @Override
    public void write(FriendlyByteBuf buffer, DimensionPalette dimensions) {
        super.write(buffer, dimensions);
        buffer.writeBoolean(station != null);
        if(station != null)
            buffer.writeUUID(station);
        buffer.writeBoolean(blockEntityPos != null);
        if (blockEntityPos != null)
            buffer.writeBlockPos(blockEntityPos);

    }

    @Override
    public void blockEntityRemoved(BlockPos tilePos, boolean front) {
        super.blockEntityRemoved(tilePos,front);
        // TODO

    }

}
