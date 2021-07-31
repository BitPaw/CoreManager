package de.BitFire.API.CoreManager;

public enum CoreManagerCommand 
{
	// System
	exit,
	sysi,
	sysinfo,
	systeminfo,
	lag,
	creload,
	pc,
	printcolors,
	
	// Database
	sql,
	
	// Player
	gm,
	speed,
	hat,
	head,  
	ci,
	clearinventory,
	heal,
	cs,
	changeskin,
	changeplayertag,
	cc,
	clearchat,	
	cn,
	changename,
	me,
	whoami,
	whoisme,
	lp,
	listplayer,
	
	
	// Econemy
	m,
	money,
	p,
	pay,
	
	// Ranks
	lr,
	listranks,
	cr,
	changerank, 
	dr,
	deleterank,
	
	// Teleport
	back, 
	b,	
	home,
	tp,
	teleport,
	setlocation,
	tpl,
	teleporttolocation,
	setspawn,
	tpw,
	teleporttoworld,  
	
	// Warp
	w,
	warp,
	warps,
	setwarp,
	delwarp,
	spawn,
	ws,
	worldspawn,
	sws,
	setworldspawn,
	
	// Portal
	portal,
	pt,
	
	// PvP
	pvp,
	
	// World
	day,
	night,
	rain,
	dw,
	deleteworld,
	cw,
	createworld,
	lw,
	listworlds,
	
	// Other
	npc,
	dc,
	digitalclock
}