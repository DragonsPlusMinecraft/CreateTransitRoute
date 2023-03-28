package plus.dragons.createdragontransit.content.logistics.transit.management.edgepoint.station;

import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.logistics.trains.management.edgePoint.station.WideIconButton;
import com.simibubi.create.foundation.gui.AbstractSimiScreen;
import com.simibubi.create.foundation.gui.element.GuiGameElement;
import com.simibubi.create.foundation.gui.widget.IconButton;
import com.simibubi.create.foundation.gui.widget.Indicator;
import com.simibubi.create.foundation.gui.widget.Label;
import com.simibubi.create.foundation.networking.AllPackets;
import com.simibubi.create.foundation.utility.Components;
import com.simibubi.create.foundation.utility.Pair;
import com.simibubi.create.foundation.utility.animation.LerpedFloat;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import plus.dragons.createdragontransit.DragonTransit;
import plus.dragons.createdragontransit.DragonTransitClient;
import plus.dragons.createdragontransit.content.logistics.transit.TransitLine;
import plus.dragons.createdragontransit.content.logistics.transit.TransitStation;
import plus.dragons.createdragontransit.content.logistics.transit.TransitStationPlatform;
import plus.dragons.createdragontransit.foundation.gui.CdtGuiTextures;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class PlatformScreen extends AbstractSimiScreen {
    protected CdtGuiTextures background;
    protected final TransitStationPlatformBlockEntity be;
    protected final TransitStationPlatform platform;

    public PlatformScreen(TransitStationPlatformBlockEntity be, TransitStationPlatform platform) {
        super(Component.literal(platform.code));
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

        public Overview(TransitStationPlatformBlockEntity be, TransitStationPlatform platform) {
            super(be,platform);
            this.background = CdtGuiTextures.TRANSIT_STATION_PLATFORM;
        }

        @Override
        protected void init() {
            super.init();

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

            addRenderableWidgets(editPlatform,bindStation,editStation,removeStation);
        }

        @Override
        public void tick() {
            super.tick();
            if(platform.station==null){
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
            }
        }
    }

    public static class PlatformEditing extends PlatformScreen {
        private EditBox platformCodeBox;
        private IconButton confirmPlatformEditing;

        public PlatformEditing(TransitStationPlatformBlockEntity be, TransitStationPlatform platform) {
            super(be,platform);
        }
    }

    public static class StationSelecting extends PlatformScreen {

        private List<LerpedFloat> horizontalScrolls = new ArrayList<>();
        private LerpedFloat scroll = LerpedFloat.linear()
                .startWithValue(0);
        private final List<Pair<TransitStation, Set<TransitLine>>> entries = new ArrayList<>();
        private final List<Pair<TransitStation, Set<TransitLine>>> shownEntry = new ArrayList<>();
        private Indicator onlyShowOwned;
        private Indicator onlyShowAvailable;
        private EditBox searchBox;

        public StationSelecting(TransitStationPlatformBlockEntity be, TransitStationPlatform platform) {
            super(be,platform);
        }
    }

    public static class StationCreating extends PlatformScreen {
        private EditBox stationNameBox;
        private EditBox stationTranslationNameBox;
        private Indicator privacyIndicator;
        private IconButton confirmStationCreating;

        public StationCreating(TransitStationPlatformBlockEntity be, TransitStationPlatform platform) {
            super(be,platform);
        }
    }

    public static class StationEditing extends PlatformScreen {
        private TransitStation station;
        private EditBox stationNameBox;
        private EditBox stationTranslationNameBox;
        private Indicator privacyIndicator;
        private IconButton confirmStationEditing;

        public StationEditing(TransitStationPlatformBlockEntity be, TransitStationPlatform platform) {
            super(be,platform);
        }
    }
}
