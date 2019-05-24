package vexMod.crossovers;

import ThePokerPlayer.patches.CardColorEnum;
import basemod.BaseMod;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import vexMod.relics.IndestructableDeckBox;
import vexMod.relics.RoboticCards;

public class PokerPlayerCrossover {
    public static void Relics() {
        BaseMod.addRelicToCustomPool(new IndestructableDeckBox(), CardColorEnum.POKER_PLAYER_GRAY);
        BaseMod.addRelicToCustomPool(new RoboticCards(), CardColorEnum.POKER_PLAYER_GRAY);
        UnlockTracker.markRelicAsSeen(RoboticCards.ID);
        UnlockTracker.markRelicAsSeen(IndestructableDeckBox.ID);
    }
}