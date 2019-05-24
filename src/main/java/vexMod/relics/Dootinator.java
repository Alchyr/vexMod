package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.bard.hooks.OnNoteQueuedHook;
import com.evacipated.cardcrawl.mod.bard.notes.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class Dootinator extends CustomRelic implements OnNoteQueuedHook {

    public static final String ID = VexMod.makeID("Dootinator");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Dootinator.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("RelicLauncher.png"));

    public Dootinator() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    @Override
    public AbstractNote onNoteQueued(AbstractNote note) {
        if (note instanceof AttackNote) {
            CardCrawlGame.sound.playV("vexMod:Attack", 2.0F);
        } else if (note instanceof BlockNote) {
            CardCrawlGame.sound.playV("vexMod:Block", 2.0F);
        } else if (note instanceof BuffNote) {
            CardCrawlGame.sound.playV("vexMod:Buff", 2.0F);
        } else if (note instanceof DebuffNote) {
            CardCrawlGame.sound.playV("vexMod:Debuff", 2.0F);
        } else if (note instanceof WildCardNote) {
            int wahoo = AbstractDungeon.cardRandomRng.random(3);
            if (wahoo == 0) {
                CardCrawlGame.sound.playV("vexMod:Attack", 2.0F);
            } else if (wahoo == 1) {
                CardCrawlGame.sound.playV("vexMod:Block", 2.0F);
            } else if (wahoo == 2) {
                CardCrawlGame.sound.playV("vexMod:Buff", 2.0F);
            } else if (wahoo == 3) {
                CardCrawlGame.sound.playV("vexMod:Debuff", 2.0F);
            }
        }
        return note;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
