package vexMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.neow.NeowEvent;
import com.megacrit.cardcrawl.relics.*;
import vexMod.modifiers.NoRelicMode;
import vexMod.relics.RockBasket;

@SpirePatch(
        clz = NeowEvent.class,
        method = "dailyBlessing"
)
public class OnPostNeowTalkNoRelicModePatch {
    @SpirePrefixPatch
    public static void Postfix() {
        if (!Settings.isDailyRun && CardCrawlGame.trial != null) {
            if (CardCrawlGame.trial.dailyModIDs().contains(NoRelicMode.ID)) {
                AbstractDungeon.commonRelicPool.remove(Whetstone.ID);
                AbstractDungeon.commonRelicPool.remove(WarPaint.ID);
                AbstractDungeon.uncommonRelicPool.remove(BottledFlame.ID);
                AbstractDungeon.uncommonRelicPool.remove(BottledLightning.ID);
                AbstractDungeon.uncommonRelicPool.remove(BottledTornado.ID);
                AbstractDungeon.uncommonRelicPool.remove(Matryoshka.ID);
                AbstractDungeon.rareRelicPool.remove(Shovel.ID);
                AbstractDungeon.shopRelicPool.remove(DollysMirror.ID);
                AbstractDungeon.shopRelicPool.remove(Cauldron.ID);
                AbstractDungeon.shopRelicPool.remove(RockBasket.ID);
                AbstractDungeon.shopRelicPool.remove(Sling.ID);
                AbstractDungeon.bossRelicPool.remove(BlackStar.ID);
                AbstractDungeon.bossRelicPool.remove(CallingBell.ID);
                AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.COMMON).instantObtain();
                AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.COMMON).instantObtain();
                AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.UNCOMMON).instantObtain();
                AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.UNCOMMON).instantObtain();
                AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.RARE).instantObtain();
                AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.SHOP).instantObtain();
                int tmp = AbstractDungeon.commonRelicPool.size();
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