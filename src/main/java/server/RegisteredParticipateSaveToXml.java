package server;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class RegisteredParticipateSaveToXml {

    public static void marshalDataToXML(String fileName, RegisteredParticipant registeredParticipant) throws JAXBException {
        Marshaller marshaller = JAXBContext.newInstance(RegisteredParticipant.class).createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(registeredParticipant, new File(fileName));

    }

    public static RegisteredParticipant unmarshalOutXMLs(String filePath) throws JAXBException {
        Unmarshaller unmarshaller = JAXBContext.newInstance(RegisteredParticipant.class).createUnmarshaller();
        Object element = unmarshaller.unmarshal(new File(filePath));
        if (element instanceof RegisteredParticipant) {
            return (RegisteredParticipant) element;
        } else throw new JAXBException("is not RegisteredParticipant, check your filePath!!");
    }
}
