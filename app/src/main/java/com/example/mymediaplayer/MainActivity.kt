package com.example.mymediaplayer

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    var dataSet:ArrayList<Audio>?=null
    var rv:RecyclerView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dataSet=ArrayList<Audio>()
        checkPermission()
        //dataSet!!.add(Audio("1","1","https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"))
        rv=findViewById(R.id.rvAudio)
        rv!!.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        rv!!.adapter=MyRecyclerView(dataSet!!)

    }
    val permesion=111
    private fun checkPermission() {
        if(Build.VERSION.SDK_INT>=23){
            if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),permesion)
            }
        }else{
            loadAudio()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            permesion->if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                loadAudio()
                Toast.makeText(this, "accepted", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadAudio(){
        val allAudio=MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val selection=MediaStore.Audio.Media.IS_MUSIC+"!=0"
        val cursor:Cursor?=contentResolver.query(allAudio,null,selection,null,null)
        try{
            cursor!!.moveToFirst()
            do {
                val url=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                var title=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                var author=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                dataSet!!.add(Audio(title,author,url))
            }while (cursor.moveToNext())
            cursor.close()

        }catch (ex:Exception){}
        rv!!.adapter!!.notifyDataSetChanged()
    }
}