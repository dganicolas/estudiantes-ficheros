import androidx.compose.runtime.*
import java.io.File

class ViewModel(
    val ficheros: IGestorFicheros
) {
    private val _lista = ficheros.leer(File("src/main/recursos/alumnos.txt"))
    val lista = _lista

    private var _toasta = mutableStateOf(false)
    val toasta = _toasta

    private var _mensaje = mutableStateOf("")
    val mensaje = _mensaje

    private var _textState = mutableStateOf("")
    val textState: State<String> = _textState

    private var _ventanatexto = mutableStateOf(false)
    val ventanatexto = _ventanatexto

    fun refrescarTexto(texto: String) {
        _textState.value = texto
    }

    fun refrescarMensaje(texto: String) {
        _mensaje.value = texto
    }

    fun refrescarToasta(estado: Boolean) {
        _toasta.value = estado
    }

    fun eliminarEstudiante(posicion: Int) {
        _lista.removeAt(posicion)
    }

    fun agregarEstudiante(estudiante: String) {
        _lista.add(estudiante)
    }

    fun guardarEstudiantes():Boolean{
            return ficheros.escribir(File("src/main/recursos/alumnos.txt"),_lista)
    }

    fun refrescartextoestudiante(posicion:Int,texto:String){
        _lista[posicion]=texto
    }

    fun refrescarEstadoPantallaEstudiante(estado:Boolean){
        _ventanatexto.value = estado
    }
}