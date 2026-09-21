package dao.client;

import model.entity.Client;
import model.strategy.PricingType;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Maps database records to client domain objects.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class ClientMapper {

    /**
     * Performs the fromresultset operation.
     *
     * @param rs supplied value used by this operation
     *
     * @return the value produced by this operation
     */
    public static Client fromResultSet(
            ResultSet rs) throws SQLException {

        Client client = new Client(
                rs.getInt("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getInt("premium") == 1
        );

        String pricingType = rs.getString("pricing_type");

        if (pricingType != null && !pricingType.isBlank()) {
            client.setPricingType(
                    PricingType.valueOf(pricingType)
            );
        }

        client.setUsername(rs.getString("username"));
        client.setPasswordHash(rs.getString("password_hash"));

        return client;
    }
}