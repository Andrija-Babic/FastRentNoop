package model;

import controller.LoginController;
import dao.reservation.ReservationDAO;
import model.entity.Client;
import model.entity.Reservation;
import model.observer.ReservationStatusObserver;

import javax.swing.table.AbstractTableModel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides table data for displaying reservations in a Swing JTable.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class ReservationTableModel
        extends AbstractTableModel {

    private final String[] columns = {

            "Vozilo",
            "Korisnik",
            "Od",
            "Do",
            "Cijena",
            "Status"
    };

    private final Integer clientId;

    private final Runnable updateAction;

    private final String statusFilter;

    private List<Reservation> reservations =
            new ArrayList<>();

    /*
     * Stores which Observer belongs to which Reservation.
     *
     * This allows us to correctly remove old observers
     * before registering new ones.
     */
    private final Map<Reservation, ReservationStatusObserver>
            observers =
            new HashMap<>();

    // =====================================================
    // ADMIN
    // =====================================================

    /**
     * Creates a new ReservationTableModel instance.
     */
    public ReservationTableModel() {

        this.clientId = null;
        this.updateAction = null;
        this.statusFilter = null;

        refresh();
    }

    /**
     * Creates a new ReservationTableModel instance.
     *
     * @param updateAction supplied value used by this operation
     */
    public ReservationTableModel(
            Runnable updateAction) {

        this.clientId = null;
        this.updateAction = updateAction;
        this.statusFilter = null;

        refresh();
    }

    /**
     * Creates a new ReservationTableModel instance with a status filter.
     *
     * @param updateAction supplied value used by this operation
     * @param statusFilter reservation status that should be displayed
     */
    public ReservationTableModel(
            Runnable updateAction,
            String statusFilter) {

        this.clientId = null;
        this.updateAction = updateAction;
        this.statusFilter = statusFilter;

        refresh();
    }

    // =====================================================
    // USER
    // =====================================================

    /**
     * Creates a new ReservationTableModel instance.
     *
     * @param loggedUser supplied value used by this operation
     * @param updateAction supplied value used by this operation
     */
    public ReservationTableModel(
            String loggedUser,
            Runnable updateAction) {

        Client client =
                LoginController.getLoggedInClient();

        Integer id = null;

        if (client != null) {

            id = client.getId();
        }

        this.clientId = id;
        this.updateAction = updateAction;
        this.statusFilter = null;

        refresh();
    }

    // =====================================================
    // REFRESH
    // =====================================================

    /**
     * Refreshes the displayed or cached data.
     */
    public void refresh() {

        /*
         * Remove observers from the reservations currently
         * stored in this table model before loading fresh data.
         */
        removeObservers();

        try {

            ReservationDAO
                    .updateExpiredReservations();

            List<Reservation> allReservations;

            if (clientId == null) {

                allReservations =
                        ReservationDAO.findAll();

            } else {

                allReservations =
                        ReservationDAO.findByClient(
                                clientId
                        );
            }

            if (statusFilter == null) {

                reservations =
                        allReservations;

            } else {

                reservations =
                        allReservations.stream()
                                .filter(reservation ->
                                        statusFilter.equals(
                                                reservation.getStatus()
                                        )
                                )
                                .toList();
            }

            registerObservers();

        } catch (SQLException e) {

            e.printStackTrace();

            reservations =
                    new ArrayList<>();
        }

        fireTableDataChanged();
    }

    // =====================================================
    // OBSERVER REGISTRATION
    // =====================================================

    private void registerObservers() {

        /*
         * If there is no UI refresh action,
         * there is nothing for the Observer to update.
         */
        if (updateAction == null) {
            return;
        }

        for (Reservation reservation : reservations) {

            ReservationStatusObserver observer =
                    new ReservationStatusObserver(
                            updateAction
                    );

            reservation.addObserver(observer);

            observers.put(
                    reservation,
                    observer
            );
        }
    }

    // =====================================================
    // OBSERVER CLEANUP
    // =====================================================

    private void removeObservers() {

        for (Map.Entry<Reservation, ReservationStatusObserver> entry :
                observers.entrySet()) {

            Reservation reservation =
                    entry.getKey();

            ReservationStatusObserver observer =
                    entry.getValue();

            reservation.removeObserver(observer);
        }

        observers.clear();
    }

    // =====================================================
    // TABLE METHODS
    // =====================================================

    @Override
    public int getRowCount() {

        return reservations.size();
    }

    @Override
    public int getColumnCount() {

        return columns.length;
    }

    @Override
    public String getColumnName(int column) {

        return columns[column];
    }

    @Override
    public Object getValueAt(
            int rowIndex,
            int columnIndex) {

        Reservation reservation =
                reservations.get(rowIndex);

        switch (columnIndex) {

            case 0:
                return reservation
                        .getVehicle()
                        .getFullName();

            case 1:
                return reservation
                        .getClient()
                        .getFullName();

            case 2:
                return reservation.getFrom();

            case 3:
                return reservation.getTo();

            case 4:
                return reservation.getTotalPrice()
                        + " €";

            case 5:
                return reservation.getStatus();

            default:
                return "";
        }
    }

    // =====================================================
    // GET RESERVATION
    // =====================================================

    /**
     * Returns the reservation at the specified row.
     *
     * @param rowIndex supplied value used by this operation
     * @return reservation at the specified row
     */
    public Reservation getReservationAt(
            int rowIndex) {

        return reservations.get(rowIndex);
    }
}