import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.delay
import java.io.File

@Composable
fun Toast(message: String, onDismiss: () -> Unit) {
    Dialog(
        //icon = painterResource("info_icon.png"),
        title = "Atención",
        resizable = false,
        onCloseRequest = onDismiss
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            Text(message)
        }
    }
    // Cierra el Toast después de 2 segundos
    LaunchedEffect(Unit) {
        delay(2000)
        onDismiss()
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun campoDeTextoYBotonNuevosEstudiantes(
    textState: String,
    focusRequester: FocusRequester,
    refrescarTexto: (String) -> Unit,
    agregarStudents: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxHeight(0.8f),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier.background(color = Color.Blue)
        ) {
            OutlinedTextField(
                label = { Text("New student name") },
                value = textState,
                singleLine = true,
                onValueChange = refrescarTexto,
                modifier = Modifier.focusRequester(focusRequester).onKeyEvent { event ->
                    if (event.type == KeyEventType.KeyUp && event.key == Key.Enter) {
                        agregarStudents()
                        true
                    } else {
                        false
                    }
                }
            )
            Button(
                onClick = agregarStudents
            ) {
                Text("Add new student")
            }
        }


    }
}

@Composable
fun campoDeListaYBotonDelete(lista: MutableList<String>) {
    Column(
        modifier = Modifier.background(color = Color.Gray).fillMaxHeight(0.9f)
    ) {
        Text("Students: ${lista.size}")
        LazyColumn(
            modifier = Modifier.background(Color.White).fillMaxHeight(0.8f).widthIn(min = 500.dp, max = 500.dp)
        ) {
            itemsIndexed(lista) { index, item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(text = item)
                    Spacer(modifier = Modifier.weight(1f).widthIn(min = 450.dp, max = 450.dp))
                    IconButton(
                        onClick = {
                            lista.removeAt(index)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = ""
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun campoDeSalvarDatos(guardarDatos: () -> Unit) {
    Column(
        modifier = Modifier.background(color = Color.Green).fillMaxWidth().fillMaxHeight(1f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //aqui va el boton de salvar cambios
        Button(onClick = guardarDatos) {
            Text("Save changes")
        }
    }
}

@Composable
@Preview
fun ventanaPrincipal() {
    val lista = remember { File("src/main/recursos/alumnos.txt").readLines().toMutableStateList() }
    var toasta by remember { mutableStateOf(false) }
    var mensaje by remember { mutableStateOf("") }
    var textState by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(
            top = 30.dp
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row {
            campoDeTextoYBotonNuevosEstudiantes(
                textState = textState,
                focusRequester = focusRequester,
                refrescarTexto = { textState = it },
                agregarStudents = {
                    if (textState.isNotBlank()) {
                        lista.add(textState)
                        textState = ""
                        focusRequester.requestFocus()
                    } else {
                        mensaje = "ERROR no se puede añadir datos vacios"
                        toasta = true
                        textState = ""
                    }

                }
            )

            Row(
                modifier = Modifier.padding(
                    start = 10.dp
                )
            ) {
                campoDeListaYBotonDelete(
                    lista = lista
                )
            }

        }
        campoDeSalvarDatos(
            guardarDatos = {
                mensaje = "datos guardados"
                toasta = true
                File("src/main/recursos/alumnos.txt").writeText("")
                lista.forEach { File("src/main/recursos/alumnos.txt").appendText("$it\n") }

            }
        )
        if (toasta) {
            Toast(mensaje) {
                toasta = false
            }
            focusRequester.requestFocus()

        }
    }


}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        ventanaPrincipal()
    }

}
