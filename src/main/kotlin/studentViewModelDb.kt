import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import java.io.File

class studentViewModelDb(
    val dataBase: IStudentsRepository
) : IViewModel {
    override val _lista = mutableStateListOf<String>()
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

    init {
        cargaralumnos()
    }

    fun cargaralumnos() {
        val result = StudentsRepository().getAllStudents()
        try {
            result.onSuccess {
                it.forEach { estudiante ->
                        _lista.add(estudiante)
                }
            }.onFailure {
                throw it
            }
        } catch (e: Exception) {
            _lista.add("reinicia la app")
        }

    }

    override fun refrescarTexto(texto: String) {
        _textState.value = texto
    }

    override fun refrescarMensaje(texto: String) {
        _mensaje.value = texto
    }

    override fun refrescarToasta(estado: Boolean) {
        _toasta.value = estado
    }

    override fun eliminarEstudiante(posicion: Int) {
        _lista.removeAt(posicion)
    }

    override fun agregarEstudiante(estudiante: String) {
        _lista.add(estudiante)
    }

    override fun guardarEstudiantes(): Boolean {
        var estado = false
        val rs = dataBase.updateStudents(_lista)
        rs.onSuccess { estado = true }.onFailure { estado = false }
        return estado

    }

    override fun refrescartextoestudiante(texto: String) {
        _lista[posicion.value] = texto
    }

    override fun refrescarPosicion(posicion: Int) {
        _posicion.value = posicion
    }

    override fun refrescarEstadoPantallaEstudiante(estado: Boolean) {
        _ventanatexto.value = estado
    }
}