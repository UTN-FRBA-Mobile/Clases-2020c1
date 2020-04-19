# Clases-2020c1
Prácticas de la materia

## Práctica Conectividad

Conectar la aplicación con un servicio REST simple.

* Agregar al `build.gradle` de la aplicación las dependencias necesarias para usar RetroFit y Picasso:
```
    implementation 'com.squareup.retrofit2:retrofit:2.5.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.5.0'
    implementation 'com.squareup.okhttp3:logging-interceptor:3.9.0'
    implementation 'com.squareup.picasso:picasso:2.71828'
```
(ya fueron agregadas para que levanten más rápido el entorno)

* Agregar en `AndroidManifest.xml` el permiso necesario para acceder a Internet (en `<manifest>`, fuera de `<application>`):
```
    <uses-permission android:name="android.permission.INTERNET" />
```

* Diseñar el modelo para leer el resultado del servicio con este formato:

```json
{
  "tweets": [
      {
          "profilePic": "url de imagen",
          "name": "nombre",
          "certified": true,
          "username": "@username",
          "content": "el tweet",
          "image": "url de imagen. opcional (puede no venir)",
          "commentCount": número,
          "retweetCount": número,
          "likeCount": número
      }
  ]
}
```

Pueden usar `data class` de Kotlin.

* Armar la interfaz para hacer la llamada con RetroFit a la siguiente URL:

    `https://demo0682762.mockable.io/list`

Hay una breve explicación de RetroFit en su página: https://square.github.io/retrofit/
(con leer la introducción alcanza)

* Crear una instancia de RetroFit que implemente la interfaz diseñada con:

```kotlin
val service = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create()) // Para parsear automágicamente el json
    .baseUrl("https://demo0682762.mockable.io/")
    .build()
    .create(TweetsService::class.java) // la interfaz que diseñaron antes
```

* Realizar la llamada al servicio en el `onStart` de `MainFragment`, suscribirse a la respuesta y pasar los tweets a `TweetsAdapter`.

La clase `Call` permite hacer llamadas síncronas y asíncronas. Como en `onStart` estamos en el main thread, queremos que la llamada sea asíncrona para no bloquearlo, por lo tanto la llamada se ejecuta con `enqueue`, no `execute`:

```kotlin
.enqueue(object: Callback<Respuesta> {
    override fun onResponse(call: Call<Respuesta>, response: Response<Respuesta>) {
        // pasar los tweets al tweetAdapter
    }
    override fun onFailure(call: Call<Respuesta>, error: Throwable) {
        Toast.makeText(activity, "No tweets founds!", Toast.LENGTH_SHORT).show()
    }
})
```
Acá `Respuesta` sería el nombre de la data class que crearon antes.

* Actualizar TweetsAdapter para contener una lista de tweets y mostrar la información de cada tweet.

* Mostrar las imágenes (`profilePic` e `image`) desde TweetsAdapter utilizando Picasso:
```
Picasso.get().load(Uri.parse(/* la url a bajar */).into(/* el imageView donde va la imagen */)
```
