package management;

import model.ElectricFillingStationNetwork;
import model.Location;
import model.NetworkStatus;
import model.OperatingStatus;

public class NetworkStatusManagement {

    private final ElectricFillingStationNetwork network;

    public NetworkStatusManagement(ElectricFillingStationNetwork network) {
        this.network = network;
    }

    /**
     * - OPERATIONAL: no station is OUT_OF_ORDER
     * - DEGRADED: at least one station is OUT_OF_ORDER, but not all
     * - DOWN: all stations are OUT_OF_ORDER
     */
    public NetworkStatus getNetworkStatus() {
        int totalStations = 0;
        int outOfOrderStations = 0;

        for (Location loc : network.getLocations()) {
            totalStations += loc.getChargingStations().size();
            outOfOrderStations += (int) loc.getChargingStations().stream()
                    .filter(s -> s.getStatus() == OperatingStatus.OUT_OF_ORDER)
                    .count();
        }

        if (totalStations == 0) return NetworkStatus.OPERATIONAL;
        if (outOfOrderStations == 0) return NetworkStatus.OPERATIONAL;
        if (outOfOrderStations == totalStations) return NetworkStatus.DOWN;
        return NetworkStatus.DEGRADED;
    }

    public String getNetworkStatusAsString() {
        return getNetworkStatus().name();
    }
}
