package plus.dragons.createcommutenetwork.content.commute.train.stationPlatform;

import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.trains.station.NoShadowFontWrapper;
import com.simibubi.create.foundation.gui.AbstractSimiScreen;
import com.simibubi.create.foundation.gui.element.GuiGameElement;
import com.simibubi.create.foundation.gui.widget.IconButton;
import com.simibubi.create.foundation.utility.Components;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import plus.dragons.createcommutenetwork.entry.CcnPackets;
import plus.dragons.createcommutenetwork.foundation.gui.CcnGuiTextures;
import plus.dragons.createcommutenetwork.foundation.network.PlatformEditPacket;

import java.util.function.Consumer;

public class PlatformScreen extends AbstractSimiScreen {
    private StationSuggestions stationSuggestions; //TODO need to finish auto suggest and auto check for validity
    private EditBox platformCode;
    private IconButton confirmButton;
    protected CcnGuiTextures background;
    protected final PlatformBlockEntity be;
    protected final Platform edgePoint;

    // TODO display info of other platform info

    public PlatformScreen(PlatformBlockEntity be, Platform edgePoint) {
        super(Component.literal("Platform"));
        this.be = be;
        this.edgePoint = edgePoint;
        this.background = CcnGuiTextures.TRANSIT_STATION_PLATFORM;
    }

    @Override
    protected void init() {
        setWindowSize(background.width, background.height);
        super.init();
        int x = this.guiLeft;
        int y = this.guiTop;

        Consumer<String> onTextChanged = (s) -> {
            this.platformCode.x = this.nameBoxX(s, this.platformCode);
        };
        this.platformCode = new EditBox(new NoShadowFontWrapper(this.font), x + 23, y + 4, this.background.width - 20, 10, Components.literal(this.edgePoint.platformCode));
        this.platformCode.setBordered(false);
        this.platformCode.setMaxLength(25);
        this.platformCode.setTextColor(5841956);
        this.platformCode.setValue(this.edgePoint.platformCode);
        this.platformCode.changeFocus(false);
        this.platformCode.mouseClicked(0.0, 0.0, 0);
        this.platformCode.setResponder(onTextChanged);
        this.platformCode.x = this.nameBoxX(this.platformCode.getValue(), this.platformCode);
        this.addRenderableWidget(this.platformCode);
    }

    private int nameBoxX(String s, EditBox nameBox) {
        return this.guiLeft + this.background.width / 2 - (Math.min(this.font.width(s), nameBox.getWidth()) + 10) / 2;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.stationSuggestions != null) {
            this.stationSuggestions.tick();
        }
        if (this.getFocused() != this.platformCode) {
            this.platformCode.setCursorPosition(this.platformCode.getValue().length());
            this.platformCode.setHighlightPos(this.platformCode.getCursorPosition());
        }
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (!this.platformCode.isFocused() && pMouseY > (double) this.guiTop && pMouseY < (double) (this.guiTop + 14) && pMouseX > (double) this.guiLeft && pMouseX < (double) (this.guiLeft + this.background.width)) {
            this.platformCode.setFocus(true);
            this.platformCode.setHighlightPos(0);
            this.setFocused(this.platformCode);
            return true;
        } else {
            return super.mouseClicked(pMouseX, pMouseY, pButton);
        }
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) { //TODO display part
        return super.mouseScrolled(pMouseX, pMouseY, pDelta);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        boolean hitEnter = this.getFocused() instanceof EditBox && (keyCode == 257 || keyCode == 335);
        if (hitEnter && this.platformCode.isFocused()) {
            this.platformCode.setFocus(false);
            this.syncPlatformCode();
            return true;
        } else {
            return super.keyPressed(keyCode, scanCode, modifiers);
        }
    }

    private void syncPlatformCode() {
        if (!this.platformCode.getValue().equals(this.edgePoint.platformCode)) {
            // TODO need to do a check for duplicate here
            CcnPackets.getChannel().sendToServer(new PlatformEditPacket(this.be.getBlockPos(), edgePoint.belongToStation, this.platformCode.getValue()));
        }

    }

    private void onStationEdited(String text) {
        if (this.stationSuggestions != null) {
            this.stationSuggestions.updateCommandInfo();
        }
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
