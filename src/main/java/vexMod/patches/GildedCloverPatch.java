package vexMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.random.Random;
import vexMod.VexMod;
import vexMod.cards.*;
import vexMod.relics.GildedClover;

import javax.smartcardio.Card;

import static com.evacipated.cardcrawl.modthespire.lib.SpireReturn.Continue;
import static com.evacipated.cardcrawl.modthespire.lib.SpireReturn.Return;
import static com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.RARE;

@SpirePatch(
        clz=AbstractDungeon.class,
        method="rollRarity",
        paramtypez = {
                Random.class
        }
)

public class GildedCloverPatch
{
    public static SpireReturn<AbstractCard.CardRarity> Prefix(Random rng)
    {
        if (AbstractDungeon.player.hasRelic(GildedClover.ID))
        {
            return SpireReturn.Return(AbstractCard.CardRarity.RARE);
        }
        return SpireReturn.Continue();
    }
}