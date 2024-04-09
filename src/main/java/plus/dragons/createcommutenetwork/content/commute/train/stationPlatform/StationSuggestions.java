package plus.dragons.createcommutenetwork.content.commute.train.stationPlatform;

import com.mojang.brigadier.context.StringRange;
import com.mojang.brigadier.suggestion.Suggestion;
import com.simibubi.create.foundation.utility.IntAttached;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.CommandSuggestions;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.util.Mth;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StationSuggestions extends CommandSuggestions {
    private EditBox textBox;
    private List<IntAttached<String>> viableStations;
    private String previous = "<>";
    private Font font;
    private boolean active;
    List<Suggestion> currentSuggestions;
    private int yOffset;

    public StationSuggestions(Minecraft pMinecraft, Screen pScreen, EditBox pInput, Font pFont, List<IntAttached<String>> viableStations, int yOffset) {
        super(pMinecraft, pScreen, pInput, pFont, true, true, 0, 7, false, -298831824);
        this.textBox = pInput;
        this.font = pFont;
        this.viableStations = viableStations;
        this.yOffset = yOffset;
        this.currentSuggestions = new ArrayList();
        this.active = false;
    }

    public void tick() {
        if (this.suggestions == null) {
            this.textBox.setSuggestion("");
        }

        if (this.active != this.textBox.isFocused()) {
            this.active = this.textBox.isFocused();
            this.updateCommandInfo();
        }
    }

    public void updateCommandInfo() {
        String value = this.textBox.getValue();
        if (!value.equals(this.previous)) {
            if (!this.active) {
                this.suggestions = null;
            } else {
                this.previous = value;
                this.currentSuggestions = this.viableStations.stream().filter((ia) -> {
                    return !((String) ia.getValue()).equals(value) && ((String) ia.getValue()).toLowerCase().startsWith(value.toLowerCase());
                }).sorted((ia1, ia2) -> {
                    return Integer.compare((Integer) ia1.getFirst(), (Integer) ia2.getFirst());
                }).map(IntAttached::getValue).map((s) -> {
                    return new Suggestion(new StringRange(0, s.length()), s);
                }).toList();
                this.showSuggestions(false);
            }
        }
    }

    public void showSuggestions(boolean pNarrateFirstSuggestion) {
        if (this.currentSuggestions.isEmpty()) {
            this.suggestions = null;
        } else {
            int width = 0;

            Suggestion suggestion;
            for (Iterator var3 = this.currentSuggestions.iterator(); var3.hasNext(); width = Math.max(width, this.font.width(suggestion.getText()))) {
                suggestion = (Suggestion) var3.next();
            }

            int x = Mth.clamp(this.textBox.getScreenX(0), 0, this.textBox.getScreenX(0) + this.textBox.getInnerWidth() - width);
            this.suggestions = new CommandSuggestions.SuggestionsList(x, 72 + this.yOffset, width, this.currentSuggestions, false);
        }
    }
}
