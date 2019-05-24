//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package vexMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.lib.Matcher.FieldAccessMatcher;
import com.evacipated.cardcrawl.modthespire.lib.Matcher.MethodCallMatcher;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import javassist.CtBehavior;
import vexMod.actions.ActivateExhaustEffectsAction;
import vexMod.relics.IndestructableDeckBox;

import java.util.ArrayList;
import java.util.Iterator;

public class IndestructableDeckBoxPatch {
    public IndestructableDeckBoxPatch() {
    }// 18

    private static class AddToHandLocator extends SpireInsertLocator {
        private AddToHandLocator() {
        }// 217

        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new FieldAccessMatcher(ShowCardAndAddToHandEffect.class, "isDone");// 220
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);// 221
        }
    }

    @SpirePatch(
            clz = ShowCardAndAddToHandEffect.class,
            method = "update"
    )
    public static class AddToHandUpdate {
        public AddToHandUpdate() {
        }// 207

        @SpireInsertPatch(
                locator = IndestructableDeckBoxPatch.AddToHandLocator.class,
                localvars = {"card"}
        )
        public static void Insert(ShowCardAndAddToHandEffect __instance, AbstractCard card) {
            if (IndestructableDeckBoxPatch.Util.gainCardEffectToExhaust.contains(__instance)) {// 210
                AbstractDungeon.player.hand.removeCard(card);// 211
                IndestructableDeckBoxPatch.Util.exhaustIncomingCard(__instance, card);// 212
            }

        }// 214
    }

    @SpirePatch(
            clz = ShowCardAndAddToHandEffect.class,
            method = "<ctor>",
            paramtypez = {AbstractCard.class}
    )
    public static class AddToHandConstructor2 {
        public AddToHandConstructor2() {
        }// 197

        @SpirePostfixPatch
        public static void Postfix(ShowCardAndAddToHandEffect __instance, AbstractCard card) {
            if (IndestructableDeckBoxPatch.Util.needToExhaust(card)) {// 200
                IndestructableDeckBoxPatch.Util.setExhaustStatus(__instance);// 201
            }

        }// 203
    }

    @SpirePatch(
            clz = ShowCardAndAddToHandEffect.class,
            method = "<ctor>",
            paramtypez = {AbstractCard.class, float.class, float.class}
    )
    public static class AddToHandConstructor1 {
        public AddToHandConstructor1() {
        }// 181

        @SpirePostfixPatch
        public static void Postfix(ShowCardAndAddToHandEffect __instance, AbstractCard card, float offsetX, float offsetY) {
            if (IndestructableDeckBoxPatch.Util.needToExhaust(card)) {// 184
                IndestructableDeckBoxPatch.Util.setExhaustStatus(__instance);// 185
            }

        }// 187
    }

    private static class AddToDrawPileLocator extends SpireInsertLocator {
        private AddToDrawPileLocator() {
        }// 164

        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new MethodCallMatcher(AbstractCard.class, "shrink");// 167
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);// 168
        }
    }

    @SpirePatch(
            clz = ShowCardAndAddToDrawPileEffect.class,
            method = "update"
    )
    public static class AddToDrawPileUpdate {
        public AddToDrawPileUpdate() {
        }// 153

        @SpireInsertPatch(
                locator = IndestructableDeckBoxPatch.AddToDrawPileLocator.class,
                localvars = {"card"}
        )
        public static SpireReturn Insert(ShowCardAndAddToDrawPileEffect __instance, AbstractCard card) {
            if (IndestructableDeckBoxPatch.Util.gainCardEffectToExhaust.contains(__instance)) {// 156
                IndestructableDeckBoxPatch.Util.exhaustIncomingCard(__instance, card);// 157
                return SpireReturn.Return(null);// 158
            } else {
                return SpireReturn.Continue();// 160
            }
        }
    }

    @SpirePatch(
            clz = ShowCardAndAddToDrawPileEffect.class,
            method = "<ctor>",
            paramtypez = {AbstractCard.class, boolean.class, boolean.class}
    )
    public static class AddToDrawPileConstruct2 {
        public AddToDrawPileConstruct2() {
        }// 141

        @SpirePostfixPatch
        public static void Postfix(ShowCardAndAddToDrawPileEffect __instance, AbstractCard srcCard, boolean randomSpot, boolean toBottom) {
            if (IndestructableDeckBoxPatch.Util.needToExhaust(srcCard)) {// 144
                AbstractDungeon.player.drawPile.removeCard(srcCard);// 145
                __instance.duration *= 0.5F;// 146
                IndestructableDeckBoxPatch.Util.setExhaustStatus(__instance);// 147
            }

        }// 149
    }

    @SpirePatch(
            clz = ShowCardAndAddToDrawPileEffect.class,
            method = "<ctor>",
            paramtypez = {AbstractCard.class, float.class, float.class, boolean.class, boolean.class, boolean.class}
    )
    public static class AddToDrawPileConstruct1 {
        public AddToDrawPileConstruct1() {
        }// 121

        @SpirePostfixPatch
        public static void Postfix(ShowCardAndAddToDrawPileEffect __instance, AbstractCard srcCard, float x, float y, boolean randomSpot, boolean cardOffset, boolean toBottom) {
            if (IndestructableDeckBoxPatch.Util.needToExhaust(srcCard)) {// 124
                AbstractDungeon.player.drawPile.removeCard(srcCard);// 125
                __instance.duration *= 0.5F;// 126
                IndestructableDeckBoxPatch.Util.setExhaustStatus(__instance);// 127
            }

        }// 129
    }

    private static class AddToDiscardLocator extends SpireInsertLocator {
        private AddToDiscardLocator() {
        }// 101

        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new MethodCallMatcher(AbstractCard.class, "shrink");// 104
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);// 105
        }
    }

    @SpirePatch(
            clz = ShowCardAndAddToDiscardEffect.class,
            method = "update"
    )
    public static class AddToDiscardUpdate {
        public AddToDiscardUpdate() {
        }// 90

        @SpireInsertPatch(
                locator = IndestructableDeckBoxPatch.AddToDiscardLocator.class,
                localvars = {"card"}
        )
        public static SpireReturn Insert(ShowCardAndAddToDiscardEffect __instance, AbstractCard card) {
            if (IndestructableDeckBoxPatch.Util.gainCardEffectToExhaust.contains(__instance)) {// 93
                IndestructableDeckBoxPatch.Util.exhaustIncomingCard(__instance, card);// 94
                return SpireReturn.Return(null);// 95
            } else {
                return SpireReturn.Continue();// 97
            }
        }
    }

    @SpirePatch(
            clz = ShowCardAndAddToDiscardEffect.class,
            method = "<ctor>",
            paramtypez = {AbstractCard.class}
    )
    public static class AddToDiscardConstruct2 {
        public AddToDiscardConstruct2() {
        }// 78

        @SpirePostfixPatch
        public static void Postfix(ShowCardAndAddToDiscardEffect __instance, AbstractCard card) {
            if (IndestructableDeckBoxPatch.Util.needToExhaust(card)) {// 81
                AbstractDungeon.player.discardPile.removeCard(card);// 82
                __instance.duration *= 0.5F;// 83
                IndestructableDeckBoxPatch.Util.setExhaustStatus(__instance);// 84
            }

        }// 86
    }

    @SpirePatch(
            clz = ShowCardAndAddToDiscardEffect.class,
            method = "<ctor>",
            paramtypez = {AbstractCard.class, float.class, float.class}
    )
    public static class AddToDiscardConstruct1 {
        public AddToDiscardConstruct1() {
        }// 60

        @SpirePostfixPatch
        public static void Postfix(ShowCardAndAddToDiscardEffect __instance, AbstractCard srcCard, float x, float y) {
            if (IndestructableDeckBoxPatch.Util.needToExhaust(srcCard)) {// 63
                AbstractDungeon.player.discardPile.removeCard(srcCard);// 64
                __instance.duration *= 0.5F;// 65
                IndestructableDeckBoxPatch.Util.setExhaustStatus(__instance);// 66
            }

        }// 68
    }

    public static class Util {
        static ArrayList<AbstractGameEffect> gainCardEffectToExhaust = new ArrayList();

        public Util() {
        }// 19

        public static boolean needToExhaust(AbstractCard card) {
            if (card.type == CardType.STATUS) {// 23
                Iterator var1 = AbstractDungeon.player.relics.iterator();// 24

                while (var1.hasNext()) {
                    AbstractRelic r = (AbstractRelic) var1.next();
                    if (r.relicId.equals(IndestructableDeckBox.ID)) {// 25
                        r.flash();// 26
                        return true;// 28
                    }
                }
            }

            return false;// 32
        }

        public static void setExhaustStatus(AbstractGameEffect age) {
            gainCardEffectToExhaust.add(age);// 36
        }// 37

        public static void exhaustIncomingCard(AbstractGameEffect age, AbstractCard c) {
            AbstractDungeon.actionManager.addToTop(new ActivateExhaustEffectsAction(c));// 40
            c.unhover();// 41
            c.untip();// 42
            c.stopGlowing();// 43
            AbstractDungeon.effectsQueue.add(new ExhaustCardEffect(c));// 45
            AbstractDungeon.player.exhaustPile.addToTop(c);// 46
            gainCardEffectToExhaust.remove(age);// 47
        }// 48
    }
}
