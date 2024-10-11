import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.californium.core.CoapResource;

public class TurnstileServer extends CoapServer {
    private int peopleInside = 0;

    public TurnstileServer() {
        add(new TurnstileResource("entrance"));
        add(new TurnstileResource("exit"));
    }

    private class TurnstileResource extends CoapResource {
        private String type;

        public TurnstileResource(String type) {
            super(type);
            this.type = type;
        }

        @Override
        public void handlePOST(CoapExchange exchange) {
            String visitorId = exchange.getRequestText();
            if (type.equals("entrance")) {
                peopleInside++;
                exchange.respond("Welcome, visitor " + visitorId + ". People inside: " + peopleInside);
            } else {
                if (peopleInside > 0) {
                    peopleInside--;
                    exchange.respond("Goodbye, visitor " + visitorId + ". People inside: " + peopleInside);
                } else {
                    exchange.respond("No people inside to exit.");
                }
            }
        }

        @Override
        public void handleGET(CoapExchange exchange) {
            exchange.respond("People inside: " + peopleInside);
        }
    }

    public static void main(String[] args) {
        TurnstileServer server = new TurnstileServer();
        server.start();
        System.out.println("Turnstile Server is running...");
    }
}
