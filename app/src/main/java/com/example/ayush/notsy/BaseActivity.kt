package com.example.ayush.notsy

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by ayush on 2/14/18.
 */
abstract class BaseActivity : AppCompatActivity() {

    val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActivityComponent()
        initUiComponents()
    }

    abstract fun setupActivityComponent()

    abstract fun initUiComponents()

    fun verifyPermission(grantResults: IntArray): Boolean {

        for (result in grantResults) return result == PackageManager.PERMISSION_DENIED

        return true

    }

    fun hasPermission(permission : String) =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
            else true

    fun hasPermissions(permissions : Array<String>) : Boolean {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true

        for (requestedPermission in permissions)
            if (ContextCompat.checkSelfPermission(this, requestedPermission) != PackageManager.PERMISSION_GRANTED)
                return false
        return true
    }

    fun shouldShowRequestPermissionReason(permission : String) =
            ActivityCompat.shouldShowRequestPermissionRationale(this, permission)

    fun askForPermissions(permissions: Array<String>, permissionCode : Int) =
            ActivityCompat.requestPermissions(this, permissions, permissionCode)

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        if (permissions.isEmpty()) return

        var allPermissionsGranted = true
        if (grantResults.isNotEmpty()){
            for (grantResult in grantResults){
                if (grantResult != PackageManager.PERMISSION_GRANTED){
                    allPermissionsGranted = false
                    break
                }
            }
        }

        if (!allPermissionsGranted){

            var foreverDenied = false
            for (permission in permissions){
                if (Build.VERSION.SDK_INT >=23 && shouldShowRequestPermissionRationale(permission)){

                }else{
                    if (ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED){
                        //allowed

                    }else{
                        //rejected
                        foreverDenied = true
                    }
                }
            }

            if (foreverDenied){

                val alertBuilder = AlertDialog.Builder(this)
                alertBuilder.setTitle("Permission Required")
                        .setMessage("You have forcefully denied some of the required permissions for this action. Please open settings, go to permissions and allow them.")
                        .setPositiveButton("Settings", DialogInterface.OnClickListener{ dialogInterface, i ->
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.fromParts("package", packageName, null))
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        })
                        .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialogInterface, i ->  })
                        .setCancelable(false)
                        .create()
                        .show()
            }

        } else
        {
            (supportFragmentManager.fragments[1] as NoteDetailFragment).onRequestPermissionsResult(requestCode, permissions, grantResults)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

}