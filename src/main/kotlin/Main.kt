import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application


fun main() = application {
    val gestor = GestorFicheros()
    val viewModel = ViewModel(gestor)
    Window(onCloseRequest = ::exitApplication) {
        ventanaPrincipal(viewModel)
    }

}
