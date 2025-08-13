package github.qbic.darkflame.events;

public class BlankEvent extends ModEvent {
    @Override
    public EventType getType() {
        return EventType.UNLISTED;
    }

    @Override
    public void execute() { }

    @Override
    public String name() {
        return "none";
    }
}
