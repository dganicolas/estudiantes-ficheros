import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
/**
La lista se debe cargar de un fichero de texto, manejarla en una lista y actualizarla al pulsar el botón "Save changes".

El resto de efectos lo que vosotros seáis capaces de realizar... manejar foco, mostrar información contextual, abrir un diálogo (os paso un simulador de Toast), reaccionar al pulsar teclas (por ejemplo en New Student si pulsamos INTRO que agregue el nuevo usuario... solo si tiene algún valor) controlar el máximo de caracteres para escribir un nombre de estudiante (yo lo he puesto a 10), mostrar mensajes, etc.

AYUDA:

 * Para manejar el foco en los distintos composables: FocusRequester.

Permite controlar el foco de los composables. Se usa para solicitar, ganar o ceder el foco a un composable en particular.

Uso básico:

- Inicialización con remember:

val focusRequester = remember { FocusRequester() }


Se utiliza remember para mantener el estado del FocusRequester a través de recomposiciones. Esto asegura que el objeto FocusRequester se mantenga constante y no se reinicie con cada recomposición, lo cual es crucial para mantener el comportamiento esperado del foco dentro de la UI.

- Asignación a Composables:

El FocusRequester se puede pasar a cualquier composable que pueda recibir foco mediante el modificador focusRequester().

TextField(
value = textState,
onValueChange = { textState = it },
modifier = Modifier.focusRequester(focusRequester)
)

- Solicitud de Foco:

El foco se puede solicitar programáticamente en respuesta a eventos o condiciones específicas usando el método requestFocus() del FocusRequester.

Button(onClick = { focusRequester.requestFocus() }) {
Text("Focus the text field")
}
 * Os proporciono la función que simula el Toast de los móviles para que la uséis a la hora de proporcionar información (se ha guardado correctamente el fichero, errores, ...):

@Composable
fun Toast(message: String, onDismiss: () -> Unit) {
Dialog(
icon = painterResource("info_icon.png"),
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
 * TooltipArea:

Se utiliza para proporcionar información contextual en forma de una pequeña ventana emergente (tooltip) cuando el usuario interactúa con el componente de la interfaz, por ejemplo, al pasar el cursor sobre un elemento en una aplicación de escritorio.

TooltipArea(
tooltip = {
// Composable con la info que se quiera mostrar...
}
) {
// Composable sobre el que se mostrará la info contextual
}
 * onKeyEvent:

Es un manejador de eventos que te permite reaccionar a eventos de teclado que ocurren en un composable específico. Es especialmente útil en desarrollos de escritorio o en aplicaciones web que necesitan interactividad a través del teclado.

En resumen, para manejar los eventos de pulsaciones de teclas en un composable... por ejemplo en la LazyColumn dónde se mostrarán los estudiantes:

modifier = Modifier
.onKeyEvent { event ->
if (event.type == KeyEventType.KeyUp) {
when (event.key) {
Key.DirectionUp -> {
if (selectedIndex > 0) {
onStudentSelected(selectedIndex - 1)
true
} else false
}
Key.DirectionDown -> {
if (selectedIndex < studentsState.size - 1) {
onStudentSelected(selectedIndex + 1)
true
} else false
}
else -> false
}
} else {
false//Solo manejar cuando la tecla se haya levantado de la presión
}
}

Explicación del código:

- La lambda de onKeyEvent recibe un KeyEvent que contiene detalles sobre la tecla presionada, como el tipo de evento (KeyEventType) y la tecla específica (Key).

Retorna un Boolean: true para indicar que el evento ha sido manejado y no necesita propagarse más, false para permitir que otros elementos también respondan al mismo evento.

- if (event.type == KeyEventType.KeyUp): para asegurarnos que solo se manejen los eventos cuando la tecla se libera (KeyUp). Esto ayuda a evitar acciones repetidas que podrían suceder si se manejaran los eventos de presión (KeyDown).

- Key.DirectionUp y Key.DirectionDown se usan para determinar si el usuario quiere navegar hacia arriba o hacia abajo en la lista de estudiantes.

- Se verifica el selectedIndex para asegurar que la navegación no salga de los límites de la lista. Por ejemplo, si selectedIndex es 0, no se puede mover más hacia arriba, y si es igual a studentsState.size - 1, no se puede mover más hacia abajo.

- onStudentSelected esa función que se llama con el nuevo índice seleccionado para actualizar la selección.

 * LaunchedEffect

Es una función de Compose que permite ejecutar efectos secundarios (código que tiene efectos fuera del ámbito puramente local del composable, como llamadas a la red, lecturas/escrituras de archivos, etc.) dentro de un ámbito de Compose. Lo importante de LaunchedEffect es que se ejecuta en el contexto de una coroutine, lo que permite realizar operaciones asincrónicas sin bloquear la interfaz de usuario.

Características:

- Control de ejecución: LaunchedEffect se ejecuta cuando el composable es compuesto por primera vez y cada vez que cambian los valores de las claves que se le pasan. Si las claves no cambian entre recomposiciones, el efecto no se volverá a ejecutar.

- Cancelación automática: Cuando el composable que llamó a LaunchedEffect es eliminado del árbol de composición, o las claves cambian, cualquier coroutine lanzada por LaunchedEffect se cancela automáticamente.

LaunchedEffect(key1 = true) {  // key1 = true asegura que esto se ejecute solo una vez
...
}
Con el parámetro key1 = true el efecto dentro de LaunchedEffect solo se ejecuta una vez cuando el composable se compone inicialmente. Esto es ideal para cargar datos inicialmente.

 * Otras Recomendaciones:

- La función main() debería hacer una llamada a la función composable principal (dónde ya se incluye el componente Window y las llamadas al resto de funciones composables que configuran la pantalla). Desde el main yo pasaría el título de la ventana, icono, tamaño y posición centrada con respecto a la resolución de la pantalla, el gestor de ficheros y el fichero de los estudiantes y qué hacer al cerrar dicha ventana, es decir: onCloseMainWindow = { exitApplication() }.


- La ventana MainWindow podría comenzar por MaterialTheme y dentro de su contenido introducimos un Surface con la llamada a la función StudentScreen.


- En la función StudentScreen yo tendría elevados los estados.


- Cread un fichero Colors.kt dónde podéis crear variables para los colores que utilicéis. Por ejemplo:


val colorSelected = Color(0xFF9CDCFA)
val colorUnselected = Color.Transparent

 * */
@Composable
@Preview
fun ventanaPrincipal(text: String,
                     onExit : () -> Unit) {
    Window(onCloseRequest = onExit) {

        MaterialTheme() {
            Surface() {

            }
            Button(onClick = {
                text = "Hello, Desktop!"
            }) {
                Text(text)
            }
        }
    }
}

fun main() = application {
    var text by remember { mutableStateOf("Hello, World!") }

        ventanaPrincipal(text,
            onExit = { exitApplication() })

}
