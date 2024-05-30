package server;

import pupbic_interfase.Participant;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@XmlRootElement(name = "RegisteredParticipant")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "participants"
})
public class RegisteredParticipant implements Iterable<Participant>, Serializable {

    @XmlElement(name = "Conferee")
    private List<Participant> participants;

    @XmlTransient
    private List<RegisteredParticipantChangeListener> listeners = new ArrayList<>();

    @XmlTransient
    private RegisteredParticipantChangeEvent event = new RegisteredParticipantChangeEvent(this);

    public RegisteredParticipant() {
        participants = new ArrayList<>();
        fireDataSheetChange();
    }

    public RegisteredParticipant(List<Participant> participants) {
        this.participants = participants;
        fireDataSheetChange();
    }


    public void addDRegisteredParticipantChangeListener(RegisteredParticipantChangeListener l) {
        listeners.add(l);
    }

    public void removeRegisteredParticipantChangeListener(RegisteredParticipantChangeListener l) {
        listeners.remove(l);
    }

    protected void fireDataSheetChange() {
        for (RegisteredParticipantChangeListener listener : listeners) listener.dataChange(event);
    }

    public void add(Participant participant) {
        participants.add(participant);
        fireDataSheetChange();
    }

    public void remove(Participant participant) {
        participants.remove(participant);
        fireDataSheetChange();
    }

    public Participant get(int index) {
        return participants.get(index);
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
        fireDataSheetChange();
    }

    @Override
    public Iterator<Participant> iterator() {
        return participants.iterator();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        participants.forEach(e -> stringBuilder
                .append("======================")
                .append(System.lineSeparator())
                .append(e)
        );
        return stringBuilder.toString();
    }

    public int size(){
        return participants.size();
    }
}
