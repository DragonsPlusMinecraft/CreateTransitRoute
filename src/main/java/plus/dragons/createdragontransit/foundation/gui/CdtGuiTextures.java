package plus.dragons.createdragontransit.foundation.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.gui.UIRenderHelper;
import com.simibubi.create.foundation.gui.element.ScreenElement;
import com.simibubi.create.foundation.utility.Color;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import plus.dragons.createdragontransit.DragonTransit;

public enum CdtGuiTextures implements ScreenElement {

    TRANSIT_STATION_PLATFORM("transit_station_platform_temp", 256, 191),
    I_EDIT_PLATFORM("transit_station_platform_temp", 0, 191,24, 16),
    I_BIND_STATION("transit_station_platform_temp", 24, 191,24, 16),
    I_EDIT_STATION("transit_station_platform_temp", 48, 191,24, 16),
    I_REMOVE_STATION("transit_station_platform_temp", 72, 191,24, 16);
    public static final int FONT_COLOR = 0x575F7A;
    public final ResourceLocation location;
    public final int width;
    public final int height;
    public final int startX;
    public final int startY;

    private CdtGuiTextures(String location, int width, int height) {
        this(location, 0, 0, width, height);
    }

    private CdtGuiTextures(int startX, int startY) {
        this("icons", startX * 16, startY * 16, 16, 16);
    }

    private CdtGuiTextures(String location, int startX, int startY, int width, int height) {
        this(DragonTransit.ID, location, startX, startY, width, height);
    }

    private CdtGuiTextures(String namespace, String location, int startX, int startY, int width, int height) {
        this.location = new ResourceLocation(namespace, "textures/gui/" + location + ".png");
        this.width = width;
        this.height = height;
        this.startX = startX;
        this.startY = startY;
    }

    @OnlyIn(Dist.CLIENT)
    public void bind() {
        RenderSystem.setShaderTexture(0, location);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(PoseStack ms, int x, int y) {
        bind();
        GuiComponent.blit(ms, x, y, 0, startX, startY, width, height, 256, 256);
    }

    @OnlyIn(Dist.CLIENT)
    public void render(PoseStack ms, int x, int y, GuiComponent component) {
        bind();
        component.blit(ms, x, y, startX, startY, width, height);
    }

    @OnlyIn(Dist.CLIENT)
    public void render(PoseStack ms, int x, int y, Color c) {
        bind();
        UIRenderHelper.drawColoredTexture(ms, c, x, y, startX, startY, width, height);
    }
}
