package plus.dragons.createcommutenetwork.content.commute.train.stationPlatform;

import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.trains.track.ITrackBlock;
import com.simibubi.create.content.trains.track.TrackTargetingBehaviour;
import com.simibubi.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

// TODO
public class PlatformRenderer extends SafeBlockEntityRenderer<PlatformBlockEntity> {

    public PlatformRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    protected void renderSafe(PlatformBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        BlockPos pos = be.getBlockPos();
        TrackTargetingBehaviour<Platform> target = be.edgePoint;
        BlockPos targetPosition = target.getGlobalPosition();
        Level level = be.getLevel();
        BlockState trackState = level.getBlockState(targetPosition);
        Block block = trackState.getBlock();
        if (block instanceof ITrackBlock) {
            if (!be.isVirtual()) {
                ms.pushPose();
                TransformStack.cast(ms).translate(targetPosition.subtract(pos));
                TrackTargetingBehaviour.render(level, targetPosition, target.getTargetDirection(), target.getTargetBezier(), ms, buffer, light, overlay, TrackTargetingBehaviour.RenderedTrackOverlayType.STATION, 1.0F);
                ms.popPose();
            }
        }
    }
}
