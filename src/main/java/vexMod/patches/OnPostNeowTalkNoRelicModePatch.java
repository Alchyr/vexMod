package vexMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapGenerator;
import com.megacrit.cardcrawl.neow.NeowEvent;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.*;
import vexMod.modifiers.NoRelicMode;
import vexMod.relics.ChestStatue;
import vexMod.relics.CursedCompass;
import vexMod.relics.ImprovementManual;
import vexMod.relics.PaidLearning;

@SpirePatch(
        clz= NeowEvent.class,
        method="dailyBlessing"
)
public class OnPostNeowTalkNoRelicModePatch
{
    @SpirePrefixPatch
    public static void Postfix()
    {
        if (CardCrawlGame.trial.dailyModIDs().contains(NoRelicMode.ID))
        {
            AbstractDungeon.uncommonRelicPool.remove(BottledFlame.ID);
            AbstractDungeon.uncommonRelicPool.remove(BottledLightning.ID);
            AbstractDungeon.uncommonRelicPool.remove(BottledTornado.ID);
            AbstractDungeon.uncommonRelicPool.remove(Matryoshka.ID);
            AbstractDungeon.rareRelicPool.remove(Shovel.ID);
            AbstractDungeon.rareRelicPool.remove(ChestStatue.ID);
            AbstractDungeon.bossRelicPool.remove(Astrolabe.ID);
            AbstractDungeon.bossRelicPool.remove(EmptyCage.ID);
            AbstractDungeon.bossRelicPool.remove(TinyHouse.ID);
            AbstractDungeon.bossRelicPool.remove(ImprovementManual.ID);
            AbstractDungeon.bossRelicPool.remove(Orrery.ID);
            AbstractDungeon.bossRelicPool.remove(CallingBell.ID);
            AbstractDungeon.shopRelicPool.remove(PaidLearning.ID);
            AbstractDungeon.shopRelicPool.remove(DollysMirror.ID);
            AbstractDungeon.shopRelicPool.remove(Cauldron.ID);
            AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.COMMON).instantObtain();
            AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.COMMON).instantObtain();
            AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.UNCOMMON).instantObtain();
            AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.UNCOMMON).instantObtain();
            AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.RARE).instantObtain();
            AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.SHOP).instantObtain();
            AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.BOSS).instantObtain();
        }
    }
}