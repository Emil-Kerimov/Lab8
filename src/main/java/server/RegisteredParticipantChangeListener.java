package server;

import java.util.EventListener;

public interface RegisteredParticipantChangeListener extends EventListener {
    void  dataChange(RegisteredParticipantChangeEvent e);
}
