package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.LockOnPower;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class GazerBeam extends CustomRelic {


    public static final String ID = VexMod.makeID("GazerBeam");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("GazerBeam.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("GazerBeam.png"));
    private static boolean activated = false;

    public GazerBeam() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.SOLID);
    }

    public void atBattleStart() {
        activated = false;
    }

    @Override
    public void atTurnStart() {
        if (!activated) {
            this.beginLongPulse();
        }
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK && !activated && action.target != null) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(action.target, AbstractDungeon.player, new LockOnPower(action.target, 2)));
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(action.target, this));
            this.stopPulse();
            activated = true;
        }
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
