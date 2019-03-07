package vexMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import vexMod.powers.ChaoticReactorPower;
import vexMod.powers.MidasTouchPower;
import vexMod.powers.StrikeStormPower;

import java.util.ArrayList;

public class BlazeAction extends AbstractGameAction {
    private int q;
    private AbstractPlayer p;

    public BlazeAction(AbstractPlayer p, int stacks) {
        this.p = p;
        this.amount = stacks;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
    }

    public void update() {

        ArrayList<AbstractPower> blazeList = new ArrayList<>();
        blazeList.add(new StrengthPower(p, this.amount));
        blazeList.add(new DexterityPower(p, this.amount));
        blazeList.add(new DemonFormPower(p, this.amount));
        blazeList.add(new DarkEmbracePower(p, this.amount));
        blazeList.add(new SlowPower(p, this.amount));
        blazeList.add(new FocusPower(p, this.amount));
        blazeList.add(new IntangiblePlayerPower(p, this.amount));
        blazeList.add(new MalleablePower(p, this.amount));
        blazeList.add(new MidasTouchPower(p, p, this.amount));
        blazeList.add(new StrikeStormPower(p, p, this.amount));
        blazeList.add(new ChaoticReactorPower(p, p, this.amount));
        blazeList.add(new ReactivePower(p));
        blazeList.add(new RupturePower(p, this.amount));
        blazeList.add(new EchoPower(p, this.amount));
        blazeList.add(new PlatedArmorPower(p, this.amount));
        blazeList.add(new ThornsPower(p, this.amount));
        blazeList.add(new TheBombPower(p, this.amount, this.amount));
        blazeList.add(new PainfulStabsPower(p));
        blazeList.add(new AccuracyPower(p, this.amount));
        blazeList.add(new ThousandCutsPower(p, this.amount));
        blazeList.add(new RagePower(p, this.amount));
        blazeList.add(new AngerPower(p, this.amount));

        this.isDone = true;
    }
}
