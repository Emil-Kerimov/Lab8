package server;

import pupbic_interfase.ClientMethods;
import pupbic_interfase.Participant;

import java.util.List;

public class ClientMethodsImpl implements ClientMethods {
    private RegisteredParticipant participants;

    public ClientMethodsImpl() {
        participants = new RegisteredParticipant();
    }

    @Override
    public synchronized void register(Participant o) {
        participants.add(o);
    }

    @Override
    public synchronized List<Participant> getAllClient() {
        return participants.getParticipants();
    }

    public RegisteredParticipant getParticipants() {
        return participants;
    }

    public void setParticipants(RegisteredParticipant participants) {
        this.participants.setParticipants(participants.getParticipants());
    }
}
