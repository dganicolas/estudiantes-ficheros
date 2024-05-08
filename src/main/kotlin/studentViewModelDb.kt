import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import java.io.File

class studentViewModelDb(
    override val ficheros: IGestorFicheros
) :IViewModel {
    override val _lista = ficheros.leer(File("src/main/recursos/alumnos.txt"))
    override val lista = _lista

    override var _toasta = mutableStateOf(false)
    override val toasta = _toasta

    val _posicion = mutableStateOf(0)
    val posicion = _posicion
    override var _mensaje = mutableStateOf("")
    override val mensaje = _mensaje

    override var _textState = mutableStateOf("")
    override val textState: State<String> = _textState

    override var _ventanatexto = mutableStateOf(false)
    override val ventanatexto = _ventanatexto


    override fun refrescarTexto(texto: String) {
        TODO("Not yet implemented")
    }

    override fun refrescarMensaje(texto: String) {
        TODO("Not yet implemented")
    }

    override fun refrescarToasta(estado: Boolean) {
        TODO("Not yet implemented")
    }

    override fun eliminarEstudiante(posicion: Int) {
        TODO("Not yet implemented")
    }

    override fun agregarEstudiante(estudiante: String) {
        TODO("Not yet implemented")
    }

    override fun guardarEstudiantes(): Boolean {
        TODO("Not yet implemented")
    }

    override fun refrescartextoestudiante(texto: String) {
        TODO("Not yet implemented")
    }

    override fun refrescarPosicion(posicion: Int) {
        TODO("Not yet implemented")
    }

    override fun refrescarEstadoPantallaEstudiante(estado: Boolean) {
        TODO("Not yet implemented")
    }
}