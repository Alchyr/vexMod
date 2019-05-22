package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.bard.actions.common.QueueNoteAction;
import com.evacipated.cardcrawl.mod.bard.hooks.OnMelodyPlayedHook;
import com.evacipated.cardcrawl.mod.bard.melodies.AbstractMelody;
import com.evacipated.cardcrawl.mod.bard.notes.AttackNote;
import com.evacipated.cardcrawl.mod.bard.notes.BlockNote;
import com.evacipated.cardcrawl.mod.bard.notes.BuffNote;
import com.evacipated.cardcrawl.mod.bard.notes.DebuffNote;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class DeadRequiem extends CustomRelic implements OnMelodyPlayedHook {

    public static final String ID = VexMod.makeID("DeadRequiem");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("DeadRequiem.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public DeadRequiem() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    @Override
    public void onMelodyPlayed(AbstractMelody melody) {
        int noteGotten = AbstractDungeon.cardRandomRng.random(3);
        if (noteGotten == 0) {
            AbstractDungeon.actionManager.addToBottom(new QueueNoteAction(AttackNote.get()));
        } else if (noteGotten == 1) {
            AbstractDungeon.actionManager.addToBottom(new QueueNoteAction(BlockNote.get()));
        } else if (noteGotten == 2) {
            AbstractDungeon.actionManager.addToBottom(new QueueNoteAction(DebuffNote.get()));
        } else if (noteGotten == 3) {
            AbstractDungeon.actionManager.addToBottom(new QueueNoteAction(BuffNote.get()));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
