package model;

import dao.vehicle.VehicleDAO;
import model.entity.Vehicle;
import model.observer.Observer;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Provides table data for displaying vehicles in a Swing JTable.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class VehicleTableModel extends AbstractTableModel {

    private final String[] columns = {
            "ID",
            "Marka i model",
            "Godina",
            "Cijena po danu",
            "Status"
    };

    private final boolean onlyAvailable;
    private final Observer observer;

    private List<Vehicle> vehicles = new ArrayList<>();

    /**
     * Creates a new VehicleTableModel instance.
     *
     * @param onlyAvailable supplied value used by this operation
     * @param observer supplied value used by this operation
    */
    public VehicleTableModel(
            boolean onlyAvailable,
            Observer observer) {

        this.onlyAvailable = onlyAvailable;
        this.observer = observer;

        refresh();
    }

    /**
     * Ponovno učitava vozila iz baze.
     */
    public void refresh() {

        /*
         * Prvo usklađujemo statuse vozila
         * prema rezervacijama aktivnim danas.
         */
        VehicleDAO.updateCurrentVehicleStatuses();

        List<Vehicle> allVehicles =
                VehicleDAO.findAll();

        /*
         * Prije učitavanja novih vozila
         * uklanjamo ovog Observera sa vozila
         * koja su bila registrirana u prethodnom refreshu.
         */
        unregisterObservers();

        if (onlyAvailable) {

            vehicles = allVehicles.stream()
                    .filter(vehicle ->
                            vehicle.getStatus() != null
                                    && vehicle.getStatus()
                                    .equalsIgnoreCase("Dostupno"))
                    .collect(Collectors.toList());

        } else {

            vehicles = allVehicles;
        }

        /*
         * Registriramo Observer samo na trenutno
         * učitana vozila.
         */
        registerObservers();

        fireTableDataChanged();
    }

    /**
     * Uklanja Observer sa prethodno učitanih vozila.
     *
     * Ovo sprječava višestruku registraciju istog
     * Observera nakon više refresh() poziva.
     */
    private void unregisterObservers() {

        if (observer == null) {
            return;
        }

        for (Vehicle vehicle : vehicles) {
            vehicle.removeObserver(observer);
        }
    }

    /**
     * VehiclesPanel je Observer i registrira se
     * direktno na svako trenutno učitano vozilo.
     */
    private void registerObservers() {

        if (observer == null) {
            return;
        }

        for (Vehicle vehicle : vehicles) {
            vehicle.addObserver(observer);
        }
    }

    @Override
    public int getRowCount() {
        return vehicles.size();
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

        Vehicle vehicle =
                vehicles.get(rowIndex);

        switch (columnIndex) {

            case 0:
                return vehicle.getId();

            case 1:
                return vehicle.getFullName();

            case 2:
                return vehicle.getYear();

            case 3:
                return vehicle.getPricePerDay() + " €";

            case 4:
                return vehicle.getStatus();

            default:
                return null;
        }
    }

    /**
     * Dohvaća vozilo iz određenog retka modela.
     */
    public Vehicle getVehicleAt(int rowIndex) {
        return vehicles.get(rowIndex);
    }
}
