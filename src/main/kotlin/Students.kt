import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.onClick
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.delay
import java.awt.TextField
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
fun actualizar_estado(message:String,
                      onDismiss: () -> Unit,
                      onclickActualizarTexto: () -> Unit,
                      onClickNoHacerNada:() -> Unit,
                      refrescarTexto :(String) -> Unit,
                      textState: String,
                      focusRequester: FocusRequester,
                      agregarStudents: (index:Int,texto:String) -> Unit){
    Dialog(
        title = "Atención",
        resizable = false,
        onCloseRequest = onDismiss
    ){
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            Text(message)
            OutlinedTextField(
                label = { Text("cambiarnombre") },
                value = textState,
                singleLine = true,
                onValueChange = {refrescarTexto(it)},
                modifier = Modifier.focusRequester(focusRequester).onKeyEvent { event ->
                    if (event.type == KeyEventType.KeyUp && event.key == Key.Enter) {
                        agregarStudents(1,textState)
                        true
                    } else {
                        false
                    }
                }
            )
            Row{
               Button(
                   onClick = onclickActualizarTexto
               ) {
                   Text("Aceptar")
               }
                Button(
                    onClick = onClickNoHacerNada
                ) {
                    Text("Denegar")
                }
            }
        }
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
                onValueChange = {refrescarTexto(it)},
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun campoDeListaYBotonDelete(lista: MutableList<String>, onclickeliminar:(numero:Int)->Unit,onclickrefrescartexto:(numero:Int) ->Unit ) {
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
                        .onClick { onclickrefrescartexto(index) }
                ) {
                    Text(text = item)
                    Spacer(modifier = Modifier.weight(1f).widthIn(min = 450.dp, max = 450.dp))
                    IconButton(
                        onClick = {
                            onclickeliminar(index)
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
fun ventanaPrincipal(viewModel: ViewModel) {

    val textState = viewModel.textState.value
    val lista = viewModel.lista
    val toasta = viewModel.toasta.value
    val mensaje = viewModel.mensaje.value
    val ventanatexto = viewModel.ventanatexto.value

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
                refrescarTexto = { viewModel.refrescarTexto(it) },
                agregarStudents = {
                    if (textState.isNotBlank()) {
                        viewModel.agregarEstudiante(textState)
                        viewModel.refrescarTexto("")
                        focusRequester.requestFocus()
                    } else {
                        viewModel.refrescarMensaje("ERROR no se puede añadir datos vacios")
                        viewModel.refrescarToasta(true)
                        viewModel.refrescarTexto("")
                    }

                }
            )

            Row(
                modifier = Modifier.padding(
                    start = 10.dp
                )
            ) {
                campoDeListaYBotonDelete(
                    lista = lista,
                    onclickeliminar = { index -> viewModel.eliminarEstudiante(index)},
                    onclickrefrescartexto = { index ->
                        viewModel.refrescarMensaje("¿Cual es el nuevo nombre?")
                        viewModel.refrescarEstadoPantallaEstudiante(true)
                        viewModel.refrescarTexto("")
                    }
                )
            }

        }

        campoDeSalvarDatos(
            guardarDatos = {
                if(viewModel.guardarEstudiantes()){
                    viewModel.refrescarMensaje("datos guardados")
                    viewModel.refrescarToasta(true)
                }else{
                    viewModel.refrescarMensaje("Hubo un error")
                    viewModel.refrescarToasta(true)
                }

            }
        )
        if (toasta) {
            Toast(mensaje) {
                viewModel.refrescarToasta(false)
            }
            focusRequester.requestFocus()

        }
            if(ventanatexto)
                actualizar_estado(
                    message = mensaje,
                    onDismiss = {viewModel.refrescarEstadoPantallaEstudiante(false)},
                    onclickActualizarTexto = TODO(),
                    onClickNoHacerNada = TODO(),
                    refrescarTexto = { viewModel.refrescarTexto(it) },
                    textState= textState,
                    focusRequester = focusRequester ,
                    agregarStudents = { index,text -> viewModel.refrescartextoestudiante(index, text) })

        }
}
