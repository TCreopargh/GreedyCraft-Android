package xyz.tcreopargh.greedycraft

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import android.widget.VideoView
import java.lang.ref.WeakReference
import java.util.*


class MainActivity : Activity() {

    private lateinit var videoView: VideoView
    private lateinit var mojangView: ImageView

    private lateinit var myHandler: MyHandler;

    class MyHandler(activity: MainActivity) : Handler(Looper.getMainLooper()) {
        val activityRef: WeakReference<MainActivity> = WeakReference(activity)

        override fun handleMessage(msg: Message) {
            when (msg.what) {
                0 -> {
                    activityRef.get()?.apply Activity@{
                        val normalVideoPath =
                            "android.resource://" + packageName + "/" + R.raw.video_raw
                        val rickrollPath =
                            "android.resource://" + packageName + "/" + R.raw.rickroll
                        mojangView.visibility = View.GONE
                        videoView.apply {
                            visibility = View.VISIBLE
                            setVideoURI(Uri.parse(normalVideoPath))
                            start()
                            setOnClickListener {
                                /*
                                    val lang = Locale.getDefault()
                                    var uri = "https://www.youtube.com/watch?v=dQw4w9WgXcQ"
                                    if (lang.language.equals(Locale.CHINESE.language)) {
                                        uri = "https://www.bilibili.com/video/BV1GJ411x7h7"
                                    }
                                    val intent = Intent(ACTION_VIEW, Uri.parse(uri))
                                    startActivity(intent)
                                    */
                                setVideoURI(Uri.parse(rickrollPath))
                                start()
                                Toast.makeText(
                                    this@Activity,
                                    context.getString(R.string.april_fools),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myHandler = MyHandler(this)
        videoView = findViewById(R.id.trollVideo)
        mojangView = findViewById(R.id.mojangView)
        videoView.visibility = View.GONE
        Toast.makeText(this, getString(R.string.loading), Toast.LENGTH_SHORT).show()
        Thread {
            Thread.sleep((Random().nextInt(5000) + 6000).toLong())
            myHandler.sendMessage(Message.obtain().apply { what = 0 })
        }.start()
    }
}