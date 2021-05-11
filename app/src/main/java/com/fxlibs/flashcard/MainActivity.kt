package com.fxlibs.flashcard

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    data class Item(val res: Int, val resAudio: Int, val title: String)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }

        vPager.adapter = object : FragmentStatePagerAdapter(
            supportFragmentManager,
            BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        ) {

            val IMAGES = arrayOf(
                Item(R.raw.ayam, R.raw.audio_ayam, "AYAM"),
                Item(R.raw.jerapah, R.raw.audio_jerapah, "JERAPAH"),
                Item(R.raw.bebek, R.raw.audio_bebek, "BEBEK"),
                Item(R.raw.gajah, R.raw.audio_gajah, "GAJAH"),
                Item(R.raw.harimau, R.raw.audio_harimau, "HARIMAU"),
                Item(R.raw.katak, R.raw.audio_katak, "KATAK"),
                Item(R.raw.kucing, R.raw.audio_kucing, "KUCING"),
                Item(R.raw.kuda, R.raw.audio_kuda, "KUDA"),
                Item(R.raw.kudanil, R.raw.audio_kudanil, "KUDANIL"),
                Item(R.raw.merpati, R.raw.audio_merpati, "MERPATI"),
                Item(R.raw.paus, R.raw.audio_ikan_paus, "IKAN PAUS"),
                Item(R.raw.singa, R.raw.audio_singa, "SINGA"),
                Item(R.raw.zebra, R.raw.audio_zebra, "ZEBRA")
//                Item(R.drawable.animal_duck, "BEBEK"),
//                Item(R.drawable.animal_horse, "KUDA"),
//                Item(R.drawable.animal_jerapah, "JERAPAH"),
//                Item(R.drawable.animal_lion, "SINGA"),
//                Item(R.drawable.animal_pinguin, "PINGUIN"),
//                Item(R.drawable.animal_tiger, "HARIMAU"),
//                Item(R.drawable.animal_turtle, "KURA-KURA"),
//                Item(R.drawable.animal_whale, "IKAN PAUS"),
//                Item(R.drawable.animal_zebra, "ZEBRA"),
//                Item(R.drawable.animal_frog, "KATAK"),
//                Item(R.drawable.animal_elephant, "GAJAH"),
//                Item(R.drawable.animal_dolphin, "LUMBA-LUMBA"),
//                Item(R.drawable.animal_clownfish, "IKAN BADUT"),
////                Item(R.drawable.animal_bufallo, "KERBAU"),
//                Item(R.drawable.animal_hippo, "KUDANIL"),
//                Item(R.drawable.animal_octopus, "GURITA"),
//                Item(R.drawable.animal_jellyfish, "UBUR-UBUR")
            ).apply {
                sortBy {it.title}
            }

            override fun getCount(): Int {
                return IMAGES.size
            }

            override fun getItem(position: Int): Fragment {
                return ImageFragment(onImageClick = {
                    var next = vPager.currentItem + 1
                    if (next == IMAGES.size) {
                        next = 0
                    }
                    vPager.currentItem = next
                }).apply {
                    arguments = Bundle().apply {
                        putString(ImageFragment.TITLE, IMAGES[position].title)
                        putInt(ImageFragment.IMAGE_RES, IMAGES[position].res)
                        putInt(ImageFragment.AUDIO_RES, IMAGES[position].resAudio)
                    }
                }
            }
        }.apply {
        }
        vPager.offscreenPageLimit = 5
        vPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                (vPager.adapter as FragmentStatePagerAdapter).let {
                    (it.instantiateItem(vPager, position) as ImageFragment).apply {
                        playSound()
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

        })

        emitBubbles()

    }

    private fun emitBubbles() {
        Handler().postDelayed({
            // Random is used to select random bubble
            // size
            val size = Random.nextInt(20, 80)
            bubbleEmitter.emitBubble(size)
            bubbleEmitter.setColors(
                resources.getColor(R.color.colorPrimary),
                resources.getColor(R.color.colorPrimaryDark),
                resources.getColor(R.color.colorAccent)
            )
            emitBubbles()
        }, Random.nextLong(100, 500))
    }
}