package vexMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.BlightHelper;
import com.megacrit.cardcrawl.neow.NeowReward;
import com.megacrit.cardcrawl.neow.NeowReward.NeowRewardDef;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.core.Settings.GameLanguage.ZHS;

public class NeowBlightPatch {
    @SpireEnum
    public static NeowReward.NeowRewardType BLIGHT_BOSS;

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
                        tmp = "#r获得一样随机的荒疫。 #g获得2件随机的BOSS遗物。";
                    } else {
                        tmp = "#rObtain #ra #rrandom #rBlight #gObtain #g2 #grandom #gboss #gRelics";
                    }
                    __result.add(new NeowRewardDef(BLIGHT_BOSS, "[ " + tmp + " ]"));
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
            if (__instance.type == BLIGHT_BOSS) {
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.BOSS));
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.BOSS));
                AbstractDungeon.getCurrRoom().spawnBlightAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), BlightHelper.getRandomBlight());
            }
        }
    }


}