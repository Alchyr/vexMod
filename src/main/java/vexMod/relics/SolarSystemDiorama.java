package vexMod.relics;

import Astrologer.AstrologerMod;
import Astrologer.Cards.Lunar.Eclipse;
import Astrologer.Cards.Lunar.Moonlight;
import Astrologer.Cards.Solar.Perihelion;
import Astrologer.Cards.Solar.Radiance;
import Astrologer.Patches.StellarPhaseValue;
import Astrologer.Util.PhaseCheck;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class SolarSystemDiorama extends CustomRelic {

    public static final String ID = VexMod.makeID("SolarSystemDiorama");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("SolarSystemDiorama.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("SolarSystemDiorama.png"));

    public SolarSystemDiorama() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster monster) {
        if ((card.cardID.equals(Eclipse.ID) || card.cardID.equals(Moonlight.ID)) && !PhaseCheck.lunarActive()) {
            StellarPhaseValue.stellarAlignment.set(AbstractDungeon.player, !StellarPhaseValue.stellarAlignment.get(AbstractDungeon.player));// 47
            AstrologerMod.stellarUI.updateStellarAlignment(StellarPhaseValue.stellarAlignment.get(AbstractDungeon.player));// 48
            AstrologerMod.stellarUI.updateTooltip();// 51
        }
        if ((card.cardID.equals(Perihelion.ID) || card.cardID.equals(Radiance.ID)) && !PhaseCheck.solarActive()) {
            StellarPhaseValue.stellarAlignment.set(AbstractDungeon.player, !StellarPhaseValue.stellarAlignment.get(AbstractDungeon.player));// 47
            AstrologerMod.stellarUI.updateStellarAlignment(StellarPhaseValue.stellarAlignment.get(AbstractDungeon.player));// 48
            AstrologerMod.stellarUI.updateTooltip();// 51
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
