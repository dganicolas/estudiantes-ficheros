import androidx.compose.runtime.*
import java.io.File

class ViewModel(
    override val ficheros: IGestorFicheros
) : IViewModel {
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

    override fun guardarEstudiantes():Boolean{
            return ficheros.escribir(File("src/main/recursos/alumnos.txt"),_lista)
    }

    override fun refrescartextoestudiante(texto:String){
        _lista[posicion.value]=texto
    }

    override fun refrescarPosicion(posicion: Int){
        _posicion.value = posicion
    }
    override fun refrescarEstadoPantallaEstudiante(estado:Boolean){
        _ventanatexto.value = estado
    }
}