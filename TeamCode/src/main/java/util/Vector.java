package util;

import androidx.annotation.NonNull;


import paths.geometry.BSpline;

/**
 * @author Atharv G. - 13085 Bionic Dutch
 */

public class Vector {
    Distance magnitude;
    Angle theta;
    Distance.Units distUnit;
    Angle.Units angleUnit;

    // region Constructors and factory methods
    
    /**
     * Constructor for the {@link Vector} class
     * @param x the x component of the vector
     * @param y the y component of the vector
     * @param translationalUnit the input and output unit
     */
    public Vector(double x, double y, Distance.Units translationalUnit) {
        setVals(x, y, translationalUnit);
    }

    /**
     * Constructor for the {@link Vector} class with the default unit of inches
     * @param x the x component of the vector
     * @param y the y component of the vector
     */
    public Vector(double x, double y) {

        this(x, y, Distance.Units.INCHES);
    }

    /**
     * Constructor for {@link Vector} class with {@link Distance} objects
     * @param x the x {@link Distance} of the vector
     * @param y the y {@link Distance} of the vector
     */
    public Vector(Distance x, Distance y) { 
        this(x.getIn(), y.getIn());
    }

    /**
     * Default constructor for {@link Vector} class, initializes to (0, 0) in inches
     */
    public Vector() { 
        this(new Distance(), new Distance()); 
    }

    /**
     * Factory method to create a {@link Vector} from a {@link Pose}
     * @param pose The Pose to extract vector coordinates from
     * @return A new Vector representing the Pose's position
     */
    public static Vector fromPose(Pose pose) {
        return new Vector(pose.getXComponent().getIn(), pose.getYComponent().getIn());
    }
    
    // endregion

    // region Getters
    
    /** @return the x component as a double in the current unit */
    public double getX() { return this.magnitude.get(distUnit) * Math.cos(theta.get(angleUnit)); }
    
    /** @return the y component as a double in the current unit */
    public double getY() { return this.magnitude.get(distUnit) * Math.sin(theta.get(angleUnit)); }

    /** @return the x component as a {@link Distance} object */
    public Distance getXComponent() { return this.magnitude.multiply(Math.cos(theta.get(angleUnit))); }
    
    /** @return the y component as a {@link Distance} object */
    public Distance getYComponent() { return this.magnitude.multiply(Math.sin(theta.get(angleUnit))); }

    /** @return the current unit of measurement for this vector */
    public Distance.Units getUnit() { return this.distUnit; }
    
    // endregion

    // region Setters
    
    /** * Sets the x component of the vector 
     * @param x the new x value in the current unit
     */
    public void setX(double x) { setVals(x, this.getY(), distUnit); }
    
    /** * Sets the y component of the vector 
     * @param y the new y value in the current unit
     */
    public void setY(double y) { setVals(this.getX(), y, distUnit); }

    /** * Sets the unit of measurement for this vector 
     * @param dist  The desired distance unit of the vector
     * @param angle The desired angular unit of the vector
     */
    public void setUnits(Distance.Units dist, Angle.Units angle) { setVals(magnitude.get(distUnit), theta.get(angleUnit), dist, angle); }

    public void setVals(double x, double y, Distance.Units unit) {
        this.magnitude = Distance.from(distUnit, Math.hypot(x, y));
        this.theta = Angle.from(angleUnit, Math.atan2(y,x));
        this.distUnit = unit;
    }

    public void setVals(double magnitude, double theta, Distance.Units distUnit, Angle.Units angleUnit) {
        this.magnitude = Distance.from(distUnit, magnitude);
        this.distUnit = distUnit;
        this.theta = Angle.from(angleUnit, theta);
    }
    
    // endregion

    // region Arithmetic methods
    
    /**
     * Gets the magnitude (length) of the vector
     * @return the magnitude in the current unit
     */
    public double getMagnitude() {
        return Math.hypot(getX(), getY());
    }

    /**
     * Gets the squared magnitude of the vector
     * @return the squared magnitude
     */
    public double getMagnitudeSquared() { 
        double currentX = getX();
        double currentY = getY();
        return (currentX * currentX) + (currentY * currentY); 
    }

    /**
     * Sets the magnitude of the vector while maintaining its current direction
     * @param value the new magnitude
     */
    public void setMagnitude(double value) {
        double ang = getTheta();
        setX(value * Math.cos(ang));
        setY(value * Math.sin(ang));
    }

    /**
     * Gets the angle (theta) of the vector in radians
     * @return the angle in radians from -PI to PI
     */
    public double getTheta() {
        return Math.atan2(getY(), getX());
    }

    /**
     * Sets the angle of the vector while maintaining its current magnitude
     * @param value the new angle in radians
     */
    public void setTheta(double value) {
        theta = new Angle(value);
    }

    /**
     * Calculates the dot product of this vector and another vector
     * @param other the other vector
     * @return the dot product
     */
    public double dotProduct(Vector other) {
        return (this.getX() * other.getXComponent().get(this.distUnit)) + (this.getY() * other.getYComponent().get(this.distUnit));
    }

    /**
     * Calculates the 2D cross product scalar of this vector and another vector
     * @param other the other vector
     * @return the magnitude of the cross product
     */
    public double crossProduct(Vector other) {
        return (this.getX() * other.getYComponent().get(this.distUnit)) - (this.getY() * other.getXComponent().get(this.distUnit));
    }

    /**
     * Rotates the vector IN PLACE by a given angle in radians.
     * @param angle The angle to rotate the vector by, in radians.
     */
    public void rotate(double angle) {
        double currentX = getX();
        double currentY = getY();
        double cosA = Math.cos(angle);
        double sinA = Math.sin(angle);

        setX((currentX * cosA) - (currentY * sinA));
        setY((currentX * sinA) + (currentY * cosA));
    }

    /**
     * Returns a NEW vector rotated by a given angle in radians.
     * @param angle The angle to rotate by, in radians.
     * @return A new rotated Vector
     */
    public Vector rotated(double angle) {
        Vector rotated = this.copy();
        rotated.rotate(normalizeAngle(angle));
        return rotated;
    }

    /**
     * Normalizes the vector to a magnitude of 1.
     * @return A new normalized Vector
     */
    public Vector normalize() {
        double mag = getMagnitude();
        if (mag > 1e-9) {
            return this.div(mag);
        }
        return new Vector(0.0, 0.0, this.distUnit);
    }

    /**
     * Adds another vector to this vector.
     * @param other the vector to add
     * @return A new Vector representing the sum
     */
    public Vector add(Vector other) {
        return new Vector(this.getX() + other.getXComponent().get(this.distUnit), this.getY() + other.getYComponent().get(this.distUnit), this.distUnit);
    }

    /**
     * Subtracts another vector from this vector.
     * @param other the vector to subtract
     * @return A new Vector representing the difference
     */
    public Vector subtract(Vector other) {
        return new Vector(this.getX() - other.getXComponent().get(this.distUnit), this.getY() - other.getYComponent().get(this.distUnit), this.distUnit);
    }

    /**
     * Multiplies this vector by a scalar.
     * @param scalar the value to multiply by
     * @return A new scaled Vector
     */
    public Vector multiply(double scalar) {
        return new Vector(getX() * scalar, getY() * scalar, this.distUnit);
    }

    /**
     * Divides this vector by a scalar.
     * @param scalar the value to divide by
     * @return A new scaled Vector
     */
    public Vector div(double scalar) {
        return new Vector(getX() / scalar, getY() / scalar, this.distUnit);
    }

    /**
     * Negates this vector.
     * @return A new Vector pointing in the opposite direction
     */
    public Vector negate() {
        return multiply(-1.0);
    }
    
    // endregion

    // region Utility methods

    /**
     * @param across The vector which the current vector will be reflected across
     * @return The reflected vector
     * @see BSpline
     */
    public Vector reflect(Vector across) {
        return across.add(this.subtract(across).multiply(-1.0));
    }
    
    /**
     * Converts the vector to a Pose object.
     * @return A Pose representation of the vector
     */
    public Pose asPose() {
        return new Pose(getX(), getY(), getTheta(), Distance.Units.INCHES, Angle.Units.RADIANS, false);
    }
    
    /** * Returns a copy of this {@link Vector} 
     * @return a completely new Vector with the same properties
     */
    public Vector copy() { 
        return new Vector(this.getX(), this.getY(), this.distUnit);
    }

    /**
     * Normalizes an angle to be within -PI and PI bounds
     * @param angle The angle to normalize
     * @return The normalized angle
     */
    private static double normalizeAngle(double angle) {
        while (angle > Math.PI) angle -= 2 * Math.PI;
        while (angle < -Math.PI) angle += 2 * Math.PI;
        return angle;
    }

    @Override
    @NonNull
    public String toString() { 
        return String.format("Vector(x: %.3f %s, y: %.3f %s)", getX(), getY(), distUnit.name().toLowerCase(), getY());
    }

    /**
     * Returns a detailed string representation for debugging
     * @return Detailed debug string
     */
    public String debug() {
        return String.format("Vector <x: %.3f, y: %.3f>, <magnitude: %.3f, θ: %.3f>", 
                getX(), getY(), getMagnitude(), getTheta());
    }
    
    // endregion
}