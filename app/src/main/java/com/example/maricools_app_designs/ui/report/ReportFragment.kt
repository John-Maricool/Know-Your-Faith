package com.example.maricools_app_designs.ui.report

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.maricools_app_designs.R
import com.example.maricools_app_designs.databinding.FragmentReportBinding
import com.google.android.gms.ads.InterstitialAd
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import javax.inject.Inject

@AndroidEntryPoint
class ReportFragment : Fragment(R.layout.fragment_report) {

    private var _binding: FragmentReportBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var mInterestitial: InterstitialAd

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentReportBinding.bind(view)

        binding.btnSendIntent.setOnClickListener {
            val recipient = binding.recipientEm.text.toString().trim()
            val subject = binding.subjectEm.text.toString().trim()
            val message = binding.messageEm.text.toString().trim()
            
            sendEmail(recipient, subject, message)
        }
    }

    private fun sendEmail(recipient: String, subject: String, message: String) {
        val mIntent = Intent(Intent.ACTION_SEND)
        mIntent.data = Uri.parse("mailto:")
        mIntent.type = "text/plain"
        mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
        mIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        mIntent.putExtra(Intent.EXTRA_TEXT, message)

        try{
            startActivity(Intent.createChooser(mIntent, "Send via..."))
        }catch (e: Exception){
            Toast.makeText(activity, e.message, Toast.LENGTH_LONG).show()
        }
        if (mInterestitial.isLoaded){
            mInterestitial.show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}