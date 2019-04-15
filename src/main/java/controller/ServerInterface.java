package controller;

import java.rmi.*;

/*Notes:
  stub: "client side gateway for client side objects all outgoing requests to server
         side objects"
  skeleton: "gateway for server side objects and all incoming clients requests are routed
             through it"
  The stub initiates the RMI and marshalls parameters, while the skeleton receives said call and
  unmarshalls the parameters. The skeleton invokes the method proper, then marshalls the return
  value, and finally the stub unmarshalls the skeleton's response.
  The RMI registry provides the client with the necessary stub.
 */

public interface ServerInterface extends Remote {

    //String getSomeMessage();
    String echo(String input) throws RemoteException;
}
