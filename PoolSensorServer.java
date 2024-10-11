import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.californium.core.CoapResource;

public class PoolSensorServer extends CoapServer {
    public PoolSensorServer() {
        // Add the temperature and chlorine sensors as resources
        add(new TemperatureSensor("temp1", 25.0));
        add(new ChlorineSensor("chlorine1", 2.0));
    }

    private static class TemperatureSensor extends CoapResource {
        private double temperature;

        public TemperatureSensor(String name, double initialTemp) {
            super(name);
            this.temperature = initialTemp;
        }

        @Override
        public void handleGET(CoapExchange exchange) {
            exchange.respond("Temperature: " + temperature + " °C");
        }

        @Override
        public void handlePOST(CoapExchange exchange) {
            try {
                // Increment temperature from the client (Heating Pump)
                double newTemp = Double.parseDouble(exchange.getRequestText());
                temperature += newTemp;
                exchange.respond("Temperature updated to: " + temperature + " °C");
            } catch (NumberFormatException e) {
                exchange.respond("Invalid temperature value");
            }
        }

        public double getTemperature() {
            return temperature;
        }
    }

    private static class ChlorineSensor extends CoapResource {
        private double chlorineLevel;

        public ChlorineSensor(String name, double initialLevel) {
            super(name);
            this.chlorineLevel = initialLevel;
        }

        @Override
        public void handleGET(CoapExchange exchange) {
            exchange.respond("Chlorine Level: " + chlorineLevel + " ppm");
        }

        @Override
        public void handlePOST(CoapExchange exchange) {
            try {
                // Increment chlorine concentration from the client (Chlorine Mixer)
                double newLevel = Double.parseDouble(exchange.getRequestText());
                chlorineLevel += newLevel;
                exchange.respond("Chlorine level updated to: " + chlorineLevel + " ppm");
            } catch (NumberFormatException e) {
                exchange.respond("Invalid chlorine value");
            }
        }

        public double getChlorineLevel() {
            return chlorineLevel;
        }
    }

    public static void main(String[] args) {
        PoolSensorServer server = new PoolSensorServer();
        server.start();
        System.out.println("Pool Sensor Server is running...");
    }
}
