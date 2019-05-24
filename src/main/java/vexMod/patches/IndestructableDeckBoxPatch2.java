package vexMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

@SpirePatch(
        clz = ShowCardAndObtainEffect.class,
        method = SpirePatch.CONSTRUCTOR,
        paramtypez = {
                AbstractCard.class,
                float.class,
                float.class,
                boolean.class
        }
)

public class IndestructableDeckBoxPatch2 {
    @SpirePrefixPatch
    public static void Prefix(ShowCardAndObtainEffect __instance, AbstractCard card, float x, float y, boolean convergeCards) {
        if ((card.type == AbstractCard.CardType.CURSE || card.type == AbstractCard.CardType.STATUS) && AbstractDungeon.player.hasRelic("vexMod:IndestructableDeckBox")) {// 24 25
            __instance.duration = 0.0F;// 27
            __instance.isDone = true;// 28
        }
    }
}