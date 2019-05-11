package vexMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.Donu;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import vexMod.cards.BlazeBeam;
import vexMod.powers.*;

import java.util.ArrayList;

public class CheckIfDeadCurseAction extends AbstractGameAction {
    private AbstractMonster m;

    public CheckIfDeadCurseAction(AbstractMonster m) {
        this.m = m;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
    }

    public void update() {
        if ((((AbstractMonster) m).isDying || m.currentHealth <= 0)) {

        } else {
            AbstractCard c = CardLibrary.getCurse().makeCopy();
            AbstractDungeon.actionManager.addToBottom(new AddCardToDeckAction(c.makeCopy()));
        }

        this.isDone = true;
    }
}