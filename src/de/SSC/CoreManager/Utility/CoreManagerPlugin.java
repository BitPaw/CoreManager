package de.SSC.CoreManager.Utility;

public class CoreManagerPlugin
{
    protected boolean _hasChanged;
    protected boolean _enabled;
    public PluginState State;

    protected CoreManagerPlugin()
    {
        _enabled = false;
        State = PluginState.Initialized;
    }

    public void Enable()
    {
        _enabled = true;
        State = PluginState.Online;
    }

    public void Disable()
    {
        _enabled = false;
        State = PluginState.Offline;
    }
}
