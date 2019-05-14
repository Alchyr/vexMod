package vexMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;
import vexMod.powers.*;
import vexMod.relics.ShiftingSkin;

import java.util.ArrayList;

public class BlazeAction extends AbstractGameAction {
    private AbstractPlayer p;
    private boolean IsSkin;

    public BlazeAction(AbstractPlayer p, int stacks, boolean isSkin) {
        this.p = p;
        this.amount = stacks;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.IsSkin = isSkin;
    }

    public void update() {
        ArrayList<AbstractPower> blazeList = new ArrayList<>();
        blazeList.add(new StrengthPower(p, this.amount));
        blazeList.add(new DexterityPower(p, this.amount));
        blazeList.add(new DemonFormPower(p, this.amount));
        blazeList.add(new DarkEmbracePower(p, this.amount));
        blazeList.add(new FocusPower(p, this.amount));
        blazeList.add(new IntangiblePlayerPower(p, this.amount));
        blazeList.add(new MalleablePower(p, this.amount));
        blazeList.add(new MidasTouchPower(p, p));
        blazeList.add(new StrikeStormPower(p, p, this.amount));
        blazeList.add(new ChaoticReactorPower(p, p, this.amount));
        blazeList.add(new RupturePower(p, this.amount));
        blazeList.add(new EchoPower(p, this.amount));
        blazeList.add(new PlatedArmorPower(p, this.amount));
        blazeList.add(new ThornsPower(p, this.amount));
        blazeList.add(new TheBombPower(p, this.amount, this.amount));
        blazeList.add(new AccuracyPower(p, this.amount));
        blazeList.add(new ThousandCutsPower(p, this.amount));
        blazeList.add(new RagePower(p, this.amount));
        blazeList.add(new AngerPower(p, this.amount));
        blazeList.add(new AfterImagePower(p, this.amount));
        blazeList.add(new AngryPower(p, this.amount));
        blazeList.add(new AmplifyPower(p, this.amount));
        blazeList.add(new BarricadePower(p));
        blazeList.add(new BerserkPower("Berserk", p, this.amount));
        blazeList.add(new BufferPower(p, this.amount));
        blazeList.add(new BlurPower(p, this.amount));
        blazeList.add(new ConservePower(p, this.amount));
        blazeList.add(new CreativeAIPower(p, this.amount));
        blazeList.add(new DoubleTapPower(p, this.amount));
        blazeList.add(new DrawCardNextTurnPower(p, this.amount));
        blazeList.add(new ElectroPower(p));
        blazeList.add(new EnergizedBluePower(p, this.amount));
        blazeList.add(new EnergizedPower(p, this.amount));
        blazeList.add(new EnvenomPower(p, this.amount));
        blazeList.add(new EquilibriumPower(p, this.amount));
        blazeList.add(new EvolvePower(p, this.amount));
        blazeList.add(new FeelNoPainPower(p, this.amount));
        blazeList.add(new FireBreathingPower(p, this.amount));
        blazeList.add(new GainStrengthPower(p, this.amount));
        blazeList.add(new GrowthPower(p, this.amount));
        blazeList.add(new HeatsinkPower(p, this.amount));
        blazeList.add(new HelloPower(p, this.amount));
        blazeList.add(new InfiniteBladesPower(p, this.amount));
        blazeList.add(new JuggernautPower(p, this.amount));
        blazeList.add(new LoopPower(p, this.amount));
        blazeList.add(new MagnetismPower(p, this.amount));
        blazeList.add(new MayhemPower(p, this.amount));
        blazeList.add(new MetallicizePower(p, this.amount));
        blazeList.add(new NextTurnBlockPower(p, this.amount));
        blazeList.add(new NightmarePower(p, this.amount, AbstractDungeon.returnTrulyRandomCardInCombat()));
        blazeList.add(new NoxiousFumesPower(p, this.amount));
        blazeList.add(new PanachePower(p, this.amount));
        blazeList.add(new PenNibPower(p, this.amount));
        blazeList.add(new PhantasmalPower(p, this.amount));
        blazeList.add(new ReboundPower(p));
        blazeList.add(new RegenPower(p, this.amount));
        blazeList.add(new RepairPower(p, this.amount));
        blazeList.add(new RetainCardPower(p, this.amount));
        blazeList.add(new SadisticPower(p, this.amount));
        blazeList.add(new ToolsOfTheTradePower(p, this.amount));
        blazeList.add(new SharpOrbsPower(p, p, this.amount));
        blazeList.add(new HatredPower(p, p, this.amount));
        blazeList.add(new StoneSkinPower(p, p, this.amount));
        blazeList.add(new EvasivePower(p, p, this.amount));
        blazeList.add(new PropogationPower(p, p, this.amount));

        AbstractPower blazePower = blazeList.get(AbstractDungeon.cardRandomRng.random(0, (blazeList.size() - 1)));

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, blazePower, this.amount, AbstractGameAction.AttackEffect.NONE));

        if (IsSkin) {
            ((ShiftingSkin) AbstractDungeon.player.getRelic(ShiftingSkin.ID)).transformDescription(blazePower.name);
        }

        this.isDone = true;
    }
}