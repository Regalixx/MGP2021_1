package com.sdm.mgp2021_1;

public class Vector3 {
    public float x,y,z;

    Vector3(float _x, float _y, float _z) {
        x = _x;
        y = _y;
        z = _z;
    }

    Vector3(Vector3 vector) {
        x = vector.x;
        y = vector.y;
        z = vector.z;
    }


    //adds to vector
    public Vector3 Add(Vector3 vector) {
        this.x += vector.x;
        this.y += vector.y;
        this.z += vector.z;
        return this;
    }

    //gives result of addition
    public Vector3 Plus(Vector3 rhs) {
        return new Vector3(
                x + rhs.x,
                y + rhs.y,
                z + rhs.z
        );
    }

    //subtracts vector from vector
    public Vector3 Subtract(Vector3 vector) {
        this.x -= vector.x;
        this.y -= vector.y;
        this.z -= vector.z;
        return this;
    }


    //gives result of subtraction
    public Vector3 Minus(Vector3 rhs) {
        return new Vector3(
                x - rhs.x,
                y - rhs.y,
                z - rhs.z
        );
    }

    // multiplies the vector
    public Vector3 Multiply(float magnitude) {
        this.x *= magnitude;
        this.y *= magnitude;
        this.z *= magnitude;
        return this;
    }

    // returns value of multiplication
    public Vector3 Times(float rhs) {
        return new Vector3(x * rhs, y * rhs, z * rhs);
    }

    public float Dot(Vector3 vector) {
        return x*vector.x + y * vector.y + z * vector.z;
    }

    public Vector3 Cross(Vector3 vector) {
        return new Vector3(
                y * vector.z + vector.y * z,
                z * vector.x + vector.z * x,
                x * vector.y + vector.x * y
        );
    }

    public float LengthSquared() {
        return x*x+y*y+z*z;
    }

    public float Length() {
        return (float)Math.sqrt((double)LengthSquared());
    }

    //returns a normalized vector of this vector
    public Vector3 Normalized() {

        double EPSILON = 0.000001d;
        float d = Length();
        if (d <= EPSILON && -d <= EPSILON) {
            throw new ArithmeticException("Divide by zero! (Normalized zero vector)");
        }

        return new Vector3 ( x / d, y / d, z / d);
    }

}
