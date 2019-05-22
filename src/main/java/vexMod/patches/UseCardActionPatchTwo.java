package vexMod.patches;

import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.lib.Matcher.MethodCallMatcher;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;

@SpirePatch(
        clz = UseCardAction.class,
        method = "update"
)
public class UseCardActionPatchTwo {
    public UseCardActionPatchTwo() {
    }// 17

    @SpireInsertPatch(
            locator = UseCardActionPatchTwo.Locator.class,
            localvars = {"targetCard", "duration"}
    )
    public static SpireReturn ReturnToBottom(UseCardAction __instance, AbstractCard targetCard, @ByRef float[] duration) {
        if (AbstractDungeon.player.hasRelic("vexMod:FutureSight")) {// 24
            AbstractDungeon.player.getRelic("vexMod:FutureSight").flash();// 26
            AbstractDungeon.player.hand.moveToBottomOfDeck(targetCard);// 28
            AbstractDungeon.player.hand.applyPowers();// 30
            AbstractDungeon.player.hand.glowCheck();// 31
            duration[0] -= Gdx.graphics.getDeltaTime();// 33
            if (duration[0] < 0.0F) {// 34
                __instance.isDone = true;// 35
            }

            return SpireReturn.Return(null);// 37
        } else {
            return SpireReturn.Continue();// 39
        }
    }

    private static class Locator extends SpireInsertLocator {
        private Locator() {
        }// 42

        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new MethodCallMatcher(CardGroup.class, "moveToDiscardPile");// 47
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);// 48
        }
    }
}
