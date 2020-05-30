package ar.edu.utn.frba.mobile.clases_2020c1

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        setFirebaseTokenInView()

        reloadButton.setOnClickListener { setFirebaseTokenInView() }

        copyButton.setOnClickListener { copyTokenToClipboard() }

        subscribeButton.setOnClickListener { subscribeToTopic() }
    }

    private fun setFirebaseTokenInView() {
        val firebaseTokenText = MyPreferences.getFirebaseToken(this)

        if (firebaseToken != null) {
            firebaseToken.setText(firebaseTokenText)
            reloadButton.visibility = View.GONE
            copyButton.visibility = View.VISIBLE
            topicContainer.visibility = View.VISIBLE
        } else {
            copyButton.visibility = View.GONE
            topicContainer.visibility = View.GONE
            reloadButton.visibility = View.VISIBLE
        }
    }

    private fun subscribeToTopic() {
        val topicText = topic.text.toString()
        FirebaseMessaging.getInstance().subscribeToTopic(topicText)
        Toast.makeText(this, "Subscripto a $topicText", Toast.LENGTH_SHORT).show()
        topic.setText("")
    }

    fun copyTokenToClipboard() {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Firebase token", firebaseToken.text)
        clipboard.setPrimaryClip(clip)
    }
}
