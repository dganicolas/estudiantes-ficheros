import androidx.compose.runtime.*
import java.io.File

class ViewModel {
    val lista = File("src/main/recursos/alumnos.txt").readLines().toMutableStateList()

    var toasta = mutableStateOf(false)
    var mensaje = mutableStateOf("")

    private var _textState = mutableStateOf("")
    val textState : State<String> = _textState


    fun refrescarTexto(texto: String) {
        _textState = text
    }


}