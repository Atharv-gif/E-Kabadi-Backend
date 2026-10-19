
package ekabadi_backend.dto;

public class NearbyCollectorResponse {

    private int rank;
    private String collectorId;
    private String userId;
    private double distanceKm;
    private Double latitude;
    private Double longitude;
    private boolean online;
    private boolean available;

    public NearbyCollectorResponse(
            int rank,
            String collectorId,
            String userId,
            double distanceKm,
            Double latitude,
            Double longitude,
            boolean online,
            boolean available) {

        this.rank = rank;
        this.collectorId = collectorId;
        this.userId = userId;
        this.distanceKm = distanceKm;
        this.latitude = latitude;
        this.longitude = longitude;
        this.online = online;
        this.available = available;
    }

    public int getRank() {
        return rank;
    }

    public String getCollectorId() {
        return collectorId;
    }

    public String getUserId() {
        return userId;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public boolean isOnline() {
        return online;
    }

    public boolean isAvailable() {
        return available;
    }
}
