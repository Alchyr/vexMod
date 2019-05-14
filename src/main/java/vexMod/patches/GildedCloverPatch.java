package vexMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.random.Random;
import vexMod.relics.GildedClover;

@SpirePatch(
        clz = AbstractDungeon.class,
        method = "rollRarity",
        paramtypez = {
                Random.class
        }
)

public class GildedCloverPatch {
    public static SpireReturn<AbstractCard.CardRarity> Prefix(Random rng) {
        if (AbstractDungeon.player.hasRelic(GildedClover.ID)) {
            return SpireReturn.Return(AbstractCard.CardRarity.RARE);
        }
        return SpireReturn.Continue();
    }
}