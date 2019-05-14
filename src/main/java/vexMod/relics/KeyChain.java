package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class KeyChain extends CustomRelic {


    public static final String ID = VexMod.makeID("KeyChain");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("KeyChain.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("KeyChain.png"));
    private static boolean activated = false;

    public KeyChain() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.SOLID);
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
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(action.target, AbstractDungeon.player, new StrengthPower(action.target, -card.damage), -card.damage));
            if (action.target != null && !action.target.hasPower("Artifact")) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(action.target, AbstractDungeon.player, new GainStrengthPower(action.target, card.damage), card.damage));
            }
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
