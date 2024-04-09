package plus.dragons.createcommutenetwork.content.commute.train.stationPlatform;

import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.graph.DimensionPalette;
import com.simibubi.create.content.trains.graph.TrackNode;
import com.simibubi.create.content.trains.signal.SingleBlockEntityEdgePoint;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;

import javax.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.util.UUID;

public class Platform extends SingleBlockEntityEdgePoint {

    public UUID id;
    @Nullable
    public UUID belongToStation;
    public String platformCode;
    public WeakReference<Train> nearestTrain;

    public Platform() {
        id = UUID.randomUUID();
        platformCode = "Not Set";
        nearestTrain = new WeakReference<>(null);
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
        id = nbt.getUUID("Id");
        if (nbt.contains("BelongToStation")) belongToStation = nbt.getUUID("BelongToStation");
        platformCode = nbt.getString("PlatformCode");
        nearestTrain = new WeakReference<Train>(null);
    }

    @Override
    public void read(FriendlyByteBuf buffer, DimensionPalette dimensions) {
        super.read(buffer, dimensions);
        id = buffer.readUUID();
        if (buffer.readBoolean())
            belongToStation = buffer.readUUID();
        platformCode = buffer.readUtf();
        if (buffer.readBoolean())
            blockEntityPos = buffer.readBlockPos();
    }

    @Override
    public void write(CompoundTag nbt, DimensionPalette dimensions) {
        super.write(nbt, dimensions);
        nbt.putUUID("Id", id);
        if (belongToStation != null) nbt.putUUID("BelongToStation", belongToStation);
        nbt.putString("PlatformCode", platformCode);
    }

    @Override
    public void write(FriendlyByteBuf buffer, DimensionPalette dimensions) {
        super.write(buffer, dimensions);
        buffer.writeUUID(id);
        buffer.writeBoolean(belongToStation != null);
        if (belongToStation != null)
            buffer.writeUUID(belongToStation);
        buffer.writeUtf(platformCode);
        buffer.writeBoolean(this.blockEntityPos != null);
        if (blockEntityPos != null)
            buffer.writeBlockPos(blockEntityPos);
    }

    @Override
    public boolean canNavigateVia(TrackNode side) {
        return super.canNavigateVia(side);
    }
}
