import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State

interface IViewModel {
    val ficheros: IGestorFicheros
    val _lista: MutableList<String>
    val lista: MutableList<String>
    var _toasta: MutableState<Boolean>
    val toasta: MutableState<Boolean>
    var _mensaje: MutableState<String>
    val mensaje: MutableState<String>
    var _textState: MutableState<String>
    val textState: State<String>
    var _ventanatexto: MutableState<Boolean>
    val ventanatexto: MutableState<Boolean>
    fun refrescarTexto(texto: String)
    fun refrescarMensaje(texto: String)
    fun refrescarToasta(estado: Boolean)
    fun eliminarEstudiante(posicion: Int)
    fun agregarEstudiante(estudiante: String)
    fun guardarEstudiantes(): Boolean
    fun refrescartextoestudiante(texto: String)
    fun refrescarPosicion(posicion: Int)
    fun refrescarEstadoPantallaEstudiante(estado: Boolean)
}