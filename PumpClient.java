import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.Response;
import org.eclipse.californium.core.CoapObserveRelation;

public class PumpClient {
    private CoapClient client;

    public PumpClient(String uri) {
        client = new CoapClient(uri);
    }

    public void observeTemperature() {
        CoapObserveRelation relation = client.observe(response -> {
            double temp = Double.parseDouble(response.getResponseText().split(" ")[1]);
            System.out.println("Current Temperature: " + temp + " °C");
            
            // If temperature drops below threshold, heat the pool
            if (temp < 24.0) {
                System.out.println("Heating the pool...");
                heatPool(1.0); // Increment by 1.0°C
            }
        });
    }

    public void heatPool(double increment) {
        client.post(String.valueOf(increment), CoAP.Type.CON);
    }

    public static void main(String[] args) {
        PumpClient pumpClient = new PumpClient("coap://localhost/temp1");
        pumpClient.observeTemperature();
    }
}
