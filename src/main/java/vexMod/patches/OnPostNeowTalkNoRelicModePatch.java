package vexMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.neow.NeowEvent;
import com.megacrit.cardcrawl.relics.*;
import vexMod.modifiers.NoRelicMode;
import vexMod.relics.ImprovementManual;
import vexMod.relics.PaidLearning;

@SpirePatch(
        clz = NeowEvent.class,
        method = "dailyBlessing"
)
public class OnPostNeowTalkNoRelicModePatch {
    @SpirePrefixPatch
    public static void Postfix() {
        if (!Settings.isDailyRun) {
            if (CardCrawlGame.trial.dailyModIDs().contains(NoRelicMode.ID)) {
                AbstractDungeon.uncommonRelicPool.remove(BottledFlame.ID);
                AbstractDungeon.uncommonRelicPool.remove(BottledLightning.ID);
                AbstractDungeon.uncommonRelicPool.remove(BottledTornado.ID);
                AbstractDungeon.uncommonRelicPool.remove(Matryoshka.ID);
                AbstractDungeon.rareRelicPool.remove(Shovel.ID);
                AbstractDungeon.shopRelicPool.remove(DollysMirror.ID);
                AbstractDungeon.shopRelicPool.remove(Cauldron.ID);
                AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.COMMON).instantObtain();
                AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.COMMON).instantObtain();
                AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.UNCOMMON).instantObtain();
                AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.UNCOMMON).instantObtain();
                AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.RARE).instantObtain();
                AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.SHOP).instantObtain();
                AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.BOSS).instantObtain();
                int tmp = AbstractDungeon.bossRelicPool.size();
                AbstractDungeon.bossRelicPool.clear();
                for (int i = 0; i < tmp; i++) {
                    AbstractDungeon.bossRelicPool.add("nothingatall");
                }

                tmp = AbstractDungeon.commonRelicPool.size();
                AbstractDungeon.commonRelicPool.clear();
                for (int i = 0; i < tmp; i++) {
                    AbstractDungeon.commonRelicPool.add("nothingatall");
                }

                tmp = AbstractDungeon.rareRelicPool.size();
                AbstractDungeon.rareRelicPool.clear();
                for (int i = 0; i < tmp; i++) {
                    AbstractDungeon.rareRelicPool.add("nothingatall");
                }

                tmp = AbstractDungeon.shopRelicPool.size();
                AbstractDungeon.shopRelicPool.clear();
                for (int i = 0; i < tmp; i++) {
                    AbstractDungeon.shopRelicPool.add("nothingatall");
                }

                tmp = AbstractDungeon.uncommonRelicPool.size();
                AbstractDungeon.uncommonRelicPool.clear();
                for (int i = 0; i < tmp; i++) {
                    AbstractDungeon.uncommonRelicPool.add("nothingatall");
                }
            }
        }
    }
}