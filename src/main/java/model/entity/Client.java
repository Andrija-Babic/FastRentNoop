package model.entity;

/**
 * Represents a FastRent client and the client's account information.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class Client {

    private int id;
    private String firstName;
    private String lastName;
    private boolean premium;

    private String username;
    private String passwordHash;

    /**
     * Creates a new Client instance.
     *
     * @param id supplied value used by this operation
     * @param firstName supplied value used by this operation
     * @param lastName supplied value used by this operation
     * @param premium supplied value used by this operation
    */
    public Client(
            int id,
            String firstName,
            String lastName,
            boolean premium) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.premium = premium;
    }

    // =====================================================
    // GETTERS
    // =====================================================

    /**
     * Returns the id.
     *
     * @return the value produced by this operation
    */
    public int getId() {
        return id;
    }

    /**
     * Returns the firstname.
     *
     * @return the value produced by this operation
    */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the lastname.
     *
     * @return the value produced by this operation
    */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns whether premium.
     *
     * @return the value produced by this operation
    */
    public boolean isPremium() {
        return premium;
    }

    /**
     * Returns the fullname.
     *
     * @return the value produced by this operation
    */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * Returns the username.
     *
     * @return the value produced by this operation
    */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the passwordhash.
     *
     * @return the value produced by this operation
    */
    public String getPasswordHash() {
        return passwordHash;
    }

    // =====================================================
    // SETTERS
    // =====================================================

    /**
     * Sets the id.
     *
     * @param id supplied value used by this operation
    */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the firstname.
     *
     * @param firstName supplied value used by this operation
    */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets the lastname.
     *
     * @param lastName supplied value used by this operation
    */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Sets the premium.
     *
     * @param premium supplied value used by this operation
    */
    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    /**
     * Sets the username.
     *
     * @param username supplied value used by this operation
    */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the passwordhash.
     *
     * @param passwordHash supplied value used by this operation
    */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
