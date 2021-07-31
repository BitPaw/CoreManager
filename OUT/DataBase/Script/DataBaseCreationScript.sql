CREATE TABLE `area` 
(
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(20) NOT NULL,
  `AX` int(11) NOT NULL,
  `AY` int(11) NOT NULL,
  `AZ` int(11) NOT NULL,
  `BX` int(11) NOT NULL,
  `BY` int(11) NOT NULL,
  `BZ` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `Name` (`Name`)
); 

CREATE TABLE `location` 
(
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(30) NOT NULL,
  `X` double NOT NULL,
  `Y` double NOT NULL,
  `Z` double NOT NULL,
  `Yaw` double NOT NULL,
  `Pitch` double NOT NULL,
  PRIMARY KEY (`ID`)
);

CREATE TABLE `npc` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IsActive` tinyint(1) NOT NULL DEFAULT 1,
  `Type` varchar(1) NOT NULL,
  `Name` varchar(30) NOT NULL,
  `LookAtPlayer` tinyint(1) NOT NULL,
  `LocationID` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `LocationID` (`LocationID`),
  CONSTRAINT `npc_ibfk_1` FOREIGN KEY (`LocationID`) REFERENCES `location` (`ID`)
);

CREATE TABLE `permission` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(40) NOT NULL,
  `RankID` varchar(20) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `Name` (`Name`),
  CONSTRAINT `permission_ibfk_1` FOREIGN KEY (`RankID`) REFERENCES `rank` (`ID`)
);

CREATE TABLE `player` (
  `PlayerUUID` varchar(36) NOT NULL,
  `OP` tinyint(1) NOT NULL DEFAULT 0,
  `Cracked` tinyint(1) NOT NULL,
  `Banned` tinyint(1) NOT NULL,
  `PlayerName` varchar(20) NOT NULL,
  `CustomName` varchar(40) DEFAULT NULL,
  `Money` float NOT NULL DEFAULT 150,
  `RankName` varchar(15) NOT NULL DEFAULT 'I',
  `IP` varchar(40) DEFAULT NULL,
  `Registered` datetime NOT NULL DEFAULT current_timestamp(),
  `LastSeen` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`PlayerUUID`),
  KEY `RankName` (`RankName`),
  CONSTRAINT `player_ibfk_1` FOREIGN KEY (`RankName`) REFERENCES `rank` (`RankName`)
);

CREATE TABLE `warpprivate` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PlayerUUID` varchar(20) NOT NULL,
  `WarpName` varchar(20) NOT NULL,
  `World` varchar(20) NOT NULL,
  `X` float NOT NULL,
  `Y` float NOT NULL,
  `Z` float NOT NULL,
  `Yaw` float NOT NULL,
  `Pitch` float NOT NULL,
  PRIMARY KEY (`PrivateWarpID`),
  KEY `PlayerUUID` (`PlayerUUID`),
  CONSTRAINT `privatewarp_ibfk_1` FOREIGN KEY (`PlayerUUID`) REFERENCES `player` (`PlayerUUID`)
);

CREATE TABLE `rank` 
(
  `ID` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(20) NOT NULL,
  `ColorTag` varchar(40) NOT NULL DEFAULT '&F',
  `PlayerColor` varchar(10) NOT NULL DEFAULT '&F',
  PRIMARY KEY (`Name`)
);

CREATE TABLE `region` (
  `IsActive` tinyint(1) NOT NULL DEFAULT 1,
  `RegionName` varchar(20) NOT NULL,
  `PlayerUUID` varchar(36) DEFAULT NULL,
  `LocationID` int(11) NOT NULL,
  `Protected` tinyint(1) NOT NULL DEFAULT 1,
  `ProtectAccess` tinyint(1) NOT NULL DEFAULT 0,
  `IgnoreHight` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`RegionName`),
  KEY `LocationID` (`LocationID`),
  KEY `PlayerUUID` (`PlayerUUID`),
  CONSTRAINT `region_ibfk_1` FOREIGN KEY (`LocationID`) REFERENCES `area` (`LocationID`),
  CONSTRAINT `region_ibfk_2` FOREIGN KEY (`PlayerUUID`) REFERENCES `player` (`PlayerUUID`)
);

CREATE TABLE `warp` (
  `WarpName` varchar(20) NOT NULL,
  `World` varchar(20) NOT NULL,
  `X` float NOT NULL,
  `Y` float NOT NULL,
  `Z` float NOT NULL,
  `Yaw` float NOT NULL,
  `Pitch` float NOT NULL,
  PRIMARY KEY (`WarpName`)
);

CREATE TABLE `world` (
  `Active` tinyint(1) NOT NULL DEFAULT 1,
  `WorldName` varchar(20) NOT NULL,
  `CustomName` varchar(40) DEFAULT NULL,
  `MapType` varchar(15) NOT NULL DEFAULT 'NORMAL',
  `MapStyle` varchar(15) NOT NULL DEFAULT 'NORMAL',
  `BorderSize` int(11) DEFAULT 1000000,
  `Difficulty` varchar(10) NOT NULL DEFAULT '0',
  `Hardcore` tinyint(1) NOT NULL DEFAULT 0,
  `PvP` tinyint(1) NOT NULL DEFAULT 1,
  `KeepInventory` tinyint(1) NOT NULL DEFAULT 0,
  `MobGrief` tinyint(1) NOT NULL DEFAULT 1,
  `MobSpawning` tinyint(1) NOT NULL DEFAULT 1,
  `Weather` tinyint(1) NOT NULL DEFAULT 1,
  `DoFireTick` tinyint(1) NOT NULL DEFAULT 1,
  `GenerateStructures` tinyint(1) NOT NULL DEFAULT 1,
  `Seed` mediumtext NOT NULL,
  `SpawnX` float NOT NULL DEFAULT 0,
  `SpawnY` float NOT NULL DEFAULT 0,
  `SpawnZ` float NOT NULL DEFAULT 0,
  `SpawnPitch` float NOT NULL DEFAULT 0,
  `SpawnYaw` float NOT NULL DEFAULT 0,
  `NeedsPermission` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`WorldName`)
);