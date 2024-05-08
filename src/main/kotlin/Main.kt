import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application


fun main() = application {
    val gestor = GestorFicheros()
    val ventanaEleccion = mutableStateOf(true)
    val opcionsql = mutableStateOf(false)
    val opcionlocal = mutableStateOf(false)
    Window(onCloseRequest = ::exitApplication) {
        if (ventanaEleccion.value) {
            ventanaEleccion(
                onclickAceptarOpcion = { ventanaEleccion.value = false },
                opcionsql = opcionsql.value,
                opciontxt = opcionlocal.value,
                oncheckedOpcionSql = {
                    if (!opcionsql.value) {
                        opcionsql.value = true
                        opcionlocal.value = false
                    }else{
                        opcionsql.value = false
                        opcionlocal.value = false
                    }

                },
                oncheckedOpcionTxt = {
                        if (!opcionlocal.value) {
                            opcionlocal.value = true
                            opcionsql.value = false
                        }else{
                            opcionlocal.value = false
                            opcionsql.value = false
                        }

                }
            )
        } else {
            val viewModel = if(opcionsql.value ){
                studentViewModelDb(StudentsRepository())
            }else{
                ViewModel(gestor)

            }
            ventanaPrincipal(viewModel)
        }
     }
}


