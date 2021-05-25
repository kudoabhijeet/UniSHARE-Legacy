package com.example.unishare.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unishare.APIClient
import com.example.unishare.FeedAdapter
import com.example.unishare.PostItem
import com.example.unishare.R
import kotlinx.android.synthetic.main.fragment_fav.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {


    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<FeedAdapter.ViewHolder>? = null
    val api = APIClient()
    private fun getContent(){
        api.apiClient.getAllPosts().enqueue(object : Callback<List<PostItem>?> {
            override fun onResponse(
                call: Call<List<PostItem>?>,
                response: Response<List<PostItem>?>
            ) {
//                Toast.makeText(this@UploadContent,"Posted!", Toast.LENGTH_SHORT).show()
                val responseBody = response.body()
                adapter = activity?.let { FeedAdapter(it.baseContext, responseBody) }
                adapter?.notifyDataSetChanged()
                feed_view.adapter = adapter

            }

            override fun onFailure(call: Call<List<PostItem>?>, t: Throwable) {
//                Toast.makeText(this@UploadContent,"Failed to Post!", Toast.LENGTH_SHORT).show()
            }
        })

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav, container, false)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        feed_view.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            getContent()
        }
    }






















//    private lateinit var homeViewModel: HomeViewModel
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        homeViewModel =
//            ViewModelProvider(this).get(HomeViewModel::class.java)
//        val root = inflater.inflate(R.layout.fragment_fav, container, false)
//        val textView: TextView = root.findViewById(R.id.text_home)
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
//        return root
//    }
}