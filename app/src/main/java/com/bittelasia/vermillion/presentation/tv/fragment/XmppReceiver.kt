package com.bittelasia.vermillion.presentation.tv.fragment

import android.media.MediaPlayer
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.bittelasia.vermillion.R
import com.bittelasia.vermillion.data.repository.stbpref.data.STB
import com.bittelasia.vermillion.databinding.StatusReceiverBinding
import com.bittelasia.vermillion.domain.model.broadcast.BroadCastData
import com.bittelasia.vermillion.presentation.components.schedule
import com.bittelasia.vermillion.presentation.home.HomeViewModel
import com.bittelasia.vlc.core.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Timer

@AndroidEntryPoint
open class XmppReceiver : BaseFragment<StatusReceiverBinding>() {
    private val vm: HomeViewModel by viewModels()
    private var mediaPlayer: MediaPlayer?=null

    override fun addContents() {
        super.addContents()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    vm.broadcastReceiver.collectLatest { broadcast ->
                        broadcastFragment(broadcast?.data)
                    }
                }
            }
        }
    }

    private fun broadcastFragment(broadcast: BroadCastData?){
        binding.apply {
            val host = "${STB.HOST}:${STB.PORT}/"
            if (broadcast != null) {
                val duration = broadcast.time.times(1000)
                when (broadcast.type) {
                    "scrolling" -> {
                        myBroadcast.visibility = View.VISIBLE
                        myBroadcast.setText(broadcast.message)
                        myBroadcast.start()
                        Timer("Scrolling", false).schedule(duration.toLong()) {
                            CoroutineScope(Dispatchers.Main).launch {
                                myBroadcast.stop()
                                myBroadcast.visibility = View.GONE
                            }
                        }
                    }
                    "pop" -> {
                        try {
                            layoutPop.visibility = View.VISIBLE
                            if (broadcast.url?.isNotEmpty() == true) {
                                val imageUri = host + broadcast.url
                                ivPop.load(imageUri)
                                tvPop.text = broadcast.message
                                Timer("Pop", false).schedule(duration.toLong()) {
                                    CoroutineScope(Dispatchers.Main).launch {
                                        layoutPop.visibility = View.GONE
                                    }
                                }
                            } else {
                                tvPop.text = broadcast.message
                                ivPop.load(0)
                                Timer("Pop", false).schedule(duration.toLong()) {
                                    CoroutineScope(Dispatchers.Main).launch {
                                        layoutPop.visibility = View.GONE
                                    }
                                }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    "emergency" -> {
                        try {
                            alertParent.visibility = View.VISIBLE
                            emergency.visibility = View.VISIBLE
                            lottieAnimationView.visibility = View.GONE
                            audioPlayer()
                            if (broadcast.url?.isNotEmpty() == true) {
                                val imageUri = host + broadcast.url
                                ivEm.visibility = View.VISIBLE
                                ivEm.load(imageUri)
                                emergency.setText(broadcast.message)
                                Timer("Emergency", false).schedule(duration.toLong()) {
                                    CoroutineScope(Dispatchers.Main).launch {
                                        emergency.stop()
                                        mediaPlayer?.stop()
                                        alertParent.visibility = View.GONE
                                        emergency.visibility = View.GONE
                                    }
                                }
                            } else {
                                lottieAnimationView.visibility = View.VISIBLE
                                ivEm.load(0)
                                emergency.setText(broadcast.message)
                                Timer("Emergency", false).schedule(duration.toLong()) {
                                    CoroutineScope(Dispatchers.Main).launch {
                                        emergency.stop()
                                        mediaPlayer?.stop()
                                        alertParent.visibility = View.GONE
                                        emergency.visibility = View.GONE
                                    }
                                }
                            }
                            emergency.start()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }
    }

    private fun audioPlayer(){
        CoroutineScope(Dispatchers.IO).launch {
            mediaPlayer = MediaPlayer.create(requireContext(), R.raw.alarm)
            mediaPlayer!!.isLooping = true
            mediaPlayer!!.start()
        }
    }

    override fun getLayout() = R.layout.status_receiver
}