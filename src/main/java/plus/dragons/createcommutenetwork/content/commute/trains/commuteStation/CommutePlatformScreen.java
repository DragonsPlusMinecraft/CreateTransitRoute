package plus.dragons.createcommutenetwork.content.commute.trains.commuteStation;

import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.gui.AbstractSimiScreen;
import com.simibubi.create.foundation.gui.element.GuiGameElement;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import plus.dragons.createcommutenetwork.foundation.gui.CcnGuiTextures;

// TODO
public class CommutePlatformScreen extends AbstractSimiScreen {
    protected CcnGuiTextures background;
    protected final CommutePlatformBlockEntity be;
    protected final CommutePlatformEdgePoint edgePoint;

    public CommutePlatformScreen(CommutePlatformBlockEntity be, CommutePlatformEdgePoint edgePoint) {
        super(/*Component.literal(platform.code)*/);
        this.be = be;
        this.edgePoint = edgePoint;
    }

    @Override
    protected void init() {
        setWindowSize(background.width, background.height);
        super.init();
        clearWidgets();
    }

    @Override
    protected void renderWindow(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
        int x = guiLeft;
        int y = guiTop;

        background.render(ms, x, y, this);

        ms.pushPose();
        TransformStack msr = TransformStack.cast(ms);
        msr.pushPose()
                .translate(x + background.width + 4, y + background.height + 4, 100)
                .scale(40)
                .rotateX(-22)
                .rotateY(63);
        GuiGameElement.of(be.getBlockState()
                        .setValue(BlockStateProperties.WATERLOGGED, false))
                .render(ms);

        ms.popPose();
    }
}
