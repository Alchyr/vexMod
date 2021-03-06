package vexMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.neow.NeowReward;
import com.megacrit.cardcrawl.neow.NeowReward.NeowRewardDef;
import com.megacrit.cardcrawl.relics.CallingBell;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.core.Settings.GameLanguage.ZHS;

public class NeowBellPatch {
    @SpireEnum
    public static NeowReward.NeowRewardType GET_BELL;

    @SpirePatch(
            clz = NeowReward.class,
            method = "getRewardOptions"
    )

    public static class blessAddBlight {
        public static ArrayList<NeowRewardDef> Postfix(ArrayList<NeowRewardDef> __result, NeowReward __instance, final int category) {
            if (category == 3) {
                if (AbstractDungeon.cardRandomRng.random(4) == 0) {
                    String tmp;
                    if (Settings.language == ZHS) {
                        tmp = "#r获得召唤铃铛 。";
                    } else {
                        tmp = "#rObtain #rCalling #rBell";
                    }
                    __result.add(new NeowRewardDef(GET_BELL, "[ " + tmp + " ]"));
                }
            }
            return __result;
        }
    }

    @SpirePatch(
            clz = NeowReward.class,
            method = "activate"
    )
    public static class ActivatePatch {
        public static void Prefix(NeowReward __instance) {
            if (__instance.type == GET_BELL) {
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), new CallingBell());
            }
        }
    }


}