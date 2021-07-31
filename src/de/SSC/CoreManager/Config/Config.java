package de.SSC.CoreManager.Config;

import java.io.File;

import de.SSC.CoreManager.Chat.ChatConfig;
import de.SSC.CoreManager.Chat.Logger;
import de.SSC.CoreManager.Chat.MessageType;
import de.SSC.CoreManager.Chat.MessagesConfig;
import de.SSC.CoreManager.Chat.Module;
import de.SSC.CoreManager.DataBase.DataBaseConfig;
import de.SSC.CoreManager.Economy.EconemyConfig;
import de.SSC.CoreManager.Rank.RankConfig;
import de.SSC.CoreManager.Sign.SignEditConfig;
import de.SSC.CoreManager.System.BaseSystem;
import de.SSC.CoreManager.System.ISystem;
import de.SSC.CoreManager.System.SystemPriority;
import de.SSC.CoreManager.System.SystemState;
import de.SSC.CoreManager.Tab.PingConfig;
import de.SSC.CoreManager.World.WorldsConfig;
import de.SSC.Timber.TimberConfig;

public class Config extends BaseSystem implements ISystem
{
	private static Config _instance;
	private Logger _logger;
	private final String _filePath = "plugins\\CoreManager\\CoreManagerConfig.yml";
	//private final String _fileHeaderName = "Config";
	private final String _directoryPath = "plugins\\CoreManager\\";
	//private final String _fileName =  "CoreManager_Configuration.yml";
	
	public ChatConfig Chat;
	public PingConfig Ping;
	public MessagesConfig Messages;
	public DataBaseConfig DataBase;
	public EconemyConfig Econemy;
	public RankConfig Rank;
	public WorldsConfig Worlds; 
	public SignEditConfig SignEdit;
	public TimberConfig Timber;
		 
	private File _file;  
 
	private Config()  
	{	 
		super(Module.Config, SystemState.Active, SystemPriority.Essential);
		_instance = this;  
	 
		DataBase = new DataBaseConfig();		 
		Messages = new MessagesConfig();	 
		Chat = new ChatConfig();	
		Ping = new PingConfig();	
		Econemy = new EconemyConfig();	 
		Rank = new RankConfig();	
		Worlds = new WorldsConfig();	 
		SignEdit = new SignEditConfig();	
		Timber = new TimberConfig();
		
		LoadReferences();
		
	  //_logger = Logger.Instance();
	  
	 // _logger.SendToConsole(Module.Logger, MessageType.Online, Messages.ConsoleIO.On);  
		
		
		// file Stuff
		{
			_file = new File(_filePath); 
		}
	}
  
  public static Config Instance() 
  {
	  return _instance == null ?  new Config() : _instance;
  }
  
  @Override
  public void LoadReferences() 
  {
	  DataBase.LoadDefaults(); 	
	  Messages.LoadDefaults(); 
	  Chat.LoadDefaults(); 
	  Ping.LoadDefaults();
	  Econemy.LoadDefaults();
	  Rank.LoadDefaults();
	  Worlds.LoadDefaults();
	  SignEdit.LoadDefaults();
	  Timber.LoadDefaults();  	
  }
  
  private boolean DoesConfigFileExists()
  {
	  boolean fileExists;	
	  String message;	  	  
	
	  if(_file == null)
	  {
		  message = "&cfile was null? &8DoesConfigFileExists()";
		  
		  _logger.SendToConsole(Module.Config, MessageType.Error, message);		 
		  
		  fileExists = false;
	  }
	  else
	  {
		  fileExists = _file.exists();	
	  }	 
	  
	  if(fileExists)
	  {
		  message = "&7Config file &afound";
		  
		  _logger.SendToConsole(Module.Config, MessageType.Info, message);
	  }
	  else
	  {
		  message = "&7Config file does &cnot exists&7!";
		  
		  _logger.SendToConsole(Module.Config, MessageType.Error, message);		  
	  }

	  return fileExists;
  }
  
  private void CreateNewConfigFile()
  {
	  String message = "&7Creating &3new &7folder structure.";
	  File file = new File(_directoryPath);
	  
	  _logger.SendToConsole(Module.Config, MessageType.Info, message);	
	  
	  file.mkdirs();
	  
	  WriteFile();
  }
  
  private void WriteFile()
  {
	  /*
	  String message;
	  YamlWriter writer; 	
	  FileWriter fileWriter;
	  _logger = Logger.Instance();	  
	  
	  try
	  { 		  
		  fileWriter = new FileWriter(_filePath);		  
		  
		  writer = new YamlWriter(fileWriter);
		  writer.getConfig().setClassTag(_fileHeaderName, this.getClass());		  
		
		  writer.write(this);
		
		  writer.close();	
		  
		  message = "&7Config &asuccessful &7saved &7from : &8" + _filePath;
		  
		  _logger.SendToConsole(Module.Config, MessageType.Info, message);
	  }
	  catch (YamlException e) 
	  {				  
		  message = "&7Config &cfailed to save &7: &8" + e.getMessage();
		  
		  _logger.SendToConsole(Module.Config, MessageType.Info, message);
	  } 
	  catch (IOException e) 
	  {				
		  message = "&7Config &cfailed to save &7: &8" + e.getMessage();
		  
		  _logger.SendToConsole(Module.Config, MessageType.Info, message);
	  }
	  */
  }
  
  public void Load()
  {
	  /*
	  String message;
	  YamlReader reader;
	  _logger = Logger.Instance();	  
	  
	  if(!DoesConfigFileExists())
	  {
		  Save();
	  }	  	  
	  
	  try 
	  {		
		  reader = new YamlReader(new FileReader(_filePath));
		  		  
		  _instance = reader.read(this.getClass());		
		  
		  message = "&7Config loaded &asuccessful &7: &8" + _filePath;
		  
		  _logger.SendToConsole(Module.Config, MessageType.Info, message);
	  }
	  catch (YamlException e)
	  {
		  message = "&7Config &cfailed &7to load &7: &8" + e.getMessage();
		  
		  _logger.SendToConsole(Module.Config, MessageType.Info, message);
	  } 
	  catch (FileNotFoundException e) 
	  {
		  message = "&7Config &cfailed &7to load &7: &8" + e.getMessage();
		  
		  _logger.SendToConsole(Module.Config, MessageType.Info, message);
	  }
	  */
  }
  
  public void Save()
  {	  
	  _logger = Logger.Instance();
	  
	  if(!DoesConfigFileExists())
	  {
		  CreateNewConfigFile();
	  }	   
	  else
	  {
		  WriteFile();
	  }
  }
}