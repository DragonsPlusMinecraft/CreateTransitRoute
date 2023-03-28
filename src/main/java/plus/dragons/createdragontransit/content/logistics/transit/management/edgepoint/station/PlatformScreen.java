package plus.dragons.createdragontransit.content.logistics.transit.management.edgepoint.station;

import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.gui.AbstractSimiScreen;
import com.simibubi.create.foundation.gui.element.GuiGameElement;
import com.simibubi.create.foundation.gui.widget.IconButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import plus.dragons.createdragontransit.foundation.gui.CdtGuiTextures;

// TODO Heavily TODO
public abstract class PlatformScreen extends AbstractSimiScreen {
    protected CdtGuiTextures background;
    protected final TransitStationPlatformBlockEntity be;
    protected final PlatformEdgePoint platform;

    public PlatformScreen(TransitStationPlatformBlockEntity be, PlatformEdgePoint platform) {
        super(/*Component.literal(platform.code)*/);
        this.be = be;
        this.platform = platform;
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

    public static class Overview extends PlatformScreen {
        private IconButton bindStation;
        private IconButton editStation;
        private IconButton removeStation;
        private IconButton editPlatform;

        public Overview(TransitStationPlatformBlockEntity be, PlatformEdgePoint platform) {
            super(be,platform);
            this.background = CdtGuiTextures.TRANSIT_STATION_PLATFORM;
        }

        @Override
        protected void init() {
            super.init();
/*
            int x = guiLeft;
            int y = guiTop;

            // Config Button
            Runnable editPlatformCallback = () -> {
                minecraft.setScreen(new PlatformScreen.PlatformEditing(be, platform));
            };
            editPlatform = new WideIconButton(x + 80, y + 60, CdtGuiTextures.I_EDIT_PLATFORM);
            editPlatform.withCallback(editPlatformCallback);

            Runnable bindStationCallback = () -> {
                minecraft.setScreen(new PlatformScreen.StationSelecting(be, platform));
            };
            bindStation = new WideIconButton(x + 110, y + 60, CdtGuiTextures.I_BIND_STATION);
            bindStation.withCallback(bindStationCallback);
            bindStation.active = false;
            bindStation.visible = false;

            Runnable editStationCallback = () -> {
                minecraft.setScreen(new PlatformScreen.StationEditing(be, platform));
            };
            editStation = new WideIconButton(x + 110, y + 60, CdtGuiTextures.I_EDIT_STATION);
            editStation.withCallback(editStationCallback);
            editStation.active = false;
            editStation.visible = false;

            Runnable removeStationCallback = () ->
                    AllPackets.channel.sendToServer(PlatformEditPacket.PlatformUnbind.of(be.getBlockPos(),be.getPlatform().station));
            removeStation = new WideIconButton(x + 140, y + 60, CdtGuiTextures.I_REMOVE_STATION);
            removeStation.withCallback(removeStationCallback);
            removeStation.active = false;
            removeStation.visible = false;

            addRenderableWidgets(editPlatform,bindStation,editStation,removeStation);*/
        }

        @Override
        public void tick() {
            super.tick();
            /*if(platform.station==null){
                bindStation.active = true;
                bindStation.visible = true;
                editStation.active = false;
                editStation.visible = false;
                removeStation.active = false;
                removeStation.visible = false;
            } else {
                bindStation.active = false;
                bindStation.visible = false;
                editStation.active = true;
                editStation.visible = true;
                removeStation.active = true;
                removeStation.visible = true;
            }*/
        }
    }

    public static class PlatformEditing extends PlatformScreen {
        private EditBox platformCodeBox;
        private IconButton confirmPlatformEditing;

        public PlatformEditing(TransitStationPlatformBlockEntity be, PlatformEdgePoint platform) {
            super(be,platform);
        }
    }
}
