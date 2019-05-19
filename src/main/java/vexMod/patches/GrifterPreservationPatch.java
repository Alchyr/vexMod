package vexMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.exordium.Hexaghost;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import javassist.CtBehavior;
import vexMod.relics.MidasArmor;
import vexMod.vfx.BouncingRelic;
import vexMod.vfx.OrbitalRelics;
import vexMod.vfx.RelicWave;

import java.util.ArrayList;

@SpirePatch(
        clz = AbstractDungeon.class,
        method = "nextRoomTransition",
        paramtypez = {
                SaveFile.class
        }
)
public class GrifterPreservationPatch {
    private static ArrayList<AbstractGameEffect> carryOver = new ArrayList<>();
    @SpireInsertPatch(
            locator = Locator.class
    )
    public static void Insert(AbstractDungeon __instance, SaveFile saveFile) {
        for (AbstractGameEffect e : AbstractDungeon.effectList)
        {
            if (e instanceof OrbitalRelics || e instanceof BouncingRelic || e instanceof RelicWave)
            {
                carryOver.add(e);
            }
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "resetControllerValues");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }

    @SpirePostfixPatch
    public static void Postfix(AbstractDungeon __instance, SaveFile saveFile) {
        AbstractDungeon.effectList.addAll(carryOver);
        carryOver.clear();
    }
}