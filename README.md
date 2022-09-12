## Qué es ECOS
Es una app Android que visualiza una **lista de libros** donde cada elemento cuenta con imagen, título, autoría y origen.<br>📔 Al presionar un elemento del listado, cambia de pantalla y visualiza **más datos del libro** como el precio y opción de delivery.<br>🛍💌 También encontrarás una opción para revisar su información en Wikipedia y **enviar un correo para tramitar su compra**.

### Imágenes del funcionamiento de la app
| <p align="center"> <img src="readme/01_main.jpg" width="220" alt="main screen"> | <p align="center"> <img src="readme/02_main_horizontal.jpg" width="420" alt="main screen horizontal"> |
|--|--|
| <p  align="center">Pantalla principal. | <p  align="center">Pantalla principal en orientación horizontal. |
|<p align="center"> <img src="readme/04_detail_horizontal.jpg" width="220" alt="detail screen horizontal">|<p align="center"> <img src="readme/03_detail.jpg" width="420" alt="detail screen">|
|<p  align="center">Pantalla detalle del libro.|<p  align="center">Pantalla detalle del libro en orientación horizontal. |

### Cápsula del funcionamiento de la app
[![Img alt text](https://img.youtube.com/vi/DvzT26Bf9us/hqdefault.jpg)](https://youtu.be/DvzT26Bf9us)

## Características técnicas
- Lenguaje: Kotlin.
- 🖼 XML layout para construir UI nativo.
- Patrón de arquitectura MVVM.
- Consumo de API RESTful con cliente HTTP Retrofit2.
- 🚀 Navigation Component para navegar entre pantallas.
- Librería Picasso para cargar imágenes.
- 💾 BBDD SQLite con una capa de abstracción Room.
- 👟 Coroutines.
- LiveData y Flow.
- 🔎🤓 Unit Testing: Room, Repository and ViewModel.
- 🔄 Vista adaptable según orientación del dispositivo.
- Intent: información web y enviar correo.
