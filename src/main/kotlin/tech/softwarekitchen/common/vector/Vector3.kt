package tech.softwarekitchen.common.vector

class Vector3(val x: Double, val y: Double, val z: Double) {
    fun length(): Double{
        return Math.sqrt(x*x+y*y+z*z)
    }
    fun norm(): Vector3 {
        val l = length()
        return Vector3(x/l,y/l,z/l)
    }
    fun ortho(other: Vector3): Vector3 {
        return Vector3(
            y*other.z - z*other.y,
            z*other.x - x*other.z,
            x*other.y - y*other.x
        )
    }
    fun plus(other: Vector3): Vector3 {
        return Vector3(x+other.x,y+other.y,z+other.z)
    }
    fun scale(factor: Double): Vector3 {
        return Vector3(x*factor, y*factor, z*factor)
    }
    fun minus(other: Vector3): Vector3 {
        return Vector3(x-other.x,y-other.y,z-other.z)
    }
    fun scalar(other: Vector3): Double{
        return x*other.x+y*other.y+z*other.z
    }
    fun invert(): Vector3 {
        return scale(-1.0)
    }
    fun dist(other: Vector3): Double{
        return minus(other).length()
    }
    fun squared(): Double{
        return x*x+y*y+z*z
    }

    override fun toString(): String {
        return "$x,$y,$z"
    }
}
