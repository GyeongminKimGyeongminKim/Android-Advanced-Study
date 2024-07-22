package com.example.study_recyclerview_multiviewtype

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.study_recyclerview_multiviewtype.databinding.LeftBalloonItemBinding
import com.example.study_recyclerview_multiviewtype.databinding.RightBalloonItemBinding

class TalkAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val lst = mutableListOf<Talk>()
    val LEFT_TALK =0
    val RIGHT_TALK =1
    private lateinit var leftBalloonBinding: LeftBalloonItemBinding
    private lateinit var rightBalloonBinding: RightBalloonItemBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            LEFT_TALK->{
                leftBalloonBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.left_balloon_item,parent,false)
                LeftViewHolder(leftBalloonBinding)
            }

            RIGHT_TALK->{
                rightBalloonBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.right_balloon_item,parent,false)
                RightViewHolder(rightBalloonBinding)
            }

            else->{
                throw RuntimeException("알 수 없는 viewtype error")
            }
        }
    }

    override fun getItemCount(): Int {
        return lst.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is LeftViewHolder){
            holder.binding.leftTalk.text = lst[position].talkContent
        }
        else if (holder is RightViewHolder){
            holder.binding.rightTalk.text = lst[position].talkContent
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(lst[position].type=="left") LEFT_TALK else RIGHT_TALK
    }


    inner class LeftViewHolder(val binding:LeftBalloonItemBinding)
        :RecyclerView.ViewHolder(binding.root){
    }
    inner class RightViewHolder(val binding:RightBalloonItemBinding)
        :RecyclerView.ViewHolder(binding.root){
    }

    fun addItem(talk:Talk){
        lst.add(talk)
    }

}