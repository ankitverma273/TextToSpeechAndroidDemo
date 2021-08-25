package com.ankitdev.texttospeechdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import com.ankitdev.texttospeechdemo.databinding.ActivityMainBinding
import java.util.*

//Extend our class with TextToSpeech OnInitListener, in our case its Main Activity
class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null   //Variable for text to speech
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initialize the Text to Speech
        tts = TextToSpeech(this, this)

        //Button click listener
        binding.btnConvertToSpeech.setOnClickListener {

            if (binding.etText.text.isEmpty()){
                Toast.makeText(this,
                    "Enter a text to use text to speech",
                    Toast.LENGTH_SHORT).show()
            }else{
                //Function to get the speech output
                speakOut(binding.etText.text.toString())
            }
        }

    }

    //speakOut function will take care for our tts to speak whats in the edittext
    private fun speakOut(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }
    //tts - text to speech
    //tts initialization takes place here
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS){
            //Set US English as language for tts
            val result = tts?.setLanguage(Locale.US)

            //if there is some error for missing data or language not supported
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS","The Language specified is not supported")
            }
        }else{
            Log.e("TTS","Initialization Failed")
        }
    }

    /**
     * Activity.onDestroy function will stop and shutDown the tts which is initialized
     */
    public override fun onDestroy() {
        if (tts != null){
            tts?.stop()
            tts?.shutdown()
        }
        super.onDestroy()
    }
}