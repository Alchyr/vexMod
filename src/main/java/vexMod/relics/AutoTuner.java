package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.bard.actions.common.SelectMelodyAction;
import com.evacipated.cardcrawl.mod.bard.helpers.MelodyManager;
import com.evacipated.cardcrawl.mod.bard.melodies.AbstractMelody;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import java.util.Collections;
import java.util.List;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class AutoTuner extends CustomRelic {

    public static final String ID = VexMod.makeID("AutoTuner");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("AutoTuner.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public AutoTuner() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.HEAVY);
    }

    @Override
    public void atTurnStart() {
        this.flash();// 55
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));// 56
        List<AbstractMelody> melodybundle = MelodyManager.getAllMelodies();
        List<AbstractMelody> randomMelody = Collections.singletonList(melodybundle.get(AbstractDungeon.cardRandomRng.random(melodybundle.size() - 1)));
        AbstractDungeon.actionManager.addToBottom(new SelectMelodyAction(randomMelody, false));
    }// 59

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
