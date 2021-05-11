package com.fxlibs.flashcard

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.image_fragment.*
import kotlinx.android.synthetic.main.image_fragment.view.*

class ImageFragment(val onImageClick:() -> Unit) : Fragment() {

    companion object {
        val IMAGE_RES = "image"
        val AUDIO_RES = "audio"
        val TITLE = "title"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.image_fragment, container,false)
    }

    var mPlayer: MediaPlayer? = null
    var isAudioReady = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let {
            view.apply {
                txtTitle.text = it.getString(TITLE)
                Glide.with(this).load(it.getInt(IMAGE_RES)).into(imgImage)
                mPlayer = MediaPlayer.create(activity, it.getInt(AUDIO_RES)).apply {
                    setOnPreparedListener {
                        isAudioReady = true
                    }
                }
                imgImage.setOnClickListener {img ->
                    if (isAudioReady && mPlayer?.isPlaying != true) {
                        mPlayer?.seekTo(0)
                        mPlayer?.start()
                    }
                }
            }
        }
    }

    fun playSound() {
        imgImage.callOnClick()
    }

}