package vexMod.crossovers;

import ThePokerPlayer.patches.CardColorEnum;
import basemod.BaseMod;
import com.evacipated.cardcrawl.mod.bard.characters.Bard;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import vexMod.relics.MusicalCard;
import vexMod.relics.PokerSong;

public class BardXPokerPlayer {
    public static void Relics() {
        BaseMod.addRelicToCustomPool(new PokerSong(), CardColorEnum.POKER_PLAYER_GRAY);
        BaseMod.addRelicToCustomPool(new MusicalCard(), Bard.Enums.COLOR);
        UnlockTracker.markRelicAsSeen(PokerSong.ID);
        UnlockTracker.markRelicAsSeen(MusicalCard.ID);
    }
}