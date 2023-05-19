package tech.softwarekitchen.common.matrix

import tech.softwarekitchen.common.vector.Vector2
import kotlin.math.cos
import kotlin.math.sin

/**
 * Usage:
 * / xx yx \
 * \ xy yy /
 */
class Matrix22(val xx: Double, val yx: Double, val xy: Double, val yy: Double){
    companion object{
        fun getRotationMatrix(rad: Double): Matrix22{
            return Matrix22(cos(rad), sin(rad), -sin(rad), cos(rad))
        }
        fun getScaleMatrix(xFac: Double, yFac: Double): Matrix22{
            return Matrix22(xFac,0.0,0.0,yFac)
        }
    }
    fun mul(vec: Vector2): Vector2 {
        return Vector2(vec.x * xx + vec.y * yx, vec.x * xy + vec.y * yy)
    }

    fun scale(fac: Double): Matrix22{
        return Matrix22(xx * fac, yx * fac, xy * fac, yy * fac)
    }

    fun invert(): Matrix22{
        return Matrix22(yy,-yx,-xy,xx).scale(1 / (xx*yy - xy*yx))
    }

}
