package plus.dragons.createcommutenetwork.foundation.network;

import com.simibubi.create.foundation.networking.BlockEntityConfigurationPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import plus.dragons.createcommutenetwork.content.commute.train.stationPlatform.PlatformBlock;
import plus.dragons.createcommutenetwork.content.commute.train.stationPlatform.PlatformBlockEntity;

import javax.annotation.Nullable;
import java.util.UUID;

public class PlatformEditPacket extends BlockEntityConfigurationPacket<PlatformBlockEntity> {

    @Nullable
    UUID stationId;
    String platformCode;

    public PlatformEditPacket(FriendlyByteBuf buffer) {
        super(buffer);
    }

    public PlatformEditPacket(BlockPos pos, @Nullable UUID stationId, String platformCode) {
        super(pos);
        this.stationId = stationId;
        this.platformCode = platformCode;
    }

    @Override
    protected void writeSettings(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBoolean(stationId != null);
        if (stationId != null) friendlyByteBuf.writeUUID(stationId);
        friendlyByteBuf.writeUtf(platformCode);
    }

    @Override
    protected void readSettings(FriendlyByteBuf friendlyByteBuf) {
        if (friendlyByteBuf.readBoolean())
            stationId = friendlyByteBuf.readUUID();
        platformCode = friendlyByteBuf.readUtf();
    }

    @Override
    protected void applySettings(PlatformBlockEntity be) {
        Level level = be.getLevel();
        BlockPos blockPos = be.getBlockPos();
        if (level.isLoaded(blockPos)) {
            if (level.getBlockState(blockPos).getBlock() instanceof PlatformBlock) {
                be.updatePlatformInfo(stationId, platformCode);
            }
        }


    }
}
