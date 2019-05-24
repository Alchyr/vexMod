package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.bard.hooks.OnNoteQueuedHook;
import com.evacipated.cardcrawl.mod.bard.notes.*;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.*;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class MetalNotes extends CustomRelic implements OnNoteQueuedHook {

    public static final String ID = VexMod.makeID("MetalNotes");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("MetalNotes.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public MetalNotes() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.HEAVY);
        this.counter = 0;
    }

    @Override
    public void atPreBattle() {
        AbstractDungeon.player.increaseMaxOrbSlots(3, false);
    }

    @Override
    public AbstractNote onNoteQueued(AbstractNote note) {
        this.counter++;
        if (this.counter % 5 == 0) {
            this.counter = 0;
            if (note instanceof AttackNote) {
                AbstractDungeon.actionManager.addToBottom(new ChannelAction(new Lightning()));
            } else if (note instanceof BlockNote) {
                AbstractDungeon.actionManager.addToBottom(new ChannelAction(new Frost()));
            } else if (note instanceof DebuffNote) {
                AbstractDungeon.actionManager.addToBottom(new ChannelAction(new Dark()));
            } else if (note instanceof BuffNote) {
                AbstractDungeon.actionManager.addToBottom(new ChannelAction(new Plasma()));
            } else if (note instanceof WildCardNote) {
                AbstractDungeon.actionManager.addToBottom(new ChannelAction(AbstractOrb.getRandomOrb(true)));
            }
        }
        return note;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}