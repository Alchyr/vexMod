package vexMod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import com.megacrit.cardcrawl.vfx.combat.TimeWarpTurnEndEffect;
import vexMod.VexMod;

public class PlayerTimeWarp extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = VexMod.makeID("PlayerTimeWarpPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static int STR_AMT;
    private static int COUNTDOWN_AMT;

    public PlayerTimeWarp(AbstractCreature owner, int timer, int str) {
        this.name = NAME;
        this.ID = "Time Warp";
        this.owner = owner;
        this.amount = 0;
        this.updateDescription();
        this.loadRegion("time");
        this.type = PowerType.BUFF;
        STR_AMT = str;
        COUNTDOWN_AMT = timer;
    }

    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("POWER_TIME_WARP", 0.05F);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[3];
    }

    @Override
    public AbstractPower makeCopy() {
        return new PlayerTimeWarp(owner, 12, 2);
    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        this.flashWithoutSound();
        ++this.amount;
        if (this.amount == COUNTDOWN_AMT) {
            this.amount = 0;
            this.playApplyPowerSfx();
            AbstractDungeon.actionManager.cardQueue.clear();

            for (AbstractCard c : AbstractDungeon.player.limbo.group) {
                AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
            }

            AbstractDungeon.player.limbo.group.clear();
            AbstractDungeon.player.releaseCard();
            AbstractDungeon.overlayMenu.endTurnButton.disable(true);
            CardCrawlGame.sound.play("POWER_TIME_WARP", 0.05F);
            AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.GOLD, true));
            AbstractDungeon.topLevelEffectsQueue.add(new TimeWarpTurnEndEffect());
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, STR_AMT), STR_AMT));

        }

        this.updateDescription();
    }

}
