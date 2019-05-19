package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import vexMod.VexMod;
import vexMod.actions.RelicBallAction;
import vexMod.actions.RelicRapidFireAction;
import vexMod.util.TextureLoader;
import vexMod.vfx.BallRelicData;
import vexMod.vfx.HolyMoleyGreatBallOfRelics;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class RockBasket extends CustomRelic {


    public static final String ID = VexMod.makeID("RockBasket");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("RockBasket.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("RockBasket.png"));


    public RockBasket() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.MAGICAL);
    }

    @Override
    public void onEquip() {
        AbstractDungeon.commonRelicPool.removeIf(wahoo -> !wahoo.startsWith("vexMod:"));
        AbstractDungeon.uncommonRelicPool.removeIf(wahoo -> !wahoo.startsWith("vexMod:"));
        AbstractDungeon.rareRelicPool.removeIf(wahoo -> !wahoo.startsWith("vexMod:"));
        AbstractDungeon.shopRelicPool.removeIf(wahoo -> !wahoo.startsWith("vexMod:"));
        AbstractDungeon.bossRelicPool.removeIf(wahoo -> !wahoo.startsWith("vexMod:"));
    }

    @Override
    public void atTurnStart() {
        int numOfVexModRelics = 0;
        for (AbstractRelic ar : AbstractDungeon.player.relics)
        {
            if (ar.relicId.startsWith("vexMod:")) {
                numOfVexModRelics++;
            }
        }
        AbstractDungeon.actionManager.addToBottom(new RelicBallAction(AbstractDungeon.getRandomMonster(), numOfVexModRelics, true));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
