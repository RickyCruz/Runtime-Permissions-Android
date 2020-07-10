package com.example.runtimepermissionswithdexter

import android.Manifest
import android.annotation.SuppressLint
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
}