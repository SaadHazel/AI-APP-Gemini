package com.saad.learnkoinmvvmretrofitroom.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.ai.client.generativeai.GenerativeModel
import com.saad.learnkoinmvvmretrofitroom.R
import com.saad.learnkoinmvvmretrofitroom.databinding.FragmentHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeFragment : Fragment() {
private lateinit var binding: FragmentHomeBinding
    private lateinit var ellipsisTextView: TextView
    private var prompt = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater,container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        ellipsisTextView = binding. findViewById(R.id.ellipsisTextView)
        val generativeModel = GenerativeModel(
            // Use a model that's applicable for your use case (see "Implement basic use cases" below)
            modelName = "gemini-pro",
            // Access your API key as a Build Configuration variable (see "Set up your API key" above)
            apiKey = "AIzaSyDgKIGpXvPvkHvs9OIh7m08PU45Nyrl_uI"
        )

        binding.button.setOnClickListener{
            prompt = binding.etQuery.text.toString()
            if(prompt.isNotEmpty()){
                CoroutineScope(Dispatchers.IO).launch {
                    var fullResponse  = ""
                    generativeModel.generateContentStream(prompt)
                        .buffer(100)
                        .collect { chunk ->

                            withContext(Dispatchers.Main){
                                fullResponse += chunk.text
                                binding.tvShowresponse.text = fullResponse
                                Log.d("APIRESPONSEforapigoogle" , "${fullResponse}")
                            }
                        }
                }
                binding.etQuery.setText("")

            }

        }

//        val prompt = "Give me compose function for adding"


    }
//    private fun showEllipsisAnimation(show: Boolean) {
//
//        ellipsisTextView.visibility = if (show) View.VISIBLE else View.GONE
//        recyclerView.visibility = if (show) View.GONE else View.VISIBLE
//
//        if (show) {
//            // Start or restart the animation
//            val animation = ObjectAnimator.ofFloat(ellipsisTextView, "alpha", 0.2f, 1f)
//            animation.duration = 500
//            animation.repeatCount = ObjectAnimator.INFINITE
//            animation.repeatMode = ObjectAnimator.REVERSE
//            animation.start()
//        }
//    }
}