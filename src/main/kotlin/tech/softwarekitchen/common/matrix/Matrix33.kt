package tech.softwarekitchen.common.matrix

import jdk.dynalink.Operation
import tech.softwarekitchen.common.vector.Vector3

/*
    / x1 x2 x3 \
    | y1 y2 y3 |
    \ z1 z2 z3 /
 */

class Matrix33(val x1: Double, val x2: Double, val x3: Double, val y1: Double, val y2: Double, val y3: Double, val z1: Double, val z2: Double, val z3: Double) {
    companion object{
        fun getOne(): Matrix33{
            return Matrix33(1.0,0.0,0.0, 0.0,1.0,0.0, 0.0,0.0,1.0)
        }

        fun fromColumnVectors(v1: Vector3, v2: Vector3, v3: Vector3): Matrix33{
            return Matrix33(v1.x, v2.x, v3.x, v1.y, v2.y, v3.y, v1.z, v2.z, v3.z)
        }

        fun fromColumnVectors(v: Array<Vector3>): Matrix33{
            if(v.size != 3){
                throw Exception()
            }
            return fromColumnVectors(v[0], v[1], v[2])
        }

        fun fromLineVectors(v1: Vector3, v2: Vector3, v3: Vector3): Matrix33{
            return Matrix33(v1.x, v1.y, v1.z, v2.x, v2.y, v2.z, v3.x, v3.y, v3.z)
        }

        fun fromLineVectors(v: Array<Vector3>): Matrix33{
            if(v.size != 3){
                throw Exception()
            }
            return fromLineVectors(v[0], v[1], v[2])
        }
    }

    fun getLineVectors(): Array<Vector3>{
        return arrayOf(
            Vector3(x1,x2,x3),
            Vector3(y1,y2,y3),
            Vector3(z1,z2,z3)
        )
    }

    fun getColumnVectors(): Array<Vector3>{
        return arrayOf(
            Vector3(x1,y1,z1),
            Vector3(x2,y2,z2),
            Vector3(x3,y3,z3)
        )
    }

    fun invert(): Matrix33{
        return Matrix33Inverter().invert(this)
    }

    fun mul(vec: Vector3): Vector3{
        return Vector3(
            x1 * vec.x + x2 * vec.y + x3 * vec.z,
            y1 * vec.x + y2 * vec.y + y3 * vec.z,
            z1 * vec.x + z2 * vec.y + z3 * vec.z
        )
    }

    fun mul(other: Matrix33): Matrix33{
        return Matrix33(
            x1 * other.x1 + x2 * other.y1 + x3 * other.z1,
            x1 * other.x2 + x2 * other.y2 + x3 * other.z2,
            x1 * other.x3 + x2 * other.y3 + x3 * other.z3,
            y1 * other.x1 + y2 * other.y1 + y3 * other.z1,
            y1 * other.x2 + y2 * other.y2 + y3 * other.z2,
            y1 * other.x3 + y2 * other.y3 + y3 * other.z3,
            z1 * other.x1 + z2 * other.y1 + z3 * other.z1,
            z1 * other.x2 + z2 * other.y2 + z3 * other.z2,
            z1 * other.x3 + z2 * other.y3 + z3 * other.z3
        )
    }
}

class Matrix33Operations{
    companion object{
        fun scaleLine(matrix: Matrix33, index: Int, factor: Double): Matrix33{
            val lv = matrix.getLineVectors()
            lv[index] = lv[index].scale(factor)
            return Matrix33.fromLineVectors(lv)
        }

        fun lineSubtract(matrix: Matrix33, subtractFrom: Int, toSubtract: Int, factor: Double): Matrix33{
            val lineVectors = matrix.getLineVectors()
            lineVectors[subtractFrom] = lineVectors[subtractFrom].minus(lineVectors[toSubtract].scale(factor))
            return Matrix33.fromLineVectors(lineVectors)
        }

        fun shuffle(matrix: Matrix33, c1: Int, c2: Int): Matrix33{
            val columnVectors = matrix.getColumnVectors()
            val mappedColumnVectors = matrix.getColumnVectors()
            mappedColumnVectors[c1] = columnVectors[c2]
            mappedColumnVectors[c2] = columnVectors[c1]
            return Matrix33.fromColumnVectors(mappedColumnVectors)
        }
    }
}

private class Matrix33Inverter{
    fun invert(matrix: Matrix33): Matrix33{
        var resultMatrix = Matrix33.getOne()
        var opMatrix = matrix

        //Shuffle if necessary
        if(opMatrix.x1 == 0.0){
            if(opMatrix.x2 != 0.0){
                val mul = - 1 / opMatrix.x2

                opMatrix = Matrix33Operations.lineSubtract(opMatrix,0,1,mul)
                resultMatrix = Matrix33Operations.lineSubtract(resultMatrix,0,1,mul)
            }else if(opMatrix.x3 != 0.0){
                val mul = - 1 / opMatrix.x3

                opMatrix = Matrix33Operations.lineSubtract(opMatrix,0,2,mul)
                resultMatrix = Matrix33Operations.lineSubtract(resultMatrix,0,2,mul)
            }else{
                throw Exception()
            }
        }

        //Scale line 1
        val line1Scaler = 1 / opMatrix.x1
        if(line1Scaler.isNaN() || line1Scaler.isInfinite()){
            throw Exception()
        }
        opMatrix = Matrix33Operations.scaleLine(opMatrix,0,line1Scaler)
        resultMatrix = Matrix33Operations.scaleLine(resultMatrix, 0, line1Scaler)


        // Eliminate y1
        val line2Sub = opMatrix.y1
        opMatrix = Matrix33Operations.lineSubtract(opMatrix,1,0,line2Sub)
        resultMatrix = Matrix33Operations.lineSubtract(resultMatrix,1,0,line2Sub)

        //Eliminate z1
        val line3Sub = opMatrix.z1
        opMatrix = Matrix33Operations.lineSubtract(opMatrix,2,0,line3Sub)
        resultMatrix = Matrix33Operations.lineSubtract(resultMatrix, 2,0, line3Sub)

        //Shuffle if necessary
        if(opMatrix.y2 == 0.0){
            if(opMatrix.y3 == 0.0){
                throw Exception()
            }
            val mul = - 1 / opMatrix.y3
            opMatrix = Matrix33Operations.lineSubtract(opMatrix, 1,2, mul)
            resultMatrix = Matrix33Operations.lineSubtract(resultMatrix, 1, 2, mul)
        }

        //Scale y2
        val line2Scaler = 1 / opMatrix.y2
        if(line2Scaler.isNaN() || line2Scaler.isInfinite()){
            throw Exception()
        }
        opMatrix = Matrix33Operations.scaleLine(opMatrix,1,line2Scaler)
        resultMatrix = Matrix33Operations.scaleLine(resultMatrix, 1, line2Scaler)

        //Eliminate z2
        val line3SubZ = opMatrix.z2
        opMatrix = Matrix33Operations.lineSubtract(opMatrix,2,1,line3SubZ)
        resultMatrix = Matrix33Operations.lineSubtract(resultMatrix, 2, 1, line3SubZ)

        //Eliminate x2
        val line2SubX = opMatrix.x2
        opMatrix = Matrix33Operations.lineSubtract(opMatrix,0,1,line2SubX)
        resultMatrix = Matrix33Operations.lineSubtract(resultMatrix,0,1,line2SubX)

        //Scale z3
        val line3Scaler = 1 / opMatrix.z3
        if(line3Scaler.isNaN() || line3Scaler.isInfinite()){
            throw Exception()
        }
        opMatrix = Matrix33Operations.scaleLine(opMatrix,2,line3Scaler)
        resultMatrix = Matrix33Operations.scaleLine(resultMatrix,2,line3Scaler)

        //Eliminate x3
        val line3SubX = opMatrix.x3
        opMatrix = Matrix33Operations.lineSubtract(opMatrix,0,2,line3SubX)
        resultMatrix = Matrix33Operations.lineSubtract(resultMatrix,0,2,line3SubX)

        //Eliminate y3
        val line3SubY = opMatrix.y3
        opMatrix = Matrix33Operations.lineSubtract(opMatrix,1,2,line3SubY)
        resultMatrix = Matrix33Operations.lineSubtract(resultMatrix,1,2,line3SubY)

        return resultMatrix
    }
}
