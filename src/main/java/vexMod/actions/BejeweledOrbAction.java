package vexMod.actions;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.orbs.*;
import vexMod.relics.*;

import com.evacipated.cardcrawl.mod.stslib.actions.defect.*;

public class BejeweledOrbAction extends AbstractGameAction {

    public BejeweledOrbAction() {

    }

    public void update() {
        if (AbstractDungeon.player.orbs.size() > 2) {
            for (int i = 0; i < AbstractDungeon.player.orbs.size() - 2; i++) { // I WAS going to use the other for loop, but this ends up being surprisingly better. Don't question it.
                if (AbstractDungeon.player.orbs.get(i).ID != null &&
                        AbstractDungeon.player.orbs.get(i).ID.equals(AbstractDungeon.player.orbs.get(i + 1).ID) &&
                        AbstractDungeon.player.orbs.get(i).ID.equals(AbstractDungeon.player.orbs.get(i + 2).ID)) {
                    for (int j = 0; j < 2; j++) { // The actions are listed in reverse so they are added into the queue properly. Seriously, why isn't there a thing to add a list of actions together?
                        AbstractDungeon.actionManager.addToTop(new ChannelAction(AbstractOrb.getRandomOrb(true)));
                    }
                    for (int j = 0; j < 3; j++) {
                        AbstractDungeon.actionManager.addToTop(new EvokeSpecificOrbAction(AbstractDungeon.player.orbs.get(i + j)));
                    }
                    AbstractDungeon.actionManager.addToTop(new WaitAction(0.15F));
                    if (AbstractDungeon.player.hasRelic(BejeweledOrb.ID)) {
                        AbstractDungeon.player.getRelic(BejeweledOrb.ID).flash();
                        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, AbstractDungeon.player.getRelic(BejeweledOrb.ID)));
                    }
                }
            }
        }
        this.isDone = true;
    }
}