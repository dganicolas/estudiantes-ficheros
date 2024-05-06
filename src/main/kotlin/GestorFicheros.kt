import androidx.compose.runtime.toMutableStateList
import java.io.File

class GestorFicheros: IGestorFicheros {
    override fun escribir(fichero: File, lista: List<String>): Boolean {
        fichero.writeText("")
        var contador = 0
        lista.forEach {
            if (lista.size-1 == contador) {
                fichero.appendText("$it")
            } else {
                contador++
                fichero.appendText("$it\n")
            }
        }
        return true
    }

    override fun leer(fichero: File): MutableList<String> {
        return fichero.readLines().toMutableStateList()
    }
}