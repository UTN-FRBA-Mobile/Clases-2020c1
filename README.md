# Clases-2020c1
Prácticas de la materia

## Práctica Permisos y Persistencia

Configurar aplicación para que solicite permisos de lectura/escritura de almacenamiento externo y para que persista archivos en File System así como en preferencias.
* Partiendo de una aplicación de edición de fotos casi funcionando, se debe agregar el código necesario para que la misma sea funcional.
* La aplicación está preparada para:
  - Listar las imágenes editadas por el usuario previamente en la pantalla inicial de la misma, en formato de grilla o lista, según gusto de usuario (dicho gusto debe ser recordado por la aplicación -> Guardado en preferencias)
  - Lanzar un Picker de imágenes, y recibir la imágen seleccionada
  - Editar la imágen seleccionada según una serie de filtros y calibraciones de brillo, contraste y saturación
* Inicialmente la aplicación usa el almacenamiento interno para guardar y listar las imágenes pero siendo que queremos que estar imágenes estén disponibles en la galería debemos cambiar la aplicación para usar almacenamiento externo.
* A la aplicación le hace falta:
  - Guardar las preferencias.
  - Cambiar el uso de almacenamiento interno por externo.
  - Pedir los permisos necesarios para poder leer/escribir en almacenamiento externo.

### Guardar las preferencias
La clase `MyPreferences` está preparada para persistir las preferencias pero no lo hace. Se provee un método privado `getPreferences(context)` que facilita el acceso a las mismas y una constante `showGridKey` para usar de clave.
* Cambiar `isGridImagesListPreferredView` para retornar el valor de `showGridKey` de las preferencias.
* Implementar `setGridImagesListPreferredView` para guardar el valor en las preferencias.
Como el objeto para acceder a las preferencias es de solo lectura se debe crear un editor con `.edit()`, hacer los cambios ahí y luego confirmar los cambios con `.apply()`.  

### Cambiar el uso de almacenamiento interno por externo

En los métodos `ImagesFragment.getEditedPictures` y `EditImageFragment.save` se utiliza la clase `InternalStorage` para leer y guardar las imágenes respectivamente. Se debe cambiar para usar `ExternalStorage`.

Tanto `InternalStorage` como `ExternalStorage` están definidas en el package `.utils.fileSystem`. Los métodos de `InternalStorage` requieren un contexto porque dependen de la aplicación, en cambio los de `ExternalStorage` no, ya que es global.


### Pedir los permisos necesarios para poder leer/escribir en almacenamiento externo
* Agregar en `AndroidManifest.xml` el permiso necesario para acceder a Internet (en `<manifest>`, fuera de `<application>`):
```
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
```
A partir de este momento para probar la aplicación, se puede ir a configuración y darle permisos al almacenamiento de forma manual. Al final de la práctica, la aplicación deberá poder pedir estos permisos por sí misma, y además, si dichos permisos son revocados en cualquier momento (desde la configuración del dispositivo), deberá ser capaz de notarlo y volver a pedirlos.
* Al editar una nueva imagen debe verificarse que será posible grabarla. Para esto agregar una llamada a ```Permissions.checkForPermissions...``` antes de lanzar la selección de una imagen en el `onClickListener` del `addButton` de `ImagesFragment`. Esta función devuelve `true` si hay permiso para acceder al almacenamiento externo, por lo tanto si devuelve `true` se puede lanzar la selección de imagen directamente. En caso contrario lanza automáticamente la solicitud de permiso al usuario. Los parámetros que necesita son:
    + permissionCode: el permiso que queremos validar/solicitar. `android.Manifest.permission.WRITE_EXTERNAL_STORAGE` en nuestro caso.
    + requestCode: un identificador numérico. Sirve para diferenciar solicitudes en `onRequestPermissionsResult`.
    + reason: un string con la explicación al usuario de por qué necesita la aplicación ese permiso. Se mostrará si el usuario rechaza el pedido la primera vez.

* Agregar en `ImagesFragment` el método `onRequestPermissionResult` para gestionar el retorno del pedido de permisos:
```
override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
    when (requestCode) {
        requestCodeConElqueLanzamosElPedidoDePermiso -> {
            if (grantResults.count() > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // tenemos permiso, continuar con la tarea
            }
            else {
                // Controlar que no nos dieron permiso, por ejemplo mostrando un Toast
            }
            return
        }
    }

    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
}
```
el "continuar con la tarea" en nuestro caso sería lanzar el selector de imagen que solicitó el usuario en primer lugar.

### Notas:
- Para cargar imagenes al emulador de Android solo es necesario arrastrarlas al mismo
