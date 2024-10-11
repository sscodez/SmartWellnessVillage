import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.Response;
import org.eclipse.californium.core.CoapObserveRelation;

public class MixerClient {
    private CoapClient client;

    public MixerClient(String uri) {
        client = new CoapClient(uri);
    }

    public void observeChlorine() {
        CoapObserveRelation relation = client.observe(response -> {
            double chlorine = Double.parseDouble(response.getResponseText().split(" ")[2]);
            System.out.println("Current Chlorine Level: " + chlorine + " ppm");
            
            // If chlorine level drops below threshold, mix chlorine
            if (chlorine < 1.5) {
                System.out.println("Adding chlorine...");
                mixChlorine(0.5); // Increment by 0.5 ppm
            }
        });
    }

    public void mixChlorine(double increment) {
        client.post(String.valueOf(increment), CoAP.Type.CON);
    }

    public static void main(String[] args) {
        MixerClient mixerClient = new MixerClient("coap://localhost/chlorine1");
        mixerClient.observeChlorine();
    }
}
