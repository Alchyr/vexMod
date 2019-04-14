package vexMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import javassist.CtBehavior;
import vexMod.actions.RelicTalkAction;
import vexMod.relics.DeviousBotling;
import vexMod.vfx.RelicSpeechBubble;

@SpirePatch(
        clz = AbstractDungeon.class,
        method = "getRewardCards"
)
public class DeviousBotlingPatch {
    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"numCards"}
    )
    public static void Insert(@ByRef int[] numCards) {
        if (AbstractDungeon.player.hasRelic(DeviousBotling.ID)) {
            int BotlingStealChance = AbstractDungeon.cardRandomRng.random(0,3);
            if (BotlingStealChance == 0) {
                AbstractDungeon.player.getRelic(DeviousBotling.ID).flash();
                if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite || AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss)
                {
                    AbstractDungeon.topLevelEffects.add(new RelicSpeechBubble(AbstractDungeon.player.getRelic(DeviousBotling.ID).currentX, AbstractDungeon.player.getRelic(DeviousBotling.ID).currentY, 5.0F, (AbstractDungeon.player.getRelic(DeviousBotling.ID).DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(105,108)] + AbstractDungeon.getCard(AbstractCard.CardRarity.RARE).name + ", " + AbstractDungeon.player.getRelic(DeviousBotling.ID).DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(34, 72)]), true));
                }
                else
                {
                    AbstractDungeon.topLevelEffects.add(new RelicSpeechBubble(AbstractDungeon.player.getRelic(DeviousBotling.ID).currentX, AbstractDungeon.player.getRelic(DeviousBotling.ID).currentY, 5.0F, (AbstractDungeon.player.getRelic(DeviousBotling.ID).DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(105,108)] + AbstractDungeon.returnRandomCard().name + ", " + AbstractDungeon.player.getRelic(DeviousBotling.ID).DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(34, 72)]), true));
                }
                --numCards[0];
            }
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(ModHelper.class, "isModEnabled");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}