package devut.buzzerbidder.domain.deal.enums;

public enum AuctionType {
    LIVE,
    DELAYED;

    public static AuctionType fromString(String value) {
        for (AuctionType t : values()) {
            if (t.name().equalsIgnoreCase(value)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Unknown AuctionType: " + value);
    }
}
