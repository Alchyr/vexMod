package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import vexMod.VexMod;
import vexMod.actions.RelicBallAction;
import vexMod.actions.RelicRapidFireAction;
import vexMod.util.TextureLoader;
import vexMod.vfx.BallRelicData;
import vexMod.vfx.HolyMoleyGreatBallOfRelics;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class RelicLauncher extends CustomRelic {


    public static final String ID = VexMod.makeID("RelicLauncher");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("RelicLauncher.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("RelicLauncher.png"));

    public RelicLauncher() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.SOLID);
    }

    @Override
    public void atTurnStart() {
        AbstractMonster m = AbstractDungeon.getRandomMonster();
        AbstractDungeon.actionManager.addToBottom(new RelicBallAction(m, false));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(m, AbstractDungeon.player.relics.size(), DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
