package vexMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vexMod.cards.GhostlyBlitz;

public class ShitRelicFlashAction extends AbstractGameAction {
    private int numbertoflash;

    public ShitRelicFlashAction(int NumberToFlash) {
        numbertoflash = NumberToFlash;
    }

    @Override
    public void update() {
        AbstractDungeon.player.relics.get(numbertoflash).flash();
        tickDuration();
    }
}