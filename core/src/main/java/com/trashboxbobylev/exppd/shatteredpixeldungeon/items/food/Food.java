/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2019 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.trashboxbobylev.exppd.shatteredpixeldungeon.items.food;

import com.trashboxbobylev.exppd.shatteredpixeldungeon.Assets;
import com.trashboxbobylev.exppd.shatteredpixeldungeon.Badges;
import com.trashboxbobylev.exppd.shatteredpixeldungeon.Statistics;
import com.trashboxbobylev.exppd.shatteredpixeldungeon.actors.buffs.Buff;
import com.trashboxbobylev.exppd.shatteredpixeldungeon.actors.buffs.Hunger;
import com.trashboxbobylev.exppd.shatteredpixeldungeon.actors.buffs.Recharging;
import com.trashboxbobylev.exppd.shatteredpixeldungeon.actors.hero.Hero;
import com.trashboxbobylev.exppd.shatteredpixeldungeon.effects.Speck;
import com.trashboxbobylev.exppd.shatteredpixeldungeon.effects.SpellSprite;
import com.trashboxbobylev.exppd.shatteredpixeldungeon.items.Item;
import com.trashboxbobylev.exppd.shatteredpixeldungeon.items.scrolls.ScrollOfRecharging;
import com.trashboxbobylev.exppd.shatteredpixeldungeon.messages.Messages;
import com.trashboxbobylev.exppd.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.trashboxbobylev.exppd.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;

import java.util.ArrayList;

public class Food extends Item {

	public static final float TIME_TO_EAT	= 3f;
	
	public static final String AC_EAT	= "EAT";
	
	public float energy = Hunger.HUNGRY;
	public String message = Messages.get(this, "eat_msg");
	
	{
		stackable = true;
		image = ItemSpriteSheet.RATION;

		bones = true;
	}
	
	@Override
	public ArrayList<String> actions( Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		actions.add( AC_EAT );
		return actions;
	}
	
	@Override
	public void execute( Hero hero, String action ) {

		super.execute( hero, action );

		if (action.equals( AC_EAT )) {
			
			detach( hero.belongings.backpack );
			
			satisfy(hero);
			GLog.i( message );
			
			foodProc( hero );
			
			hero.sprite.operate( hero.pos );
			hero.busy();
			SpellSprite.show( hero, SpellSprite.FOOD );
			Sample.INSTANCE.play( Assets.SND_EAT );
			
			hero.spend( TIME_TO_EAT );
			
			Statistics.foodEaten++;
			Badges.validateFoodEaten();
			
		}
	}
	
	protected void satisfy( Hero hero ){
		(hero.buff( Hunger.class )).satisfy( energy );
	}
	
	public static void foodProc( Hero hero ){
		switch (hero.heroClass) {
			case WARRIOR:
				if (hero.HP < hero.HT) {
					hero.HP = Math.min( hero.HP + 5, hero.HT );
					hero.sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
				}
				break;
			case MAGE:
				//1 charge
				Buff.affect( hero, Recharging.class, 4f );
				ScrollOfRecharging.charge( hero );
				break;
			case ROGUE:
			case HUNTRESS:
				break;
		}
	}
	
	@Override
	public boolean isUpgradable() {
		return false;
	}
	
	@Override
	public boolean isIdentified() {
		return true;
	}
	
	@Override
	public int price() {
		return 10 * quantity;
	}
}
