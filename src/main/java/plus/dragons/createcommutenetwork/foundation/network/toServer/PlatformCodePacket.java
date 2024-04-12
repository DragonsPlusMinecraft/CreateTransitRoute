package plus.dragons.createcommutenetwork.foundation.network.toServer;

import com.simibubi.create.foundation.networking.BlockEntityConfigurationPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import plus.dragons.createcommutenetwork.content.commute.train.stationPlatform.PlatformBlock;
import plus.dragons.createcommutenetwork.content.commute.train.stationPlatform.PlatformBlockEntity;

public class PlatformCodePacket extends BlockEntityConfigurationPacket<PlatformBlockEntity> {
    String platformCode;

    public PlatformCodePacket(FriendlyByteBuf buffer) {
        super(buffer);
    }

    public PlatformCodePacket(BlockPos pos, String platformCode) {
        super(pos);
        this.platformCode = platformCode;
    }

    @Override
    protected void writeSettings(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeUtf(platformCode);
    }

    @Override
    protected void readSettings(FriendlyByteBuf friendlyByteBuf) {
        platformCode = friendlyByteBuf.readUtf();
    }

    @Override
    protected void applySettings(PlatformBlockEntity be) {
        Level level = be.getLevel();
        BlockPos blockPos = be.getBlockPos();
        if (level.isLoaded(blockPos)) {
            if (level.getBlockState(blockPos).getBlock() instanceof PlatformBlock) {
                be.updatePlatformInfo(platformCode);
            }
        }
    }
}
