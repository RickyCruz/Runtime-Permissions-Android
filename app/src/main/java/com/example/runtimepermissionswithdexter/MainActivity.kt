package com.example.runtimepermissionswithdexter

import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.runtimepermissionswithdexter.enum.PermissionStatusEnum
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCamera.setOnClickListener { checkCameraPermissions() }
        btnContacts.setOnClickListener { checkContactsPermissions() }
        btnAudio.setOnClickListener { checkAudioPermissions() }
    }

    private fun checkCameraPermissions() = setPermissionHandler(Manifest.permission.CAMERA, tvCamera)

    private fun checkContactsPermissions() = setPermissionHandler(Manifest.permission.READ_CONTACTS, tvContacts)

    private fun checkAudioPermissions() = setPermissionHandler(Manifest.permission.RECORD_AUDIO, tvAudio)

    private fun setPermissionHandler(permission: String, textView: TextView) {
        Dexter.withContext(this)
            .withPermission(permission)
            .withListener(object: PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    setPermissionStatus(textView, PermissionStatusEnum.GRANTED)
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    if (response.isPermanentlyDenied) {
                        setPermissionStatus(textView, PermissionStatusEnum.PERMANENTLY_DENIED)
                    } else {
                        setPermissionStatus(textView, PermissionStatusEnum.DENIED)
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            })
            .check()
    }

    private fun setPermissionStatus(textView: TextView, status: PermissionStatusEnum) {
        when (status) {
            PermissionStatusEnum.GRANTED -> {
                textView.text = getString(R.string.permission_status_granted)
                textView.setTextColor(ContextCompat.getColor(this, R.color.colorPermissionStatusGranted))
            }

            PermissionStatusEnum.DENIED -> {
                textView.text = getString(R.string.permission_status_denied)
                textView.setTextColor(ContextCompat.getColor(this, R.color.colorPermissionStatusDenied))
            }

            PermissionStatusEnum.PERMANENTLY_DENIED -> {
                textView.text = getString(R.string.permission_status_denied_permanently)
                textView.setTextColor(ContextCompat.getColor(this, R.color.colorPermissionStatusPermanentlyDenied))
            }
        }
    }
}