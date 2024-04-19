package net.wilux.util;

import org.joml.Matrix4f;

public class Mtx {
    private static float sin(double degrees) {
        return (float)Math.sin(Math.toRadians(degrees));
    }
    private static float cos(double degrees) {
        return (float)Math.cos(Math.toRadians(degrees));
    }

    public static Matrix4f matrixProduct(Matrix4f... mtxs) {
        Matrix4f result = new Matrix4f();
        for (var mtx: mtxs) {
            result.mul(mtx);
        }
        return result;
    }

    public static Matrix4f rotY(double degrees) {
        var RY = degrees;
        return new Matrix4f(
                cos(RY), 0,  sin(RY), 0,
                0,       1,  0,       0,
                -sin(RY), 0,  cos(RY), 0,
                0,       0,  0,       1
        ).transpose();
    }

    public static Matrix4f rotX(double degrees) {
        var RX = degrees;
        return new Matrix4f(
                1, 0,        0,       0,
                0, cos(RX), -sin(RX), 0,
                0, sin(RX),  cos(RX), 0,
                0, 0,        0,       1
        ).transpose();
    }

    public static Matrix4f flattenZ() {
        return new Matrix4f(
                1, 0,  0, 0,
                0, 1,  0, 0,
                0, 0,  0, 0,
                0, 0,  0, 1
        ).transpose();
    }

    public static Matrix4f translate(double x, double y, double z) {
        return new Matrix4f(
                1, 0,  0, (float)x,
                0, 1,  0, (float)y,
                0, 0,  1, (float)z,
                0, 0,  0, 1
        ).transpose();
    }

    public static Matrix4f scale(double amount) {
        float a = (float)amount;
        return new Matrix4f(
                a, 0,  0, 0,
                0, a,  0, 0,
                0, 0,  a, 0,
                0, 0,  0, 1
        ).transpose();
    }
}
