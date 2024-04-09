package plus.dragons.createcommutenetwork.content.commute.train.stationPlatform;

import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.gui.AbstractSimiScreen;
import com.simibubi.create.foundation.gui.element.GuiGameElement;
import com.simibubi.create.foundation.gui.widget.IconButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import plus.dragons.createcommutenetwork.foundation.gui.CcnGuiTextures;

// TODO
public class PlatformScreen extends AbstractSimiScreen {
    private StationSuggestions stationSuggestions;
    private EditBox platformCode;
    private IconButton confirmButton;
    protected CcnGuiTextures background;
    protected final PlatformBlockEntity be;
    protected final PlatformEdgePoint edgePoint;

    public PlatformScreen(PlatformBlockEntity be, PlatformEdgePoint edgePoint) {
        super(Component.literal("Platform"));
        this.be = be;
        this.edgePoint = edgePoint;
        this.background = CcnGuiTextures.TRANSIT_STATION_PLATFORM;
    }

    @Override
    protected void init() {
        setWindowSize(background.width, background.height);
        super.init();
        clearWidgets();
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        return super.mouseScrolled(pMouseX, pMouseY, pDelta);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return super.keyPressed(keyCode, scanCode, modifiers);
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
