package vexMod.relics;

import Astrologer.Util.PhaseCheck;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.bard.actions.common.SelectMelodyAction;
import com.evacipated.cardcrawl.mod.bard.helpers.MelodyManager;
import com.evacipated.cardcrawl.mod.bard.melodies.AbstractMelody;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import java.util.Collections;
import java.util.List;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class MidnightOcarina extends CustomRelic {

    public static final String ID = VexMod.makeID("MidnightOcarina");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("MidnightOcarina.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public MidnightOcarina() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
        this.counter = 0;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (PhaseCheck.isStar(card)) {// 33
            ++this.counter;// 34
            if (this.counter % 7 == 0) {// 36
                this.counter = 0;// 37
                this.flash();// 55
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));// 56
                List<AbstractMelody> melodybundle = MelodyManager.getAllMelodies();
                List<AbstractMelody> randomMelody = Collections.singletonList(melodybundle.get(AbstractDungeon.cardRandomRng.random(melodybundle.size() - 1)));
                AbstractDungeon.actionManager.addToBottom(new SelectMelodyAction(randomMelody, false));
            }
        }
    }// 48

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
