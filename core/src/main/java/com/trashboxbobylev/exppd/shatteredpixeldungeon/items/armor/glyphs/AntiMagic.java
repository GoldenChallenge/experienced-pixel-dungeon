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

package com.trashboxbobylev.exppd.shatteredpixeldungeon.items.armor.glyphs;

import com.trashboxbobylev.exppd.shatteredpixeldungeon.actors.Char;
import com.trashboxbobylev.exppd.shatteredpixeldungeon.actors.buffs.Charm;
import com.trashboxbobylev.exppd.shatteredpixeldungeon.actors.buffs.Weakness;
import com.trashboxbobylev.exppd.shatteredpixeldungeon.actors.mobs.Eye;
import com.trashboxbobylev.exppd.shatteredpixeldungeon.actors.mobs.Shaman;
import com.trashboxbobylev.exppd.shatteredpixeldungeon.actors.mobs.Warlock;
import com.trashboxbobylev.exppd.shatteredpixeldungeon.actors.mobs.Yog;
import com.trashboxbobylev.exppd.shatteredpixeldungeon.items.armor.Armor;
import com.trashboxbobylev.exppd.shatteredpixeldungeon.levels.traps.DisintegrationTrap;
import com.trashboxbobylev.exppd.shatteredpixeldungeon.levels.traps.GrimTrap;
import com.trashboxbobylev.exppd.shatteredpixeldungeon.sprites.ItemSprite;

import java.util.HashSet;

public class AntiMagic extends Armor.Glyph {

	private static ItemSprite.Glowing TEAL = new ItemSprite.Glowing( 0x88EEFF );
	
	public static final HashSet<Class> RESISTS = new HashSet<>();
	static {
		RESISTS.add( Charm.class );
		RESISTS.add( Weakness.class );
		
		RESISTS.add( DisintegrationTrap.class );
		RESISTS.add( GrimTrap.class );
		
		RESISTS.add( Shaman.class );
		RESISTS.add( Warlock.class );
		RESISTS.add( Eye.class );
		RESISTS.add( Yog.BurningFist.class );
	}
	
	@Override
	public int proc(Armor armor, Char attacker, Char defender, int damage) {
		//no proc effect, see Hero.damage
		return damage;
	}

	@Override
	public ItemSprite.Glowing glowing() {
		return TEAL;
	}

}