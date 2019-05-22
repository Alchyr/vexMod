package vexMod.relics;

import Astrologer.Powers.Starlit;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class Spotlight extends CustomRelic {


    public static final String ID = VexMod.makeID("Spotlight");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Spotlight.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));


    public Spotlight() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.HEAVY);
    }

    @Override
    public void atBattleStart() {
        this.flash();// 24
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (!m.isDead && !m.isDying) {// 26
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new Starlit(m, 3), 3));// 27
            }
        }
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
