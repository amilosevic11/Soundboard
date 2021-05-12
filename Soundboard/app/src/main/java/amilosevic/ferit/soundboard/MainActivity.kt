package amilosevic.ferit.soundboard

import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var soundPool: SoundPool
    private var loaded: Boolean = false
    var soundMap: HashMap<Int, Int> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupUi()
        loadSounds()
    }

    private fun setupUi() {
        this.ib_tomhanks.setOnClickListener(this)
        this.ib_rdj.setOnClickListener(this)
        this.ib_johnnydepp.setOnClickListener(this)
    }

    private fun loadSounds() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            this.soundPool = SoundPool.Builder().setMaxStreams(10).build()
        }
        else
        {
            this.soundPool = SoundPool(10, AudioManager.STREAM_MUSIC, 0)
        }

        this.soundPool.setOnLoadCompleteListener{_, _, _ -> loaded = true}

        this.soundMap[R.raw.hanks] = this.soundPool.load(this, R.raw.hanks, 1)
        this.soundMap[R.raw.rdj] = this.soundPool.load(this, R.raw.rdj, 1)
        this.soundMap[R.raw.depp] = this.soundPool.load(this, R.raw.depp, 1)
    }

    private fun playSound(selectedSound: Int) {
        val soundID = this.soundMap[selectedSound] ?: 0

        this.soundPool.play(soundID, 1f, 1f, 1, 0, 1f)
    }

    override fun onClick(v: View?) {
        if(!this.loaded) return
        when(v?.id)
        {
            R.id.ib_tomhanks -> playSound(R.raw.hanks)
            R.id.ib_rdj -> playSound(R.raw.rdj)
            R.id.ib_johnnydepp -> playSound((R.raw.depp))
        }
    }
}