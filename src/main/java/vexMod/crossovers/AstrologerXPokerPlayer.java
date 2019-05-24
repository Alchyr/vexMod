package vexMod.crossovers;

import ThePokerPlayer.patches.CardColorEnum;
import basemod.BaseMod;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import vexMod.relics.HeavenlyCard;
import vexMod.relics.StarPokerPlayer;

public class AstrologerXPokerPlayer {
    public static void Relics() {
        BaseMod.addRelicToCustomPool(new StarPokerPlayer(), CardColorEnum.POKER_PLAYER_GRAY);
        BaseMod.addRelicToCustomPool(new HeavenlyCard(), Astrologer.Enums.CardColorEnum.ASTROLOGER);
        UnlockTracker.markRelicAsSeen(StarPokerPlayer.ID);
        UnlockTracker.markRelicAsSeen(HeavenlyCard.ID);
    }
}