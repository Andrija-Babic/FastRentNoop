package dao.reservation;

import model.entity.Reservation;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Maintains in-memory references to reservations used by status notifications.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class ReservationRegistry {

    private static final Map<Integer, Reservation>
            registry = new ConcurrentHashMap<>();

    /**
     * Performs the put operation.
     *
     * @param id supplied value used by this operation
     * @param reservation supplied value used by this operation
    */
    public static void put(
            int id,
            Reservation reservation) {

        registry.put(id, reservation);
    }

    /**
     * Returns the .
     *
     * @param id supplied value used by this operation
     *
     * @return the value produced by this operation
    */
    public static Reservation get(int id) {
        return registry.get(id);
    }
}
