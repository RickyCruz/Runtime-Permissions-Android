package com.example.runtimepermissionswithdexter

import android.Manifest
import android.app.Activity
import android.os.Bundle
import androidx.core.content.ContextCompat
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

        btnCamera.setOnClickListener {
            checkCameraPermissions()
        }

        btnContacts.setOnClickListener {
            checkContactsPermissions()
        }

        btnAudio.setOnClickListener {
            checkAudioPermissions()
        }
    }

    private fun checkCameraPermissions() {
        val context = this

        Dexter.withContext(context)
            .withPermission(Manifest.permission.CAMERA)
            .withListener(object: PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    tvCamera.text = getString(R.string.permission_status_granted)
                    tvCamera.setTextColor(ContextCompat.getColor(context, R.color.colorPermissionStatusGranted))
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    if (response.isPermanentlyDenied) {
                        tvCamera.text = getString(R.string.permission_status_denied_permanently)
                        tvCamera.setTextColor(
                            ContextCompat.getColor(
                                context,
                                R.color.colorPermissionStatusPermanentlyDenied
                            )
                        )
                    } else {
                        tvCamera.text = getString(R.string.permission_status_denied)
                        tvCamera.setTextColor(
                            ContextCompat.getColor(
                                context,
                                R.color.colorPermissionStatusDenied
                            )
                        )
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

    private fun checkContactsPermissions() {
        val context = this

        Dexter.withContext(context)
            .withPermission(Manifest.permission.READ_CONTACTS)
            .withListener(object: PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    tvContacts.text = getString(R.string.permission_status_granted)
                    tvContacts.setTextColor(ContextCompat.getColor(context, R.color.colorPermissionStatusGranted))
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    if (response.isPermanentlyDenied) {
                        tvContacts.text = getString(R.string.permission_status_denied_permanently)
                        tvContacts.setTextColor(
                            ContextCompat.getColor(
                                context,
                                R.color.colorPermissionStatusPermanentlyDenied
                            )
                        )
                    } else {
                        tvContacts.text = getString(R.string.permission_status_denied)
                        tvContacts.setTextColor(
                            ContextCompat.getColor(
                                context,
                                R.color.colorPermissionStatusDenied
                            )
                        )
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

    private fun checkAudioPermissions() {
        val context = this

        Dexter.withContext(context)
            .withPermission(Manifest.permission.RECORD_AUDIO)
            .withListener(object: PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    tvAudio.text = getString(R.string.permission_status_granted)
                    tvAudio.setTextColor(ContextCompat.getColor(context, R.color.colorPermissionStatusGranted))
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    if (response.isPermanentlyDenied) {
                        tvAudio.text = getString(R.string.permission_status_denied_permanently)
                        tvAudio.setTextColor(
                            ContextCompat.getColor(
                                context,
                                R.color.colorPermissionStatusPermanentlyDenied
                            )
                        )
                    } else {
                        tvAudio.text = getString(R.string.permission_status_denied)
                        tvAudio.setTextColor(
                            ContextCompat.getColor(
                                context,
                                R.color.colorPermissionStatusDenied
                            )
                        )
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
}