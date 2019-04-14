package vexMod.relics;

import basemod.abstracts.CustomRelic;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import vexMod.VexMod;
import vexMod.cards.Virus;
import vexMod.util.TextureLoader;

import java.util.ArrayList;
import java.util.List;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class EndlessSickness extends CustomRelic {


    public static final String ID = VexMod.makeID("EndlessSickness");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("EndlessSickness.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("EndlessSickness.png"));

    public EndlessSickness() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);
        this.tips.add(new PowerTip("Virus", "Viruses are #rCurse cards that duplicate when drawn."));
    }

    // Gain 1 energy on equip.
    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster += 1;
    }

    // Lose 1 energy on unequip.
    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster -= 1;
    }

    public void atBattleStart() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Virus(), 1, true, true));
    }


    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}