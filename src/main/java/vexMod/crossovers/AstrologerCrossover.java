package vexMod.crossovers;

import Astrologer.Enums.CardColorEnum;
import basemod.BaseMod;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import vexMod.relics.*;

public class AstrologerCrossover {
    public static void Relics() {
        BaseMod.addRelicToCustomPool(new StarEater(), CardColorEnum.ASTROLOGER);
        BaseMod.addRelicToCustomPool(new Spotlight(), CardColorEnum.ASTROLOGER);
        BaseMod.addRelicToCustomPool(new Trinoculars(), CardColorEnum.ASTROLOGER);
        BaseMod.addRelicToCustomPool(new FakeStar(), CardColorEnum.ASTROLOGER);
        BaseMod.addRelicToCustomPool(new GravityRainbow(), CardColorEnum.ASTROLOGER);
        BaseMod.addRelicToCustomPool(new SolarSystemDiorama(), CardColorEnum.ASTROLOGER);
        BaseMod.addRelicToCustomPool(new ThrowingStar(), CardColorEnum.ASTROLOGER);
        BaseMod.addRelicToCustomPool(new RainbowShades(), CardColorEnum.ASTROLOGER);
        BaseMod.addRelicToCustomPool(new ElectroStars(), CardColorEnum.ASTROLOGER);
        BaseMod.addRelicToCustomPool(new FutureSight(), CardColorEnum.ASTROLOGER);
        BaseMod.addRelicToCustomPool(new MiniStar(), CardColorEnum.ASTROLOGER);
        UnlockTracker.markRelicAsSeen(StarEater.ID);
        UnlockTracker.markRelicAsSeen(FakeStar.ID);
        UnlockTracker.markRelicAsSeen(Spotlight.ID);
        UnlockTracker.markRelicAsSeen(Trinoculars.ID);
        UnlockTracker.markRelicAsSeen(GravityRainbow.ID);
        UnlockTracker.markRelicAsSeen(SolarSystemDiorama.ID);
        UnlockTracker.markRelicAsSeen(ThrowingStar.ID);
        UnlockTracker.markRelicAsSeen(RainbowShades.ID);
        UnlockTracker.markRelicAsSeen(ElectroStars.ID);
        UnlockTracker.markRelicAsSeen(FutureSight.ID);
        UnlockTracker.markRelicAsSeen(MiniStar.ID);
    }
}