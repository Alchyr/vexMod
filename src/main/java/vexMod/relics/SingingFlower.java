package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.bard.actions.common.QueueNoteAction;
import com.evacipated.cardcrawl.mod.bard.notes.BuffNote;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class SingingFlower extends CustomRelic {

    public static final String ID = VexMod.makeID("SingingFlower");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("SingingFlower.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("SingingFlower.png"));

    public SingingFlower() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public void onEquip() {
        this.counter = 0;// 42
    }

    @Override
    public void atTurnStart() {
        ++this.counter;// 50
        if (this.counter == 3) {// 53
            this.counter = 0;// 54
            this.flash();// 55
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));// 56
            AbstractDungeon.actionManager.addToBottom(new QueueNoteAction(BuffNote.get()));
        }
    }// 59

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
