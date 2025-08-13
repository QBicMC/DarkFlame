package github.qbic.darkflame.util;

import github.qbic.darkflame.events.ClientModEvent;
import github.qbic.darkflame.events.ModEvents;

public abstract class StringClientModEvent extends ClientModEvent {

    public abstract void execute(String data);

    protected abstract String getDefault();

    public StringClientModEvent() {
        ModEvents.STRING_CLIENT_EVENTS.put(clientEventID(), this);
    }

    @Override
    public void execute() {
        execute(getDefault());
    }
}
