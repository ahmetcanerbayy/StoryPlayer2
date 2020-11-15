package com.bolaware.viewsslidetimer

import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import android.net.Uri
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.VideoView
import com.bolaware.viewstimerstory.Momentz
import com.bolaware.viewstimerstory.MomentzCallback
import com.bolaware.viewstimerstory.MomentzView
import com.squareup.picasso.Callback
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import toPixel
import java.lang.Exception


class MainActivity : AppCompatActivity(), MomentzCallback {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        // show an imageview be loaded from file
        val locallyLoadedImageView = ImageView(this)
        locallyLoadedImageView.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.bieber

            )
        )



        //video to be loaded from the internet
        val internetLoadedVideo = VideoView(this)

        val listOfViews = listOf(
            MomentzView(locallyLoadedImageView, 5),
            MomentzView(internetLoadedVideo, 60)
        )

        Momentz(this, listOfViews, container, this).start()
    }


    override fun onNextCalled(view: View, momentz: Momentz, index: Int) {
        if (view is VideoView) {
            momentz.pause(true)
            playVideo(view, index, momentz)
        }
    }

    override fun done() {

        val intent = Intent(this,MainActivity2::class.java)
        startActivity(intent)
    }

    fun playVideo(videoView: VideoView, index: Int, momentz: Momentz) {
        val str = "https://images.all-free-download.com/footage_preview/mp4/triumphal_arch_paris_traffic_cars_326.mp4"
        val uri = Uri.parse(str)

        videoView.setVideoURI(uri)

        videoView.requestFocus()
        videoView.start()

        videoView.setOnInfoListener(object : MediaPlayer.OnInfoListener {
            override fun onInfo(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
                if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                    // Here the video starts
                    momentz.editDurationAndResume(index, (videoView.duration) / 1000)
                    Toast.makeText(this@MainActivity, "Video loaded from the internet", Toast.LENGTH_LONG).show()
                    return true
                }
                return false
            }
        })
    }

}

