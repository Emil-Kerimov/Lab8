package pupbic_interfase;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ClientMethods extends Remote {

    void register(Participant o) throws RemoteException;
    List<Participant> getAllClient() throws RemoteException;
}
