import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.coap.Response;

public class ManagerClient {
    private CoapClient client;

    public ManagerClient(String uri) {
        client = new CoapClient(uri);
    }

    public void observeSystem() {
        Response response = client.get();
        if (response != null) {
            System.out.println(response.getResponseText());
        }
    }

    public void intervene(String resourceUri, double value) {
        client = new CoapClient(resourceUri);
        client.post(String.valueOf(value), 0);
    }

    public static void main(String[] args) {
        ManagerClient manager = new ManagerClient("coap://localhost/temp1");
        manager.observeSystem();

        // Intervene in temperature or chlorine level as needed
        manager.intervene("coap://localhost/temp1", 28.0);  // Drastically update temperature
    }
}
