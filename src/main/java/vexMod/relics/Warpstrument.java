package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.bard.hooks.OnNoteQueuedHook;
import com.evacipated.cardcrawl.mod.bard.notes.AbstractNote;
import com.evacipated.cardcrawl.mod.bard.notes.WildCardNote;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class Warpstrument extends CustomRelic implements OnNoteQueuedHook {

    public static final String ID = VexMod.makeID("Warpstrument");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Warpstrument.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Warpstrument.png"));

    public Warpstrument() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void onEquip() {
        this.counter = 0;
    }

    @Override
    public AbstractNote onNoteQueued(AbstractNote note) {
        this.counter++;
        if (this.counter == 10) {
            this.counter = 0;
            return WildCardNote.get();
        }
        return note;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}