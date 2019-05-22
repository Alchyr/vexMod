package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.bard.hooks.OnNoteQueuedHook;
import com.evacipated.cardcrawl.mod.bard.notes.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class CleansingRadio extends CustomRelic implements OnNoteQueuedHook {

    public static final String ID = VexMod.makeID("CleansingRadio");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("CleansingRadio.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("CleansingRadio.png"));

    private int numOfGreenNotes;
    private int numOfOrangeNotes;
    private int numOfBlueNotes;
    private int numOfRedNotes;

    public CleansingRadio() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    @Override
    public void atTurnStart() {
        this.counter = 0;
        numOfGreenNotes = 0;
        numOfOrangeNotes = 0;
        numOfBlueNotes = 0;
        numOfRedNotes = 0;
    }

    @Override
    public AbstractNote onNoteQueued(AbstractNote note) {
        if (note instanceof WildCardNote) {
            numOfGreenNotes++;
            numOfOrangeNotes++;
            numOfBlueNotes++;
            numOfRedNotes++;
            if (numOfGreenNotes > this.counter) {
                this.counter = numOfGreenNotes;
            }
            if (numOfOrangeNotes > this.counter) {
                this.counter = numOfOrangeNotes;
            }
            if (numOfBlueNotes > this.counter) {
                this.counter = numOfBlueNotes;
            }
            if (numOfRedNotes > this.counter) {
                this.counter = numOfRedNotes;
            }
        } else if (note instanceof BuffNote) {
            numOfGreenNotes++;
            if (numOfGreenNotes > this.counter) {
                this.counter = numOfGreenNotes;
            }
        } else if (note instanceof DebuffNote) {
            numOfOrangeNotes++;
            if (numOfOrangeNotes > this.counter) {
                this.counter = numOfOrangeNotes;
            }
        } else if (note instanceof BlockNote) {
            numOfBlueNotes++;
            if (numOfBlueNotes > this.counter) {
                this.counter = numOfBlueNotes;
            }
        } else if (note instanceof AttackNote) {
            numOfRedNotes++;
            if (numOfRedNotes > this.counter) {
                this.counter = numOfRedNotes;
            }
        }
        if (this.counter % 3 == 0) {// 37
            this.flash();// 38
            this.counter = 0;// 39
            if (numOfBlueNotes == 3) {
                numOfBlueNotes = 0;
            }
            if (numOfGreenNotes == 3) {
                numOfGreenNotes = 0;
            }
            if (numOfOrangeNotes == 3) {
                numOfOrangeNotes = 0;
            }
            if (numOfRedNotes == 3) {
                numOfRedNotes = 0;
            }
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));// 40
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(5, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.SLASH_HEAVY));// 41 44
        }
        return note;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}