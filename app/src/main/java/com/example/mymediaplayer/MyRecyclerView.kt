package com.example.mymediaplayer

import android.media.MediaPlayer
import android.net.Uri
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.net.toUri

import androidx.recyclerview.widget.RecyclerView
import java.io.File

class MyRecyclerView(var dataSet:ArrayList<Audio>):
    RecyclerView.Adapter<MyRecyclerView.ViewHolder>() {
    var mplayer:MediaPlayer?=null
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var tvTitle:TextView
        var tvAuthor:TextView
        var playStopBtn:Button
        init {
            tvTitle=itemView.findViewById<TextView>(R.id.tvTitle)
            tvAuthor=itemView.findViewById<TextView>(R.id.tvAuthor)
            playStopBtn=itemView.findViewById<Button>(R.id.playPauseBtn)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.audio_ticket,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTitle.text=dataSet[position].title
        holder.tvAuthor.text=dataSet[position].author
        holder.playStopBtn.setOnClickListener(View.OnClickListener {

            if(it.findViewById<Button>(R.id.playPauseBtn).text=="PLAY"){
                mplayer= MediaPlayer()
                //val uri=Uri.parse(dataSet[position].url)
                try {
                    //mplayer = MediaPlayer.create(holder.itemView.context, uri)
                    mplayer!!.setDataSource(dataSet[position].url)
                    mplayer!!.prepare()
                    mplayer!!.start()
                    it.findViewById<Button>(R.id.playPauseBtn).text = "STOP"
                }catch (ex:Exception){}
            }else{
                mplayer!!.stop()
                mplayer!!.release()
                it.findViewById<Button>(R.id.playPauseBtn).text="PLAY"
            }
        })
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}