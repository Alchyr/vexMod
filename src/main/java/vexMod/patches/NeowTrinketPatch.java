package vexMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.neow.NeowReward;
import com.megacrit.cardcrawl.neow.NeowReward.NeowRewardDef;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import vexMod.VexMod;
import vexMod.relics.*;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.core.Settings.GameLanguage.ZHS;

public class NeowTrinketPatch {
    @SpireEnum
    public static NeowReward.NeowRewardType GRIFTER_RELIC;

    @SpirePatch(
            clz = NeowReward.class,
            method = "getRewardOptions"
    )

    public static class blessAddTrinket {
        public static ArrayList<NeowRewardDef> Postfix(ArrayList<NeowRewardDef> __result, NeowReward __instance, final int category) {
            if (category == 1 && VexMod.enableMemes) {
                String tmp;
                if (Settings.language == ZHS) {
                    tmp = "#gObtain #ga #grandom #gTrinket";
                } else {
                    tmp = "#gObtain #ga #grandom #gTrinket";
                }
                __result.add(new NeowRewardDef(GRIFTER_RELIC, "[ " + tmp + " ]"));
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
            if (__instance.type == GRIFTER_RELIC) {
                ArrayList<AbstractRelic> themRelics = new ArrayList<>();
                themRelics.add(RelicLibrary.getRelic(VoiceBox.ID));
                themRelics.add(RelicLibrary.getRelic(TheWave.ID));
                themRelics.add(RelicLibrary.getRelic(NotEnergy.ID));
                themRelics.add(RelicLibrary.getRelic(NewsTicker.ID));
                themRelics.add(RelicLibrary.getRelic(StoryBook.ID));
                themRelics.add(RelicLibrary.getRelic(Pepega.ID));
                themRelics.add(RelicLibrary.getRelic(SpireShuffle.ID));
                themRelics.add(RelicLibrary.getRelic(HealthChanger.ID));
                themRelics.add(RelicLibrary.getRelic(Bottle.ID));
                themRelics.add(RelicLibrary.getRelic(Incredibleness.ID));
                themRelics.add(RelicLibrary.getRelic(MiniSolarSystem.ID));
                themRelics.add(RelicLibrary.getRelic(RealismEngine.ID));
                themRelics.add(RelicLibrary.getRelic(PopTire.ID));
                themRelics.add(RelicLibrary.getRelic(JugglerBalls.ID));

                AbstractRelic relicToGive = themRelics.get(AbstractDungeon.miscRng.random(themRelics.size() - 1));
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), relicToGive);
            }
        }
    }
}