package vexMod.patches;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.neow.NeowReward;
import com.megacrit.cardcrawl.neow.NeowReward.NeowRewardDef;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.core.Settings.GameLanguage.ZHS;

public class NeowDootPatch {
    @SpireEnum
    public static NeowReward.NeowRewardType DOOT;

    @SpirePatch(
            clz = NeowReward.class,
            method = "getRewardOptions"
    )

    public static class blessAddDoot {
        public static ArrayList<NeowRewardDef> Postfix(ArrayList<NeowRewardDef> __result, NeowReward __instance, final int category) {
            if (Loader.isModLoaded("bard")) {
                if (category == 1 && DoTheBardStuff.AREYOUTHEDAMNBARD()) {
                    String tmp;
                    if (Settings.language == ZHS) {
                        tmp = "#gDOOT";
                    } else {
                        tmp = "#gDOOT";
                    }
                    __result.add(new NeowRewardDef(DOOT, "[ " + tmp + " ]"));
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
            if (Loader.isModLoaded("bard")) {
                if (__instance.type == DOOT) {
                    DoTheBardStuff.Wahoo();
                }
            }
        }
    }
}