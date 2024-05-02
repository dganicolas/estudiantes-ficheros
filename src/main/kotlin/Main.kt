import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application


fun main() = application {
    val viewModel = ViewModel()
    val gestor = GestorFicheros()
    Window(onCloseRequest = ::exitApplication) {
        ventanaPrincipal(gestor,viewModel)
    }

}
