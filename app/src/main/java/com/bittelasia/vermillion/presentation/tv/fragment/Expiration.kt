package com.bittelasia.vermillion.presentation.tv.fragment

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bittelasia.vermillion.data.repository.stbpref.manager.LicenseDataStore.readLicense
import com.bittelasia.vlc.core.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.bittelasia.vlc.R
import com.bittelasia.vlc.databinding.ExpirationMonitorBinding

class Expiration : BaseFragment<ExpirationMonitorBinding>(){
    override fun getLayout() = R.layout.expiration_monitor

    override fun addContents() {
        super.addContents()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                requireContext().readLicense(Dispatchers.Main){
                    monitorLicense(it.END_DATE)
                }
            }
        }
    }

    private fun monitorLicense(inputDate: String){
        val sdfInput = SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.getDefault())
        val sdfOutput = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dateLicence = sdfInput.parse(inputDate)

        val parseDate = dateLicence?.let { parse -> sdfOutput.format(parse) }
        val currentDate = parseDate?.let { date -> sdfOutput.parse(date) }

        val parseDateToday = sdfOutput.format(Date())
        val dateToday = sdfOutput.parse(parseDateToday)

        val result = currentDate!!.time - dateToday!!.time
        val totalDate = result / (24 * 60 * 60 * 1000)
        binding.apply {
            if (totalDate <= 0) {
                expired = true
                message = "Subscription expired"
            } else if (totalDate <= 30){
                expired = false
            }else{
                expired = false
            }
        }
    }

}