package com.trashboxbobylev.exppd.shatteredpixeldungeon.items.weapon.missiles;

import com.trashboxbobylev.exppd.shatteredpixeldungeon.Dungeon;
import com.trashboxbobylev.exppd.shatteredpixeldungeon.actors.Actor;
import com.trashboxbobylev.exppd.shatteredpixeldungeon.actors.Char;
import com.trashboxbobylev.exppd.shatteredpixeldungeon.actors.hero.Hero;
import com.trashboxbobylev.exppd.shatteredpixeldungeon.items.weapon.melee.T6Weapon;
import com.trashboxbobylev.exppd.shatteredpixeldungeon.mechanics.Ballistica;
import com.trashboxbobylev.exppd.shatteredpixeldungeon.messages.Messages;
import com.trashboxbobylev.exppd.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.trashboxbobylev.exppd.shatteredpixeldungeon.sprites.MissileSprite;
import com.trashboxbobylev.exppd.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;

public class CobaltScythe extends MissileWeapon {
    {
        image = ItemSpriteSheet.COBALT_SCYTHE;

        tier = 6;
        baseUses = 3;
    }

    @Override
    public int min(int lvl) {
        return  6 +
                lvl;
    }

    public int exp;

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put("exp", exp);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        exp = bundle.getInt("exp");
    }

    public void gainExp(int exp ) {
        this.exp += exp;
        if (this.exp >= 500){
            while (this.exp >= 500){
                upgrade();
                this.exp -= 500;
            }
        }
    }

    @Override
    public String info() {
        String inf = super.info();
        inf += " " + Messages.get(T6Weapon.class, "exp", 500 - exp);
        return inf;
    }

    @Override
    public int max(int lvl) {
        return  tier*6 +     //42
                8 * lvl;//+8
    }


    @Override
    public void rangedHit(Char enemy, int cell ) {
        super.rangedHit(enemy, cell);
        circleBack(cell, curUser);

        //throws other chars around the center.
        for (int i  : PathFinder.NEIGHBOURS8){
            Char ch = Actor.findChar(cell + i);

            if (ch != null && ch != curUser){
                ch.damage(Math.round(damageRoll(curUser)), this);
            }
        }
    }

    @Override
    protected void rangedMiss( int cell ) {
        super.rangedMiss(cell);
        circleBack( cell, curUser );
    }

    private void circleBack( int from, Hero owner ) {

        ((MissileSprite)curUser.sprite.parent.recycle( MissileSprite.class )).
                reset( from, owner.sprite, curItem, null );

        if (!collect( curUser.belongings.backpack )) {
            Dungeon.level.drop( this, owner.pos ).sprite.drop();
        }
    }
}