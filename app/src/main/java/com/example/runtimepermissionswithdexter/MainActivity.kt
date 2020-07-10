package com.example.runtimepermissionswithdexter

import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.runtimepermissionswithdexter.enum.PermissionStatusEnum
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCamera.setOnClickListener { checkCameraPermissions() }
        btnContacts.setOnClickListener { checkContactsPermissions() }
        btnAudio.setOnClickListener { checkAudioPermissions() }
        btnAll.setOnClickListener { checkAllPermissions() }
    }

    private fun checkCameraPermissions() = setPermissionHandler(Manifest.permission.CAMERA, tvCamera)

    private fun checkContactsPermissions() = setPermissionHandler(Manifest.permission.READ_CONTACTS, tvContacts)

    private fun checkAudioPermissions() = setPermissionHandler(Manifest.permission.RECORD_AUDIO, tvAudio)

    private fun checkAllPermissions() {
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.RECORD_AUDIO
            )
            .withListener(object: MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let {
                        for (permission in report.grantedPermissionResponses) {
                            when (permission.permissionName) {
                                Manifest.permission.CAMERA -> setPermissionStatus(tvCamera, PermissionStatusEnum.GRANTED)
                                Manifest.permission.READ_CONTACTS -> setPermissionStatus(tvContacts, PermissionStatusEnum.GRANTED)
                                Manifest.permission.RECORD_AUDIO -> setPermissionStatus(tvAudio, PermissionStatusEnum.GRANTED)
                            }
                        }

                        for (permission in report.deniedPermissionResponses) {
                            when (permission.permissionName) {
                                Manifest.permission.CAMERA -> {
                                    if (permission.isPermanentlyDenied) {
                                        setPermissionStatus(tvCamera, PermissionStatusEnum.PERMANENTLY_DENIED)
                                    } else {
                                        setPermissionStatus(tvCamera, PermissionStatusEnum.DENIED)
                                    }
                                }

                                Manifest.permission.READ_CONTACTS -> {
                                    if (permission.isPermanentlyDenied) {
                                        setPermissionStatus(tvContacts, PermissionStatusEnum.PERMANENTLY_DENIED)
                                    } else {
                                        setPermissionStatus(tvContacts, PermissionStatusEnum.DENIED)
                                    }
                                }

                                Manifest.permission.RECORD_AUDIO -> {
                                    if (permission.isPermanentlyDenied) {
                                        setPermissionStatus(tvAudio, PermissionStatusEnum.PERMANENTLY_DENIED)
                                    } else {
                                        setPermissionStatus(tvAudio, PermissionStatusEnum.DENIED)
                                    }
                                }
                            }
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }

            })
            .check()
    }

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