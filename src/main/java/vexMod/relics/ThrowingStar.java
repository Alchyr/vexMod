package vexMod.relics;

import Astrologer.Util.PhaseCheck;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class ThrowingStar extends CustomRelic {

    public static final String ID = VexMod.makeID("ThrowingStar");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("ThrowingStar.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public ThrowingStar() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    @Override
    public void atTurnStart() {
        this.counter = 0;// 28
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (PhaseCheck.isStar(card)) {// 33
            ++this.counter;// 34
            if (this.counter % 3 == 0) {// 36
                this.counter = 0;// 37
                this.flash();// 38
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));// 39
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 1), 1));// 40
            }
        }

    }// 48

    public void onVictory() {
        this.counter = -1;// 52
    }// 53

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
