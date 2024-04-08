package plus.dragons.createcommutenetwork.content.commute.train.commuteStation;

import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.graph.DimensionPalette;
import com.simibubi.create.content.trains.graph.TrackNode;
import com.simibubi.create.content.trains.signal.SingleBlockEntityEdgePoint;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;

import javax.annotation.Nullable;
import java.lang.ref.WeakReference;

public class CommutePlatformEdgePoint extends SingleBlockEntityEdgePoint {

    public String name;
    public String code;
    public WeakReference<Train> nearestTrain;

    public CommutePlatformEdgePoint() {
        name = "Commute Station";
        code = generateUnrepeatedCode();
        nearestTrain = new WeakReference<Train>(null);
    }

    private static String generateUnrepeatedCode() {
        // TODO
        return "";
    }

    @Override
    public void blockEntityAdded(BlockEntity blockEntity, boolean front) {
        super.blockEntityAdded(blockEntity, front);
    }

    @Override
    public void read(CompoundTag nbt, boolean migration, DimensionPalette dimensions) {
        super.read(nbt, migration, dimensions);
        name = nbt.getString("Name");
        code = nbt.getString("Code");
        nearestTrain = new WeakReference<Train>(null);
    }

    @Override
    public void read(FriendlyByteBuf buffer, DimensionPalette dimensions) {
        super.read(buffer, dimensions);
        name = buffer.readUtf();
        code = buffer.readUtf();
        if (buffer.readBoolean())
            blockEntityPos = buffer.readBlockPos();
    }

    @Override
    public void write(CompoundTag nbt, DimensionPalette dimensions) {
        super.write(nbt, dimensions);
        nbt.putString("Name", name);
        nbt.putString("Code", code);
    }

    @Override
    public void write(FriendlyByteBuf buffer, DimensionPalette dimensions) {
        super.write(buffer, dimensions);
        buffer.writeUtf(name);
        buffer.writeUtf(code);
        buffer.writeBoolean(blockEntityPos != null);
        if (blockEntityPos != null)
            buffer.writeBlockPos(blockEntityPos);
    }

    public boolean canApproachFrom(TrackNode side) {
        return isPrimary(side);
    }

    @Override
    public boolean canNavigateVia(TrackNode side) {
        return super.canNavigateVia(side);
    }

    public void reserveFor(Train train) {
        Train nearestTrain = getNearestTrain();
        if (nearestTrain == null
                || nearestTrain.navigation.distanceToDestination > train.navigation.distanceToDestination)
            this.nearestTrain = new WeakReference<>(train);
    }

    public void cancelReservation(Train train) {
        if (nearestTrain.get() == train)
            nearestTrain = new WeakReference<>(null);
    }

    public void trainDeparted(Train train) {
        cancelReservation(train);
    }

    @Nullable
    public Train getPresentTrain() {
        Train nearestTrain = getNearestTrain();
        // FIXME
        /*if (nearestTrain == null || nearestTrain.getCurrentStation() != this)
            return null;*/
        return nearestTrain;
    }

    @Nullable
    public Train getImminentTrain() {
        Train nearestTrain = getNearestTrain();
        if (nearestTrain == null)
            return nearestTrain;
        // FIXME
        /*if (nearestTrain.getCurrentStation() == this)
            return nearestTrain;*/
        if (!nearestTrain.navigation.isActive())
            return null;
        if (nearestTrain.navigation.distanceToDestination > 30)
            return null;
        return nearestTrain;
    }

    @Nullable
    public Train getNearestTrain() {
        return this.nearestTrain.get();
    }

}
