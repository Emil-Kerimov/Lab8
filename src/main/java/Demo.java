import pupbic_interfase.Participant;
import server.RegisteredParticipant;
import server.RegisteredParticipateSaveToXml;

import javax.xml.bind.JAXBException;

public class Demo {

    public static void main(String[] args) throws JAXBException {
        RegisteredParticipant participant = new RegisteredParticipant();
        participant.addDRegisteredParticipantChangeListener(e->{
            System.out.println("Зміна");
        });
        participant.add(new Participant("Name1","test1","org1","rep1","name1@gmail.com"));
        participant.add(new Participant("Name2","test2","org2","rep2","name2@gmail.com"));
        participant.add(new Participant("Name3","test3","org3","rep3","name3@gmail.com"));
        participant.add(new Participant("Name4","test4","org4","rep4","name4@gmail.com"));
        participant.add(new Participant("Name5","test5","org5","rep5","name5@gmail.com"));


        RegisteredParticipateSaveToXml.marshalDataToXML("data.xml",participant);
        System.out.println(RegisteredParticipateSaveToXml.unmarshalOutXMLs("data.xml"));
    }
}
